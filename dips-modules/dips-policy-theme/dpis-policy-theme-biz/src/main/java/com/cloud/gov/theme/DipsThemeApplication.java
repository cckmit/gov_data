package com.cloud.gov.theme;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 用户统一管理系统
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
@EnableAsync
@EnableScheduling
public class DipsThemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsThemeApplication.class, args);
	}
}
