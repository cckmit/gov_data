package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:04:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_organization")
public class GovOrganization extends Model<GovOrganization> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 机构名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 机构层级（1|国家级  2|省级  3|市级  4|区级（县级））
	 */
	@TableField("level")
	private String level;
	/**
	 * 机构分类（1|人民政府  ...）
	 */
	@TableField("classification")
	private String classification;
	/**
	 * 机构网址
	 */
	@TableField("url")
	private String url;
	/**
	 * 机构介绍
	 */
	@TableField("introduce")
	private String introduce;
	/**
	 * 机构地址
	 */
	@TableField("address")
	private String address;
	/**
	 * 删除标志
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 上传者
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
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
