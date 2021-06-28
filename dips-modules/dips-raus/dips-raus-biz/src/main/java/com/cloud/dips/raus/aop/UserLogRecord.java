package com.cloud.dips.raus.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author johan
 * @Date 2018年12月5日
 * @Company 佛山司马钱技术有限公司
 * @description 自定义注解
 */

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLogRecord {
	
	/**
	  * 日志描述
	 * @return
	 */
	String description()  default ""; 
	
	/**
	  * 操作类型
	 * @return
	 */
	String operationType()  default ""; 
}
