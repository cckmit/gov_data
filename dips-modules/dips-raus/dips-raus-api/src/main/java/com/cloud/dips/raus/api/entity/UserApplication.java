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
@TableName("web_user_application")
public class UserApplication extends Model<UserApplication> {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	@TableField("user_id")
	private Integer userId;
	/**
	 * web用户名字
	 */
	@TableField("user_name")
	private String userName;
	/**
	 * 申请人的联系名称
	 */
	@TableField("connect_name")
	private String connectName;
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
	 * 申请的公司名称
	 */
	@TableField("company")
	private String company;
	/**
	 * 订单号
	 */
	@TableField("order_id")
	private String orderId;
	/**
	 * 申请的字段
	 */
	@TableField("apply_column")
	private String applyColumn;
	/**
	 * 申请主题
	 */
	@TableField("apply_theme")
	private String applyTheme;
	/**
	 * 申请人电话
	 */
	@TableField("mobile_phone")
	private String mobilePhone;
	/**
	 * 申请人电子邮箱
	 */
	@TableField("mail")
	private String mail;
	/**
	 * 申请人备注
	 */
	@TableField("mark")
	private String mark;
	/**
	 * 失效时间
	 */
	@TableField("invalid_time")
	private Date invalidTime;
	/**
	 * 处理者名字
	 */
	@TableField("handle_name")
	private String handleName;
	/**
	 * 不成功理由
	 */
	@TableField("reason")
	private String reason;
	/**
	 * 处理结果（0-待跟进，1-成功，2-失败）
	 */
	@TableField("handle_result")
	private Integer handleResult;

	/**
	 * 金额（取值时需除以100，才为正确值）
	 */
	@TableField("money")
	private double money;
	/**
	 * 主题中文名称
	 */
	@TableField("apply_theme_name")
	private String applyThemeName;
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
