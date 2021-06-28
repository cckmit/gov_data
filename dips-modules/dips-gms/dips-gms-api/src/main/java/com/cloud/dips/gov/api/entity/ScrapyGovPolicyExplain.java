package com.cloud.dips.gov.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author C.Z.H
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_policy_explain")
public class ScrapyGovPolicyExplain extends Model<ScrapyGovPolicyExplain> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 政策解读标题
	 */
	private String title;
	/**
	 * 解读时间
	 */
	private Date publishTime;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;
	/**
	 * 解读主体
	 */
	private String main;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标题图
	 */
	private String image;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 删除标识
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 优先级
	 */
	private String priority;
	/**
	 * 浏览次数
	 */
	private Integer views;
	/**
	 * 关联标签
	 */
	private String tag;
	/**
	 * 使用主题
	 */
	private String theme;
	/**
	 * 使用行业
	 */
	private String industry;
	/**
	 * 上传者
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
