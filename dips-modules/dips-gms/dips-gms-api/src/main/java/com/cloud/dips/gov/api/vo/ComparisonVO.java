package com.cloud.dips.gov.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class ComparisonVO implements Serializable{

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
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级)
	 */
	private List<ComparisonListVO> levelList = new ArrayList<ComparisonListVO>();
	/**
	 * 文体
	 */
	private List<ComparisonListVO> styleList = new ArrayList<ComparisonListVO>();
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
	 * 申报状态（1|即将开始  2|申报中  3|已截止）
	 */
	private List<ComparisonListVO> statusList = new ArrayList<ComparisonListVO>();
	/**
	 * 申报方式（1|自主申报  2|主管部门推荐）
	 */
	private List<ComparisonListVO> methodList = new ArrayList<ComparisonListVO>();
	/**
	 * 发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
	/**
	 * 申报对象（复选 1|企业  2|个人  3|政府  4|科研机构  5|行业协会  6|园区  7|其他）
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
	 * 扶持阶段（复选 1|事前  2|事后  3|其他） 
	 */
	private List<ComparisonListVO> modeList = new ArrayList<ComparisonListVO>();
	/**
	 * 扶持方式（复选 1|资金扶持  2|降低成本  3|评选认定  4|简化审批  5|其他） 
	 */
	private List<ComparisonListVO> supportList = new ArrayList<ComparisonListVO>();
	/**
	 * 扶持形式（复选 1|奖补  2|股权  3|投资  4|减免  5|其他）
	 */
	private List<ComparisonListVO> formalityList = new ArrayList<ComparisonListVO>();
	/**
	 * 扶持资金规模 
	 */
	private List<ComparisonListVO> fundList = new ArrayList<ComparisonListVO>();
	/**
	 * 专项资金分类
	 */
	private List<ComparisonListVO> specialList = new ArrayList<ComparisonListVO>();
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 申报条件
	 */
	private String condition;
	/**
	 * 扶持标准
	 */
	private String standard;
	/**
	 * 办理流程
	 */
	private String process;
	/**
	 * 材料递交需求
	 */
	private String requirement;
	
	/**
	 * 标识符
	 */
	private String mark;
}
