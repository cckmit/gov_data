package com.cloud.dips.theme.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户d订阅主题推送表
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_push")
public class WebUserPush extends Model<WebUserPush> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
	private Long id;
	/**
	 * web用户ID
	 */
	@TableField("web_user_id")
	private Integer webUserId;
	/**
	 * 订阅的政策id（用于增量邮箱推送）
	 */
	@TableField("policy_id")
	private Integer policyId;

	/**
	 * 订阅政策类型（邮箱增量推送）
	 */
	@TableField("mark")
	private String mark;
	/**
	 * 是否推送（0-没推送，1-已推送）
	 */
	@TableField("is_push")
	private Integer isPush;
	/**
	 * 政策标题
	 */
	@TableField("title")
	private String title;
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
