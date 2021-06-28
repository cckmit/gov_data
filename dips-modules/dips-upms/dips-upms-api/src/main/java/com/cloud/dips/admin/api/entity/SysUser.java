package com.cloud.dips.admin.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@TableName("gov_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getCreateTime() == null) {
			setCreateTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
		}
		if (getModifiedTime() == null) {
			setModifiedTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
		}
		if (getSalt() == null) {
			setSalt("");
		}
		if (getPhone() == null) {
			setPhone("");
		}
		if(getEmail() ==null){
			 setEmail("");
		}
		if (getAvatar() == null) {
			setAvatar("");
		}
		if (getWeixinOpenid() == null) {
			setWeixinOpenid("");
		}
		if (getQqOpenid() == null) {
			setQqOpenid("");
		}
	}

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	private String salt;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String isDeleted;

	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 微信openid
	 */
	private String weixinOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 区分是否web端和后台账号
	 */
	private String type;
	/**
	 * 登录次数
	 */
	@TableField("login_times")
	private Integer loginTimes;
	
	@TableField("is_check")
	private Integer isCheck;
	
	/**
	 * 部门
	 */
	@TableField("user_department")
	private String userDepartment;
	/**
	 * 层级
	 */
	@TableField("user_level")
	private String userLevel;
	/**
	 * 地域
	 */
	@TableField("user_region")
	private String userRegion;
	/**
	 * 角色
	 */
	@TableField("user_target")
	private  String userTarget;
	/**
	 * 规模
	 */
	@TableField("user_scale")
	private String userScale;
	/**
	 * 岗位
	 */
	@TableField("user_job")
	private  String userJob;
	
	/**
	 * 关键字
	 */
	@TableField("key_word")
	private String keyWord;
	/**
	 * vip等级
	 */
	@TableField("vip_level")
	private String vipLevel;
	/**
	 * 体验会员标志位(0：正式会员，1：体验会员）
	 */
	@TableField("is_experience")
	private String isExperience;
}
