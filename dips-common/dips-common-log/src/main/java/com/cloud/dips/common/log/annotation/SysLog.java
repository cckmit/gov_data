/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.log.annotation;

import com.cloud.dips.common.log.enmu.EnumRole;

import java.lang.annotation.*;

import org.springframework.scheduling.annotation.Async;

/**
 * @author Wilson
 * @date 2018/6/28
 * 操作日志注解
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();

	/**
	 * 判断前后端类型
	 */
	EnumRole role() default EnumRole.USER_TYPE;


}
