package com.cloud.dips.theme.api.dto;

import lombok.Data;

@Data
public class SendEmailToClineDTO {

	private Integer id;
	
	private String email;
	
	private String mark;
	
	private String title;
	
	private Integer policyId;
	
	private String userName;
}
