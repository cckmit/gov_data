package com.cloud.dips.user.controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.constant.enums.EnumLoginType;
import com.cloud.dips.common.core.exception.ValidateCodeException;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.user.util.WeChatUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.user.service.WebUsersService;
import com.cloud.dips.common.core.util.R;
import org.springframework.web.client.RestTemplate;

/**
 * @author BigPan
 * @date 2018-12-05 16:47:56
 */
@RestController
@Slf4j
@RequestMapping("/webusers")
public class WebUsersController {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	@Autowired
	private WebUsersService webUsersService;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public Page page(@RequestParam Map<String, Object> params) {
		return webUsersService.selectPage(new Query<>(params), new EntityWrapper<>());
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id) {
		SysUser webUsers = webUsersService.selectById(id);
		return new R<>(webUsers);
	}


	/**
	 * web端注册成功
	 *
	 * @param userDto
	 * @return
	 */
	@PostMapping("/register")
	@SysLog(value = "用户注册", role = EnumRole.WEB_TYE)
	public R<Boolean> insetSysUser(@RequestBody UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setIsDeleted(CommonConstant.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		sysUser.applyDefaultValue();
		sysUser.setIsExperience(EnumLoginType.IS_Experience.getType());
		//设置为web端账号
		sysUser.setType(EnumLoginType.WEB_TYE.getType());
		webUsersService.insert(sysUser);
//		userDto.getRole().forEach(roleId -> {
//			SysUserRole userRole = new SysUserRole();
//			userRole.setUserId(sysUser.getId());
//			userRole.setRoleId(roleId);
//			userRole.insert();
//		});
		return new R<>(Boolean.TRUE);

	}

	/**
	 * 修改个人信息
	 *
	 * @param
	 * @return success/false
	 */
//	@PutMapping("/editInfo")
//	@ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
//	public R<Boolean> editInfo(@RequestBody UserDTO userDto) {
////       SysUser sysUser=new SysUser();
////       BeanUtils.copyProperties(userDto,sysUser);
////       webUsersService.updateById(sysUser);
//
//       return webUsersService.editInfo(userDto,userDto.getUsername());
//
//	}
	@PutMapping("/changeUpdate")
	@ApiOperation(value = "修改密码", notes = "修改用户密码", httpMethod = "PUT")
	@SysLog(value = "用户修改密码", role = EnumRole.WEB_TYE)
	public R<Boolean> changeUpdate(@RequestBody UserDTO userDTO) {

//		SysUser sysUser = webUsersService.selectById(userDTO.getId());
//		log.info("用户原始密码:{}",sysUser.getPassword());
//		log.info("用户输入原始密码:{}",ENCODER.encode(userDTO.getPassword()));
//	    if(ENCODER.matches(userDTO.getPassword(),sysUser.getPassword())){
//           sysUser.setPassword(ENCODER.encode(userDTO.getNewpassword1()));
//           webUsersService.updateById(sysUser);
//           return   new R<>(Boolean.TRUE);
//		}

		return webUsersService.changeUpdate(userDTO, userDTO.getUsername());

	}

	/**
	 * 用户名是否重复
	 *
	 * @param params 用户信息
	 * @return success/false
	 */
	@PostMapping("/repeat")
	@SysLog(value = "用户名是否重复", role = EnumRole.WEB_TYE)
	@ApiOperation(value = "用户名是否重复", notes = "用户名", httpMethod = "POST")
	public R<Boolean> repeat(@RequestBody Map<String, String> params) {
		String username = params.get("username");
		SysUser sysUser = new SysUser();
		sysUser.setType(EnumLoginType.WEB_TYE.getType());
		sysUser.setUsername(username);
		SysUser user = this.webUsersService.selectOne(new EntityWrapper<>(sysUser));
		if (user != null) {
			return new R<>(Boolean.FALSE);
		}
		return new R<>(Boolean.TRUE);
	}


	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public R delete(@PathVariable Integer id) {

		return new R<>(webUsersService.deleteById(id));
	}

	@GetMapping("/share")
	@ApiOperation(value = "微信分享接口调用")
	@SysLog(value = "微信分享接口调用", role = EnumRole.WEB_TYE)
	public R<Map<String, Object>> share(@RequestParam String url) {
		String key = "access_tokens";
		String jsApiTicket = "jsApiTicket";
		String accessToken = new String();
		Object ticket = new Object();

		//判断access_token 是否过期
		if (redisTemplate.hasKey(key)) {
			log.info("从redis中获取access_token 方法");
			accessToken = redisTemplate.opsForValue().get(key).toString();


		} else {
			log.info("进入获取access_token 方法");
			JSONObject accessTokens = WeChatUtils.getAccessToken();
			Optional<Object> token = Optional.ofNullable(accessTokens.get("access_token"));

			if (token.isPresent()) {
				redisTemplate.opsForValue().set(key, accessTokens.get("access_token"), 7100, TimeUnit.SECONDS);
				accessToken = accessTokens.get("access_token").toString();
			}
		}
		log.info("access_token=====>" + accessToken);
		//判断jspi是否过期
		if (redisTemplate.hasKey(jsApiTicket)) {
			log.info("从redis中获取ticket 方法");
			ticket = redisTemplate.opsForValue().get(jsApiTicket);
		} else {
			log.info("进入获取ticket 方法");
			JSONObject json = WeChatUtils.getJsApiTicket(accessToken);
			Optional<Object> jspi = Optional.ofNullable(json.get("ticket"));


			if (jspi.isPresent()) {

				redisTemplate.opsForValue().set(jsApiTicket, json.get("ticket"), json.getLong("expires_in") - 100, TimeUnit.SECONDS);
				ticket = json.getString("ticket");
			}
		}
		log.info("ticke==========>" + ticket);
		//生成微信权限验证的参数
		Map<String, Object> map = WeChatUtils.makeWXTicket(ticket, url);

		return new R<>(map);
	}


}
