package com.cloud.dips.admin.api.feign.fallback;

import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.feign.RemoteUserService;
import com.cloud.dips.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {
	@Setter
	private Throwable cause;

	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @param from     内外标志
	 * @return R
	 */
	@Override
	public R<UserInfo> info(String username, String from) {
		log.error("feign 查询用户信息失败:{}", username, cause);
		return null;
	}

	/**
	 * 通过社交账号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@Override
	public R<UserInfo> social(String inStr) {
		log.error("feign 查询用户信息失败:{}", inStr, cause);
		return null;
	}

	/**
	 * 修改用户个人资料
	 */
	@Override
	public R<Boolean> editInfo(@Valid UserDTO userDto) {
		log.error("feign 修改用户个人资料:{}", userDto, cause);
		return null;
	}




}
