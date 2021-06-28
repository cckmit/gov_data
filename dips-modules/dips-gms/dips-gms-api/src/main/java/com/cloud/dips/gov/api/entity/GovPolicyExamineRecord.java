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
 * 政策审核记录
 * @author Wave 
 * @date 2019年1月3日
 * @version 1.0  
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_examine_record")
public class GovPolicyExamineRecord extends Model<GovPolicyExamineRecord> {
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
	 * 政策库关联ID
	 */
	@TableField("policy_id")
	private Integer policyId;
	
	
	/**
	 * 采集库关联ID
	 */
	@TableField("scrapy_id")
	private Integer scrapyId;
	
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
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
	
	/**
	 * 用户行为
	 */
	@TableField("behavior")
	private Integer behavior;
	
	/**
	 * 意见
	 */
	@TableField("content")
	private String content;
	
	/**
	 * 创建用户
	 */
	@TableField("create_user")
	private Integer create_user;
	
	/**
	 * 加工者
	 */
	@TableField("processor_id")
	private Integer processorId;
	
	/**
	 * 政策标题
	 */
	@TableField("title")
	private String title;
	
	/**
	 * 政策类型
	 */
	@TableField("policy_type")
	private Integer policyType;
}
