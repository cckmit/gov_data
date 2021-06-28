package com.cloud.dips.gov.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 机构模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:04:59
 */
@Data
public class OrganizationVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 机构层级（1|国家级  2|省级  3|市级  4|区级（县级））
	 */
	private String level;
	private String levelName;
	/**
	 * 机构分类（1|人民政府  ...）
	 */
	private String classification;
	private String classificationName;
	/**
	 * 机构标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();
	/**
	 * 机构网址
	 */
	private String url;
	/**
	 * 机构介绍
	 */
	private String introduce;
	/**
	 * 机构地址
	 */
	private String address;
	/**
	 * 上传者id
	 */
	private Integer creatorId;
	/**
	 * 生成时间
	 */
	private Date createTime;
	/**
	 * 上传者Name
	 */
	private String creatorName;
	/**
	 * 后一篇
	 * */
	private CommonVO next;
	/**
	 * 前一篇
	 * */
	private CommonVO prev;
	/**
	 * 采集库关联ID
	 */
	private Integer scrapyId;
}
