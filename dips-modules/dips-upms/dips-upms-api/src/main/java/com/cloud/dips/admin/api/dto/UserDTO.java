package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.data.annotation.Transient;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	private List<Integer> role;

	/**
	 * 部门Id
	 */
	private Integer deptId;

	/**
	 * 新密码
	 */
	private String newpassword1;

	/**
	 * 确认密码
	 */
	private  String  checkPass;
	
	
}
