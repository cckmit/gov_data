package com.cloud.dips.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.cloud.dips.common.core.datascope.DataScopeInterceptor;

/**
 * <p>Company: 佛山国脉</p>
 * <p>@author hebo 495261798@qq.com</p>
 * <p>CreateTime: 2018年12月4日/上午9:42:56</p>
 * <p>Description:分页插件配置</p>
 */
@Configuration
@MapperScan("com.cloud.dips.user.mapper")
public class MybatisPlusConfigurer {
	/**
	 * 分页插件
	 *
	 * @return PaginationInterceptor
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	/**
	 * 数据权限插件
	 *
	 * @return DataScopeInterceptor
	 */
	@Bean
	public DataScopeInterceptor dataScopeInterceptor() {
		return new DataScopeInterceptor();
	}
}
