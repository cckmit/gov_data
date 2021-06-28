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
 * @author C.Z.H
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_policy_sense")
public class ScrapyGovPolicySense extends Model<ScrapyGovPolicySense> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 描述
	 */
	private String introduce;
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
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
