package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 政策常识模型
 *
 * @author Z.Y.S
 * @date 2018-09-11 10:14:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_sense")
public class GovPolicySense extends Model<GovPolicySense> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 关键词
	 */
	@TableField("keywords")
	private String keywords;
	/**
	 * 描述
	 */
	@TableField("introduce")
	private String introduce;
	/**
	 * 发文时间
	 */
	@TableField(value = "publish_time", strategy = FieldStrategy.IGNORED)
	private Date publishTime;
	/**
	 * 来源
	 */
	@TableField("source")
	private String source;
	/**
	 * 简介
	 */
	@TableField("summary")
	private String summary;
	/**
	 * 正文
	 */
	@TableField("text")
	private String text;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 创建者
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "modified_time", update = "now()")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date modifiedTime;
	
	/**
	 * 标记
	 */
	@TableField("mark")
	private String mark;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	/**
	 * 采集库关联ID
	 */
	private Integer scrapyId;
	
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
