package com.cloud.dips.raus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.dto.PreposeUserDTO;
import com.cloud.dips.raus.api.dto.ThemeApiDTO;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.vo.GeneralApiVO;
import com.cloud.dips.raus.mapper.PreposeUserMapper;
import com.cloud.dips.raus.service.GovPolicyGeneralApiService;
import com.cloud.dips.raus.service.PreposeUserService;


@Service("preposeUserService")
public class PreposeUserServiceImpl extends ServiceImpl<PreposeUserMapper, PreposeUser> implements PreposeUserService {

	@Autowired
	private PreposeUserMapper preposeUserMapper;
	@Autowired
	private GovPolicyGeneralApiService govPolicyGeneralApiService;
	/**
	 * 查找是否存在token
	 */
	@Override
	public Integer findHasToken(String tokenName) {
		Integer integer = preposeUserMapper.findHasToken(tokenName);
		return integer;
	}

	@Override
	public void insertToken(PreposeUser preposeUser) {
		preposeUserMapper.insertToken(preposeUser);
	}

	@Override
	public String createToken(PreposeUserDTO preposeUserDTO) {
		StringBuffer  bulid = new StringBuffer ();
		// 新建一个32位数组
		String[] token = new String[32];
		// 设置随机生成的格式
		String[] a= {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
					 "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
				     "1","2","3","4","5","6","7","8","9","0","!","@","$","%","/","-","=","*"};
		String tokenName = null;
		// 设置开关
		boolean flag = true;
		do {
			// 清除存在的生成的token
			bulid.delete(0, token.length);
			// 随机生成32位token
			for (int i = 0; i < token.length; i++) {
				token[i] = a[(int) (Math.random()*a.length)];
				// 利用StringBuffer进行拼接
				bulid.append(token[i]);
			}
			// 转换为String类型
			tokenName = bulid.toString();
			// 查找数据库时候是否已经存在改令牌，如果存在，则重新生成
			Integer number = preposeUserMapper.findHasToken(tokenName);
			if(number != null && number <= 0) {
				// 没有相同则走出循环
				flag = false;
			}
		}while(flag);
		// 插入数据库（名字，token,失效时间，主题个数，字段个数，）
		PreposeUser preposeUser = new PreposeUser();
		preposeUser.setUserName(preposeUserDTO.getUserName());
		preposeUser.setToken(tokenName);
		preposeUser.setInvalidTime(preposeUserDTO.getInvalidTime());
		preposeUser.setPermitThemeCounts(preposeUserDTO.getPermitThemeCounts());
		preposeUser.setPermitColumnCounts(preposeUserDTO.getPermitColumnCounts());
		preposeUserMapper.insertToken(preposeUser);
		return tokenName;
	}

	/**
	 * 推送通用api数据
	 */
	@Override
	public Page<GeneralApiVO> invokingThemeApi(ThemeApiDTO themeApiDTO) {
		List<String> error = new ArrayList<String>();
		PreposeUser preposeUser = new PreposeUser();
		preposeUser.setUserName(themeApiDTO.getUserName());
		PreposeUser one = preposeUserMapper.selectOne(preposeUser);
		if (null == one) {
			Page page = new Page<>();
			error.add("没有该用户信息，请核实用户信息！");
			page.setRecords(error);
			return page;
		}
		Date nowDate = new Date();
		boolean isTrue = nowDate.after(one.getInvalidTime());
		if (isTrue) {
			Page page = new Page<>();
			error.add("用户有效期已到，未免影响正常使用，请及时续费！");
			page.setRecords(error);
			return page;
		}
		String token = one.getToken();
		if (!token.equals(themeApiDTO.getToken())) {
			Page page = new Page<>();
			error.add("用户令牌不正确，请核实用户令牌！");
			page.setRecords(error);
			return page;
		}
		if (!"6M".equals(themeApiDTO.getQueryTime()) && !"1Y".equals(themeApiDTO.getQueryTime()) 
				&& !"3Y".equals(themeApiDTO.getQueryTime()) && !"5Y".equals(themeApiDTO.getQueryTime())){
			Page page = new Page<>();
			error.add("请输入有效的查询时间！");
			page.setRecords(error);
			return page;
		}
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("page", themeApiDTO.getPage());
    	// 传入参数查询数据
    	Page<GeneralApiVO> generalApi = govPolicyGeneralApiService.selectGeneralApi(new Query<>(params),one,themeApiDTO.getQueryTime());
		// 成功调用则返回次数+1
		PreposeUser prepose = new PreposeUser();
		int returnCount = one.getReturnTimes().intValue();
		returnCount++;
		prepose.setId(one.getId());
		prepose.setReturnTimes(returnCount);
		preposeUserMapper.updateById(prepose);
		return generalApi;
	}
}