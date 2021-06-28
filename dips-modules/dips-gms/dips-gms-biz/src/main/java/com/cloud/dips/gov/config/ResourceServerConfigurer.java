/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.gov.config;

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
			.antMatchers("/actuator/**"
				, "/log/**"
				, "/organization/**"
				, "/blog/**"
				, "/policy/general/**"
				, "/policy/explain/**"
				, "/policy/declare/**"
				, "/policy/information/**"
				, "/policy/sense/**"
				, "/advertisement/**","/sortingData/globalSearch"
				, "/v2/api-docs").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}

}
