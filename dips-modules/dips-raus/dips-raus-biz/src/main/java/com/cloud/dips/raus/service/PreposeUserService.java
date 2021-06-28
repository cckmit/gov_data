package com.cloud.dips.raus.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.raus.api.dto.PreposeUserDTO;
import com.cloud.dips.raus.api.dto.ThemeApiDTO;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;

/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
public interface PreposeUserService extends IService<PreposeUser> {
	
	/**
	 * 创建用户令牌
	 */
	public String createToken(PreposeUserDTO preposeUserDTO);
	/**
	 * 查找是否存在token
	 */
	public Integer findHasToken(String tokenName);
	
	/**
	 * 用户增加对应的信息(token,失效时间等)
	 */
	public void insertToken(PreposeUser preposeUser);
	
	/**
	 * 用户主题api调用
	 */
	 public Page invokingThemeApi(ThemeApiDTO themeApiDTO);
	
}

