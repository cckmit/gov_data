/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 关联类型表
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@TableName("gov_relation_type")
public class GovRelationType implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联类型ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 关联类型名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 关联类型编码
	 */
	@TableField("number")
	private String number;

}
