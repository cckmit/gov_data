package com.cloud.dips.theme.api.dto;

import java.time.LocalDateTime;


import lombok.Data;

/**
 * 
 *
 * @author johan
 * @date 2018-12-10
 */
@Data
public class WebUserPushDTO {


	/**
	 * 用户订阅主题记录ID
	 */
	private Integer id;

	/**
	 * web用户ID
	 */
	private Integer webUserId;

	/**
	 * 订阅主题ID
	 */
	private String dictValueId;

	/**
	 * 0-正常，1-删除
	 */
	private String deleted;

	/**
	 * 主题key值
	 */
	private String dictValueKey;
	/**
	 * 角色
	 */
	private  String targetString;

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
	private String levelString;

	/**
	 * 地区
	 */
	private String region;
	/**
	 * 企业规模
	 */
	private String scaleString;

	/**
	 * 岗位
	 */
	private  String job;
	
}
