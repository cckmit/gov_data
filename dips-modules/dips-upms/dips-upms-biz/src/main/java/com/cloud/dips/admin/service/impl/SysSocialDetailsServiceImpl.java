package com.cloud.dips.admin.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.vo.SocialVO;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.security.util.AesCbcUtil;
import com.cloud.dips.common.security.util.AuthUtils;
import com.cloud.dips.common.security.util.UuidUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysSocialDetails;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.mapper.SysSocialDetailsMapper;
import com.cloud.dips.admin.mapper.SysUserMapper;
import com.cloud.dips.admin.service.SysSocialDetailsService;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.constant.enums.EnumLoginType;
import com.cloud.dips.common.security.util.SecurityUtils;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Slf4j
@AllArgsConstructor
@Service("sysSocialDetailsService")
public class SysSocialDetailsServiceImpl extends ServiceImpl<SysSocialDetailsMapper, SysSocialDetails> implements SysSocialDetailsService {
    private final CacheManager cacheManager;
    private final RestTemplate restTemplate;
    private final SysUserMapper sysUserMapper;
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final RedisTemplate redisTemplate;

    /**
     * 通过客户端信息查询openId
     *
     * @param inStr appid @ code
     * @return
     */
    @Override
    public Map<String, String> findOpenId(String inStr) {
        String[] inStrs = inStr.split("@");
        SocialVO socialVO = new SocialVO();
        if (inStrs.length > SecurityConstants.JUDGEMENT_VALUE) {
            socialVO.setAppId(inStrs[0]);
            socialVO.setCode(inStrs[1]);
            socialVO.setEncryptedData(inStrs[2]);
            socialVO.setIv(inStrs[3]);
        } else if (inStrs.length <= SecurityConstants.JUDGEMENT_VALUE) {
            socialVO.setAppId(inStrs[0]);
            socialVO.setCode(inStrs[1]);
        }
        return getOpenId(socialVO);
    }

    /**
     * 绑定社交账号
     *
     * @param socialVO
     * @return
     */
    @Override
    public Boolean bindSocial(SocialVO socialVO) {
        Map<String, String> result = getOpenId(socialVO);

        SysUser sysUser = sysUserMapper.selectById(SecurityUtils.getUser().getId());
        sysUser.setWeixinOpenid(result.get("openId"));

        sysUserMapper.updateAllColumnById(sysUser);
        //更新緩存
        cacheManager.getCache("user_details").evict(result.get("openId"));
        return Boolean.TRUE;
    }

    /**
     * 通过appid 、code 获得openID
     *
     * @param socialVO
     * @return
     */
    private Map<String, String> getOpenId(SocialVO socialVO) {


        SysSocialDetails condition = new SysSocialDetails();
        condition.setAppId(socialVO.getAppId());
        SysSocialDetails socialDetails = this.baseMapper.selectOne(condition);

        Map<String, String> result = new HashMap<>(2);
        String unionid = null;
        //微信登录
        if (EnumLoginType.WECHAT.getType().equals(socialDetails.getType())) {
            String url = String.format(SecurityConstants.WX_AUTHORIZATION_CODE_URL
                    , socialDetails.getAppId(), socialDetails.getAppSecret(), socialVO.getCode());
            String resulted = restTemplate.getForObject(url, String.class);
            log.debug("微信响应报文:{}", resulted);

            Object obj = JSONUtil.parseObj(resulted).get("unionid");
            if (obj == null) {
                return null;
            }

            SysUser user = new SysUser();
            unionid = obj.toString();
            result.put("type", socialDetails.getType());
            result.put("unionid", unionid);
            user.setWeixinOpenid(unionid);
            SysUser sysUser = sysUserMapper.selectOne(user);
            if (sysUser == null) {
                String urls = String.format(SecurityConstants.WX_USERINFO_URL
                        , JSONUtil.parseObj(resulted).get("access_token").toString(), JSONUtil.parseObj(resulted).get("openid").toString());
                String resultUserInfo = restTemplate.getForObject(urls, String.class);
                user.setIsDeleted(CommonConstant.STATUS_NORMAL);
                //设置为web端账号
                user.setType(EnumLoginType.WEB_TYE.getType());
                user.setPassword(ENCODER.encode("guomai168"));
                user.setUsername("govmade" + UuidUtils.generateShortUuid());

                String username = null;
                try {
                    username = new String(JSONUtil.parseObj(resultUserInfo).get("nickname").toString().getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                log.info("用户名为：{}" + username);
                user.setRealName(username);
                user.setAvatar(JSONUtil.parseObj(resultUserInfo).get("headimgurl").toString());
                user.applyDefaultValue();
                sysUserMapper.insert(user);

            } else {
                String urls = String.format(SecurityConstants.WX_USERINFO_URL
                        , JSONUtil.parseObj(resulted).get("access_token").toString(), JSONUtil.parseObj(resulted).get("openid").toString());
                String resultUserInfo = restTemplate.getForObject(urls, String.class);
                String username = null;
                try {
                    username = new String(JSONUtil.parseObj(resultUserInfo).get("nickname").toString().getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
				EntityWrapper<SysUser> entityWrapper=new EntityWrapper<>();
                entityWrapper.eq("id",sysUser.getId());
				String headimgurl = JSONUtil.parseObj(resultUserInfo).get("headimgurl").toString();
				sysUserMapper.updateForSet("real_name='"+username+"',"+"avatar='"+headimgurl+"'",entityWrapper);


            }


        } else if (EnumLoginType.APPLETS_LOGIN.getType().equals(socialDetails.getType())) {
            AuthUtils.setCout(AuthUtils.getCout() + 1);
            String sessionKey = null;
            String url = String.format(SecurityConstants.WX_APPLETS_CODE_URL
                    , socialDetails.getAppId(), socialDetails.getAppSecret(), socialVO.getCode());
            String resulted = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.fromObject(resulted);
            sessionKey = json.getString("session_key");

//            Object sessionKeys = redisTemplate.opsForValue().get(json.getString("openid"));
//            if (sessionKeys != "" && sessionKeys != null) {
//                sessionKey = String.valueOf(sessionKeys);
//            } else {
//                redisTemplate.opsForValue().set(json.getString("openid"), sessionKey, 110, TimeUnit.SECONDS);
//            }

            log.info("微信返回值为:{}" + json);


            try {
                String results = AesCbcUtil.decrypt(socialVO.getEncryptedData(), sessionKey, socialVO.getIv(), "UTF-8");

                JSONObject jsonUserInfo = JSONObject.fromObject(results);
                log.info("解析用户数据UUID：{}" + jsonUserInfo.getString("unionId"));
                result.put("type", socialDetails.getType());
                result.put("unionid", jsonUserInfo.getString("unionId"));

                SysUser userInfo = new SysUser();
                userInfo.setWeixinOpenid(jsonUserInfo.getString("unionId"));
                SysUser users = sysUserMapper.selectOne(userInfo);
                //判断账号是否绑定
                if (users == null) {
                    this.autoUser(userInfo);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("微信小程序解密失败");
            }


            log.info("调用次数为：{}" + AuthUtils.getCout());
        }

        return result;


    }


    public void autoUser(SysUser user) {

        user.setIsDeleted(CommonConstant.STATUS_NORMAL);
        //设置为web端账号
        user.setType(EnumLoginType.WEB_TYE.getType());
        user.setPassword(ENCODER.encode("guomai168"));
        user.setUsername("govmade" + UuidUtils.generateShortUuid());
        user.applyDefaultValue();
        sysUserMapper.insert(user);
    }

}
