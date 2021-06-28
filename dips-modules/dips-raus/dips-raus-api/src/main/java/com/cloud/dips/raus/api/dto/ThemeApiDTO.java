package com.cloud.dips.raus.api.dto;

import java.io.Serializable;


import lombok.Data;

/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@Data
public class ThemeApiDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密钥
	 */
	private String token;
	/**
	 * 页码
	 */
	private Integer page;
	/**
	 * 调用时间
	 */
	private String queryTime;

}
