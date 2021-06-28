package com.cloud.dips.user.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author BigPan
 * @date 2018-12-05 16:47:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_users")
public class WebUsers extends Model<WebUsers> {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Integer id;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	private String salt;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 成长值
	 */
	private Integer growthValue;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 账户余额
	 */
	private BigDecimal account;
	/**
	 * 会员到期时间
	 */
	private LocalDateTime membershipExpirationTime;
	/**
	 * 微博UID
	 */
	private String weboUid;
	/**
	 * 微信OpenId
	 */
	private String weixinOpenid;
	/**
	 * QQOpenId
	 */
	private String qqOpenid;
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	/**
	 * 最后登录时间
	 */
	private LocalDateTime lastLoginTime;
	/**
	 * 0-正常，1-删除
	 */
	private String isDeleted;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
