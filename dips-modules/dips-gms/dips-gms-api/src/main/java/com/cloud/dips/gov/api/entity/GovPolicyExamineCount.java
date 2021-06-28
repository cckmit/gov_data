package com.cloud.dips.gov.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 政策审核统计
 * @author Wave 
 * @date 2019年1月3日
 * @version 1.0  
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_examine_count")
public class GovPolicyExamineCount extends Model<GovPolicyExamineCount>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
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
	 * 通过次数
	 */
	@TableField("agree_count")
	private Integer agreeCount;
	
	/**
	 * 不通过次数
	 */
	@TableField("disagree_count")
	private Integer disagreeCount;
	
	/**
	 * 加工者
	 */
	@TableField("processor_id")
	private Integer processorId;
	
	/**
	 * 当前分值
	 */
	@TableField("mark")
	private Double mark;
	
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
	
}
