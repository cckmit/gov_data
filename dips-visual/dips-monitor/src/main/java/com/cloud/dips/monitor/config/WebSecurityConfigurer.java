package com.cloud.dips.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * WebSecurityConfigurer
 *
 * @author BigPan
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	private final String adminContextPath;

	public WebSecurityConfigurer(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");
		http
			.headers().frameOptions().disable()
			//对请求做一个授权
			.and().authorizeRequests()
			// 如果有允许匿名的url，填在下面
			.antMatchers(adminContextPath + "/assets/**"
				, adminContextPath + "/login"
				, adminContextPath + "/actuator/**"
			).permitAll()
			//任何请求都要做一个身份认证
			.anyRequest().authenticated()
			.and()
			//登录页面配置
			.formLogin().loginPage(adminContextPath + "/login")
			.successHandler(successHandler).and()
			//登录退出
			.logout().logoutUrl(adminContextPath + "/logout")
			.and()
			.httpBasic().and()
			.csrf()
			.disable();
		// @formatter:on
	}
}
