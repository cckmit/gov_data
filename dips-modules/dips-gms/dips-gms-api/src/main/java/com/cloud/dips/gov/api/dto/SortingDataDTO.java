package com.cloud.dips.gov.api.dto;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author johan
 * 2019年1月3日
 *SortingDataDTO.java
 */
@Data
public class SortingDataDTO {


	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String levelString;
	/**
	 * 对象
	 */
	private String targetString;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 规模
	 */
	private String scaleString;
	/**
	 * 地区编码
	 */
	private String region;
	/**
	 * 主题
	 */
	private String theme;
	/**
	 * 政策标识
	 */
	private String mark;
	/**
	 * 发文部门
	 */
	private String deparment;
	/**
	 * 订阅标签
	 */
	private String tagStatus;
	/**
	 * 字段订阅或标签订阅标识位(0字段订阅,1标签订阅)
	 */
	private Integer type;
}
