package com.cloud.dips.admin.api.dto;

import java.io.Serializable;

import com.cloud.dips.admin.api.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author RCG
 * @date 2018/11/18
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;

	/**
	 * 小程序会话时间
	 */
	@Setter
	@Getter
	private  String sessionKey;

	/**
	 * opne
	 */
	@Setter
	@Getter
	private  String openid;
	/**
	 * unionid
	 */
	@Setter
	@Getter
	private  String unionid;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private String[] roles;
	/**
	 * 判断用户是否存在值
	 */
	@Getter
	@Setter
	private  Integer state;
	
	/**
	 * 部门
	 */
	@Getter
	@Setter
	private String department;
	/**
	 * 层级
	 */
	@Getter
	@Setter
	private Integer level;
	/**
	 * 地域
	 */
	@Getter
	@Setter
	private String region;
	/**
	 * 角色
	 */
	@Getter
	@Setter
	private  Integer target;
	/**
	 * 规模
	 */
	@Getter
	@Setter
	private Integer scale;
	/**
	 * 岗位
	 */
	@Getter
	@Setter
	private  String job;
	/**
	 * vip等级
	 */
	@Getter
	@Setter
	private  String vipLevel;
	
	/**
	 * 所属地区
	 */
	@Getter
	@Setter
	private  String [] regionArras;

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}





}
