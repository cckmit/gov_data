package com.cloud.dips.theme.api.vo;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;

/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
@Data
public class WebUserFootprintVO {

	/**
	 * 自增ID
	 */
	private Long id;
	
	
	/**
	 * 政策对象ID
	 */
	private Integer policyId;
	
	/**
	 * 政策类型
	 */
	private String policyType;
	

	/**
	 * 政策标题
	 */
	private String title;
	

}
