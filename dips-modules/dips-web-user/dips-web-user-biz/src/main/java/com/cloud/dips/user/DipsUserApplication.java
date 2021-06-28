/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.user;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;

/**
 * <p>Company: 佛山国脉</p>
 * <p>@author hebo 495261798@qq.com</p>
 * <p>CreateTime: 2018年12月4日/上午9:42:14</p>
 * <p>Description:web前台用户业务处理模块【用户认证和个人中心】，项目启动类</p>
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class DipsUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsUserApplication.class, args);
	}

}
