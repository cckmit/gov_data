package com.cloud.dips.theme.api.vo;

import java.util.Date;


import lombok.Data;

/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
@Data
public class WebUserPushVO {

	
	
	/**
	 * 对象ID
	 */
	private Integer id;
	
	/**
	 * 政策ID
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
	
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 标签
	 */
	private String tagList;
	/**
	 * 主题
	 */
	private String theme;
	
	/**
	 * 主题
	 */
	private String themeName;
	

}
