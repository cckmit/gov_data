package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author C.Z.H
 * 2019年1月3日
 *ScrapyGovInformation.java
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_information")
public class ScrapyGovInformation extends Model<ScrapyGovInformation> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标签
	 */
	private String title;
	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 标题图
	 */
	private String image;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 是否删除
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 创建者
	 */
	private Integer creatorId;
	
	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	
	/**
	 * 审核状态
	 */
	private Integer examineStatus;
	
	/**
	 * 审核人
	 */
	@TableField("examine_user_id")
	private Integer examineUser;
	
	/**
	 * 审核日期
	 */
	private Date examineDate;
	
	/**
	 * 退回次数
	 */
	private Integer retreatCount;
	
	/**
	 * 退回人
	 */
	private Integer retreatUser;
	
	/**
	 * 退回原因
	 */
	private String retreatContent;
	
	/**
	 * 加工者
	 */
	private Integer processorId;
}
