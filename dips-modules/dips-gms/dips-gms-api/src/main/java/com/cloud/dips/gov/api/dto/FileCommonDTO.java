package com.cloud.dips.gov.api.dto;



import java.io.Serializable;

import lombok.Data;

/**
 * @author C.Z.H
 */
@Data
public class FileCommonDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 *路径 
	 */
	private String url;
}
