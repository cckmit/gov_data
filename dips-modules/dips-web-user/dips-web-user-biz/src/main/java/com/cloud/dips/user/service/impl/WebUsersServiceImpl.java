package com.cloud.dips.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.user.mapper.WebUsersMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cloud.dips.user.service.WebUsersService;

import java.util.Objects;


/**
 * web端
 * @author yinzan
 * @description //TODO
 * @date 下午3:02 2018/12/11
 * @param
 * @return
　*/
@Slf4j
@Service
@AllArgsConstructor
public class WebUsersServiceImpl extends ServiceImpl<WebUsersMapper, SysUser> implements WebUsersService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final CacheManager cacheManager;
	/**
	 * 修改个人信息
	 * @param userDto
	 * @param username
	 */
	@Override
	public R<Boolean> editInfo(UserDTO userDto,String username) {
		SysUser sysUser=new SysUser();
		BeanUtils.copyProperties(userDto,sysUser);
       this.updateById(sysUser);
		Objects.requireNonNull(cacheManager.getCache("user_details" )).clear();
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 修改密码
	 * @param userDTO
	 * @param username
	 */
	@Override
	public R<Boolean> changeUpdate(UserDTO userDTO, String username) {
		SysUser sysUser = this.selectById(SecurityUtils.getUser().getId());
		Objects.requireNonNull(cacheManager.getCache("user_details" )).clear();
		if(ENCODER.matches(userDTO.getPassword(),sysUser.getPassword())){
			if(ENCODER.matches(userDTO.getNewpassword1(),ENCODER.encode(userDTO.getCheckPass()))) {
				//BeanUtils.copyProperties(userDTO,sysUser);
				sysUser.setPassword(ENCODER.encode(userDTO.getNewpassword1()));
				this.updateById(sysUser);
				return new R<>(Boolean.TRUE);
			}
			return  new R<>(Boolean.FALSE,"确认密码和新密码不一致，修改失败");

		}

		return  new R<>(Boolean.FALSE,"原密码错误，修改失败");
	}
}
