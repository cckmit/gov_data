package com.cloud.dips.gov.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author johan
 * 2018年12月15日
 *SortDataVO.java
 */
@Data
public class SortDataVO implements Serializable,Comparable<SortDataVO>{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2750235752770695837L;

	/**
	 * 政策id
	 */
	private Integer id;
	
	/**
	 * 政策类型
	 */
	private String mark;
	/**
	 * 点击量
	 */
	private String views;
	
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
	 * 申报标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();
	/**
	 * 主题key值
	 */
	private String theme;
	
	/**
	 * 主题名称
	 */
	private List<String> themeList;
	/**
	 * 申报发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>(); ;
	@Override
	public int compareTo(SortDataVO o) {
		return o.getPublishTime().compareTo(this.getPublishTime());
	}
	
	
}
