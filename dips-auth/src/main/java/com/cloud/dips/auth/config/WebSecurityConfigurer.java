/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cloud.dips.common.security.handler.MobileLoginSuccessHandler;
import com.cloud.dips.common.security.mobile.MobileSecurityConfigurer;
import com.cloud.dips.common.security.service.DipsUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author BigPan
 * @date 2018/6/22
 * 认证相关配置
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private DipsUserDetailsService userDetailsService;
	@Lazy
	@Autowired
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(
				"/actuator/**",
				"/oauth/removeToken",
				"/oauth/delToken/*",
				"/oauth/listToken",
				"/mobile/**").permitAll()
			.anyRequest().authenticated()
			//由于使用的是JWT，我们这里不需要csrf
			.and().csrf().disable()
			.apply(mobileSecurityConfigurer());
	}

	/**
	 *  Spring Boot 2 配置，这里要bean 注入
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	/**
	 * 用户认证
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
		return MobileLoginSuccessHandler.builder() 
			.objectMapper(objectMapper)
			.clientDetailsService(clientDetailsService)
			.passwordEncoder(passwordEncoder())
			.defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
	}

	/**
	 * 手机端配置
	 * @return
	 */
	@Bean
	public MobileSecurityConfigurer mobileSecurityConfigurer() {
		MobileSecurityConfigurer mobileSecurityConfigurer = new MobileSecurityConfigurer();
		mobileSecurityConfigurer.setMobileLoginSuccessHandler(mobileLoginSuccessHandler());
		mobileSecurityConfigurer.setUserDetailsService(userDetailsService);
		return mobileSecurityConfigurer;
	}


	/**
	 * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-d#password-storage-updated
	 * Encoded password does not look like BCrypt
	 * 密码验证机制
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder()	 {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
