package com.cloud.dips.raus.api.dto;

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
public class UserApplicationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 申请人的网站id
	 */
	private Integer userId;
	/**
	 * 申请人的网站名字
	 */
	private String userName;
	/**
	 * 申请人的联系姓名
	 */
	private String connectName;
	/**
	 * 申请人的公司名称
	 */
	private String company;
	/**
	 * 申请的字段
	 */
	private String applyColumn;
	/**
	 * 申请主题
	 */
	private String applyTheme;
	/**
	 * 申请人电话
	 */
	private String mobilePhone;
	/**
	 * 申请人电子邮箱
	 */
	private String mail;
	/**
	 * 申请人备注
	 */
	private String mark;
	/**
	 * 金额
	 */
	private double money;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 处理者名字
	 */
	private String handleName;
	/**
	 * 不成功理由
	 */
	private String reason;
	/**
	 * 处理结果（0-待跟进，1-成功，2-失败）
	 */
	private Integer handleResult;
	/**
	 * 主题中文名称
	 */
	private String applyThemeName;

}
