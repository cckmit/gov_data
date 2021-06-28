package com.cloud.dips.raus;


import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 
 * @author johan
 * 2018年12月4日
 * 启动类
 */
@EnableDipsSwagger2
@EnableDipsFeignClients
@SpringCloudApplication
public class DipsRausApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsRausApplication.class, args);
	}
}
