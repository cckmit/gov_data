package com.cloud.dips.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.user.api.entity.WebUsers;

/**
 * 
 *
 * @author BigPan
 * @date 2018-12-05 16:47:56
 */
public interface WebUsersService extends IService<SysUser> {
	/**
	 * 修改个人信息
	 * @param userDto
	 */
	R<Boolean> editInfo(UserDTO userDto, String username);

	/**
	 * 修改密码
	 * @param userDTO
	 * @param username
	 */
	R<Boolean>  changeUpdate(UserDTO userDTO,String username);
}

