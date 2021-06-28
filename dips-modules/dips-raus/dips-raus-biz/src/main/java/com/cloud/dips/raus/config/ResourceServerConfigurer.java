/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.raus.config;

import com.cloud.dips.common.security.component.BaseResourceServerConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author BigPan
 * @date 2018/6/22
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers( "/actuator/**"
					, "/preposeuser/**"
					, "/social/info/**"
					, "/log/**"
					, "/dict/list/**"
					, "/dict/map"
					, "/user/register"
					, "/user/repeat"
					, "/user/one_login"
					, "/v2/api-docs","/generalPolicy/api/page","/dataSource/theme/api").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}

}
