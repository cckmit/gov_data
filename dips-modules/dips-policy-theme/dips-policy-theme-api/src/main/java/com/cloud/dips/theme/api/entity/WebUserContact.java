package com.cloud.dips.theme.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Transient;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 * @author BigPan
 * @date 2018-12-07 10:33:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_contact")
public class WebUserContact extends Model<WebUserContact> {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户订阅主题记录ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * web用户ID
	 */
	@TableField("web_users_id")
	private Integer webUserId;

	/**
	 * 订阅主题ID
	 */
	@TableField("dict_value_id")
		private String dictValueId;

	/**
	 * 0-正常，1-删除
	 */
	@TableField("is_deleted")
	private String deleted;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(value = "modified_time", update = "now()")
	private LocalDateTime modifiedTime;

	/**
	 * 主题key值
	 */
	@TableField("dict_value_key")
	private String dictValueKey;

	/**
	 * 角色
	 */
	@TableField("target")
	private  String target;

	/**
	 * 部门
	 */
	@TableField("deparment")
	private String  department;

	/**
	 * 所属行业
	 */
	private String industry;

	/**
	 * 部门层级
	 */
	@TableField("level")
	private String level;

	/**
	 * 地区
	 */
	private String region;
	/**
	 * 企业规模
	 */
	@TableField("scale")
	private String scale;

	/**
	 * 岗位
	 */
	private  String job;
	
	/**
	 * 用户订阅的标签
	 */
	@TableField("tag_status")
	private String tagStatus;
	
	
	/**
	 * 地区名称
	 */
	@TableField("region_name")
	private String regionName;
	/**
	 * 字段订阅或标签订阅标识位
	 */
	@TableField("type")
	private Integer type;
	/**
	 * 是否通过邮箱推送(0-否1-是）
	 */
	@TableField("is_mail")
	private Integer isMail;
	
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
