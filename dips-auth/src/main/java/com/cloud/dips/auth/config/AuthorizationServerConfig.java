/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.auth.config;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.security.component.DipsWebResponseExceptionTranslator;
import com.cloud.dips.common.security.service.DipsClientDetailsService;
import com.cloud.dips.common.security.service.DipsUserDetailsService;

import lombok.AllArgsConstructor;

/**
 * @author Wilson
 * @date 2018/6/22
 * 认证服务器配置
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final DataSource dataSource;
	private final DipsUserDetailsService dipsUserDetailsService;
	private final AuthenticationManager authenticationManager;
	private final RedisConnectionFactory redisConnectionFactory;

	/**
	 * 用来配置客户端详情服务
	 * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
	  * @author yinzan
	  * @description //TODO
	  * @date 下午3:38 2018/12/6
	  * @param
	  * @return
	　*/
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		DipsClientDetailsService clientDetailsService = new DipsClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	/**
	 * 用来配置令牌端点(Token Endpoint)的安全约束
	 * @param oauthServer
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			//允许表单认证
			.allowFormAuthenticationForClients()
			.checkTokenAccess("permitAll()");
	}

	/**
	 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			//增加配置 允许GET POST 请求获取token 即访问端点：oauth/token
			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancer())
			//社交登录
			.userDetailsService(dipsUserDetailsService)
			.authenticationManager(authenticationManager)
			// refresh_token需要userDetailsService
			.reuseRefreshTokens(false)
			.exceptionTranslator(new DipsWebResponseExceptionTranslator());
	}


    //声明tokenStore实现
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstants.DIPS_PREFIX + SecurityConstants.OAUTH_PREFIX);
		return tokenStore;
	}
	/**
	 * 返回生成获取token
	 */

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(1);
			additionalInfo.put("license", SecurityConstants.DIPS_LICENSE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}
