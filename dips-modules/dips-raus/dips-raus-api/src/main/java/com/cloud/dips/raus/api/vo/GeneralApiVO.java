package com.cloud.dips.raus.api.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;

/**
 * @author johan
 */
@Data
public class GeneralApiVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 正文
	 */
	private String text;
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
	private List<String> dispatchList = new ArrayList<String>();
	/**
	 * 联合发文单位（关联机构）
	 */
	private List<String> unionList = new ArrayList<String>();
	/**
	 * 标签
	 */
	private List<String> tagList = new ArrayList<String>();
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 标识符
	 */
	private String mark;
	/**
	 * 地域
	 */
	private String region;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级)
	 */
	private List<String> levelList = new ArrayList<String>();
	/**
	 * 主题
	 */
	private List<String> themeList = new ArrayList<String>();
	/**
	 * 对象（复选 1|企业  2|个人  3|政府  4|科研机构  5|行业协会  6|园区  7|其他）
	 */
	private List<String> targetList = new ArrayList<String>();
	/**
	 * 行业
	 */
	private List<String> industryList = new ArrayList<String>();
	/**
	 * 适用规模（复选  1|大型企业  2|规上企业  3|中小企业  4|小微企业）
	 */
	private List<String> scaleList = new ArrayList<String>();
}
