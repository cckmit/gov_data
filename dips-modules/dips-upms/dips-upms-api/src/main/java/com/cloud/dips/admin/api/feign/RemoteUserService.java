/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.feign.factory.RemoteUserServiceFallbackFactory;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;

/**
 * @author Wilson
 * @date 2018/6/22
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {
	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/user/info/{username}")
	R<UserInfo> info(@PathVariable("username") String username
		, @RequestHeader("from") String from);

	/**
	 * 通过社交账号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@PostMapping("/social/info")
	R<UserInfo> social(@RequestParam("inStr") String inStr);
	/**
	 * 修改个人资料
	 * @param userDto
	 * @return
	 */
	@PutMapping("/user/webusers/editInfo")
	public R<Boolean> editInfo(@Valid @RequestBody UserDTO userDto);

}
