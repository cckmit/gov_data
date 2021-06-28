package com.cloud.dips.tag.api.vo;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class UserTagVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer tagId;
	/**
	 * 标签名称
	 */
	private String name;
	
}