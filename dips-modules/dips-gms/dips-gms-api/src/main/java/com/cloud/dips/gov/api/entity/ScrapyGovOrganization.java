package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 机构模型
 *
 * @author BlackR
 * @date 2018-10-24 14:40:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_organization")
public class ScrapyGovOrganization extends Model<ScrapyGovOrganization> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 机构层级（1|国家级  2|省级  3|市级  4|区级（县级））
	 */
	private String level;
	/**
	 * 机构分类（1|人民政府  ...）
	 */
	private String classification;
	/**
	 * 机构网址
	 */
	private String url;
	/**
	 * 上传者
	 */
	private Integer creatorId;
	/**
	 * 机构介绍
	 */
	private String introduce;
	/**
	 * 机构地址
	 */
	private String address;
	/**
	 * 0:正常 1:删除
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 机构标签
	 */
	private String tag;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
