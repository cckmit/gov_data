package com.cloud.dips.theme.api.vo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;

@Data
public class WebusercontactVo implements Serializable{
	
	private static final long serialVersionUID = 2640363603930287919L;

	
	/**
	 * 主题key值
	 */
	private String keys;

	/**
	 * 主题id
	 */
	private String theme;

	/**
	 * web用户ID
	 */
	private Integer webUserId;

	/**
	 * 角色
	 */
	private  String target;

	/**
	 * 部门
	 */
	private String  department;

	/**
	 * 所属行业
	 */
	private String industry;

	/**
	 * 部门层级
	 */
	private String level;

	/**
	 * 所属区域
	 */
	private String region;
	/**
	 * 企业规模
	 */
	private String scale;

	/**
	 * 岗位
	 */
	private String job;
	
	/**
	 * 用户订阅的标签
	 */
	private String tagStatus;

	/**
	 * 地区名称
	 */
	private String regionName;
	/**
	 * 字段订阅或标签订阅标识位(0字段订阅,1标签订阅)
	 */
	private Integer type;
	/**
	 * 是否通过邮箱推送(0-否1-是）
	 */
	private Integer isMail;
}
