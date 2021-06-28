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
 * 政策解读
 *
 * @author C.Z.H
 * @date 2018-09-11 11:27:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_explain")
public class GovPolicyExplain extends Model<GovPolicyExplain> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 政策解读标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 解读时间
	 */
	@TableField(value = "publish_time", strategy = FieldStrategy.IGNORED)
	private Date publishTime;
	/**
	 * 来源
	 */
	@TableField("source")
	private String source;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	@TableField("level")
	private String level;
	/**
	 * 解读主体
	 */
	@TableField("main")
	private String main;
	/**
	 * 摘要
	 */
	@TableField("summary")
	private String summary;
	/**
	 * 正文
	 */
	@TableField("text")
	private String text;
	/**
	 * 标题图
	 */
	@TableField("image")
	private String image;
	/**
	 * 原文链接
	 */
	@TableField("url")
	private String url;
	/**
	 * 删除标识
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 优先级
	 */
	@TableField("priority")
	private String priority;
	/**
	 * 浏览次数
	 */
	@TableField("views")
	private Integer views;
	/**
	 * 创建人ID
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 适用行业
	 */
	@TableField("industry")
	private String industry;
	/**
	 * 主题
	 */
	@TableField("theme")
	private String theme;
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
	 * 地域
	 */
	@TableField("region")
	private String region;
	
	/**
	 * 地域编码
	 */
	@TableField("region_arr")
	private String regionArray;
	/**
	 * 附件路径
	 */
	@TableField("file")
	private String file;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
	
	/**
	 * 提交时间
	 */
	@TableField("commit_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date commitTime;
}
