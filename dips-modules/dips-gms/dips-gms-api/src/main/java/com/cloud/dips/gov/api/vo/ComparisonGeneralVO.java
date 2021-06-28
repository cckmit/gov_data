package com.cloud.dips.gov.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class ComparisonGeneralVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 索引号
	 */
	private String reference;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 发文号
	 */
	private String issue;
	/**
	 * 文体
	 */
	private List<ComparisonListVO> styleList = new ArrayList<ComparisonListVO>();
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级)
	 */
	private List<ComparisonListVO> levelList = new ArrayList<ComparisonListVO>();
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级)
	 */
	private List<ComparisonListVO> stageList = new ArrayList<ComparisonListVO>();
	/**
	 * 发文形式
	 */
	private List<ComparisonListVO> formalityList = new ArrayList<ComparisonListVO>();
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
	/**
	 * 对象（复选 1|企业  2|个人  3|政府  4|科研机构  5|行业协会  6|园区  7|其他）
	 */
	private List<ComparisonListVO> targetList = new ArrayList<ComparisonListVO>();
	
	/**
	 * 地域编码
	 */
	private List<ComparisonListVO> regionList = new ArrayList<ComparisonListVO>();;
	/**
	 * 行业
	 */
	private List<ComparisonListVO> industryList = new ArrayList<ComparisonListVO>();
	/**
	 * 适用规模（复选  1|大型企业  2|规上企业  3|中小企业  4|小微企业）
	 */
	private List<ComparisonListVO> scaleList = new ArrayList<ComparisonListVO>();
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 标识符
	 */
	private String mark;
}
