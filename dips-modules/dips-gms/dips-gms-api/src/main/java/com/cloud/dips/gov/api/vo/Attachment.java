package com.cloud.dips.gov.api.vo;

import lombok.Data;
/**
 * 
 * @author C.Z.H
 * 2019年1月3日
 *Attachment.java
 */
@Data
public class Attachment {
	/**
	 * 存储路径
	 */
	private String url;
	/**
	 * 文件名加后缀
	 */
	private String fileName;

}
