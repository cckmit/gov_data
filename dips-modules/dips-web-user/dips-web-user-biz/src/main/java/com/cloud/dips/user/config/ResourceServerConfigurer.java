/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.cloud.dips.common.security.component.BaseResourceServerConfigurerAdapter;

/**
 * <p>Company: 佛山国脉</p>
 * <p>@author hebo 495261798@qq.com</p>
 * <p>CreateTime: 2018年12月4日/上午9:43:02</p>
 * <p>Description:API网关配置</p>
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/actuator/**"
				, "/webusers/register",
				"/webusers/repeat",
				"/wechat/**",
				"/webusers/share",
//				"/user/register",
//				"user/repeat",
				"/v2/api-docs").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}


}
