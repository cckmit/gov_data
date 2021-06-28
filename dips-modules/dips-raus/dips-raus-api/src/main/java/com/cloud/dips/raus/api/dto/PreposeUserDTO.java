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
public class PreposeUserDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户名
	 */
	private Integer userId;
	/**
	 * 选中推送的主题
	 */
	private String theme;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 密钥
	 */
	private String token;
	/**
	 * 允许调用的主题个数
	 */
	private Integer permitThemeCounts;
	/**
	 * 允许调用的字段
	 */
	private String permitColumn;
	/**
	 * 允许调用的字段个数
	 */
	private Integer permitColumnCounts;

}
