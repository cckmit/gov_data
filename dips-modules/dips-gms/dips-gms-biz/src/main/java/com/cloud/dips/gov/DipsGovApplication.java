package com.cloud.dips.gov;


import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 国策库管理系统
 */
@EnableDipsSwagger2
@EnableDipsFeignClients
@SpringCloudApplication
@EnableAsync
public class DipsGovApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsGovApplication.class, args);
	}
}
