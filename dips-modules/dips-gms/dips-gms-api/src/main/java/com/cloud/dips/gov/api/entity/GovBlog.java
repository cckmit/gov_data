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
 * 友情链接
 *
 * @author BlakcR
 * @date 2018-10-09 13:50:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_blog")
public class GovBlog extends Model<GovBlog> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 网站名
	 */
	@TableField("title")
	private String title;
	/**
	 * 网站图片
	 */
	@TableField("image")
	private String image;
	/**
	 * 网站链接
	 */
	@TableField("url")
	private String url;
	/**
	 * 网站简介
	 */
	@TableField("introduction")
	private String introduction;
	/**
	 * 优先度
	 */
	@TableField("sort")
	private Integer sort;
	/**
	 * 删除标记
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "modified_time", update = "now()")
	private Date modifiedTime;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
