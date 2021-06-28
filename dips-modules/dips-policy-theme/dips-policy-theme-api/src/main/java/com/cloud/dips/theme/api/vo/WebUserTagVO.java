package com.cloud.dips.theme.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户个人标签表
 *
 * @author BigPan
 * @date 2018-12-11 10:56:16
 */
@Data
public class WebUserTagVO  {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	private Long id;
	
	/**
	 * web用户ID
	 */
	private Integer userId;
	
	/**
	 * 政策对象ID
	 */
	private Integer tagId;
	

	
	/**
	 * 0-正常，1-删除
	 */
	private String deleted;
	
	/**
	 * 收藏时间
	 */
	private LocalDateTime createTime;
	
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;

	/**
	 * 标签的key
	 */
	private  String tagKey;



}
