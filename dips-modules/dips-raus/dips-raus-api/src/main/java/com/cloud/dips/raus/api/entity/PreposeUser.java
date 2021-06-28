package com.cloud.dips.raus.api.entity;

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
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_prepose")
public class PreposeUser extends Model<PreposeUser> {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	@TableField("user_name")
	private String userName;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@TableField("modified_time")
	private LocalDateTime modifiedTime;
	/**
	 * 选中推送的主题
	 */
	@TableField("theme")
	private String theme;
	/**
	 * 失效时间
	 */
	@TableField("invalid_time")
	private Date invalidTime;
	/**
	 * 密钥
	 */
	@TableField("token")
	private String token;
	/**
	 * 成功调用次数
	 */
	@TableField("return_times")
	private Integer returnTimes;
	/**
	 * 允许调用的主题个数
	 */
	@TableField("permit_theme_counts")
	private Integer permitThemeCounts;
	/**
	 * 允许调用的字段
	 */
	@TableField("permit_column")
	private String permitColumn;
	/**
	 * 允许调用的字段个数
	 */
	@TableField("permit_column_counts")
	private Integer permitColumnCounts;
	/**
	 * 允许调用的字段个数
	 */
	@TableField("application_id")
	private Integer applicationId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
