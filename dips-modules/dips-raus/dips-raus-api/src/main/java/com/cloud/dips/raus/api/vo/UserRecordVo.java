package com.cloud.dips.raus.api.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author johan
 * @Date 2018年12月6日
 * @Company 佛山司马钱技术有限公司
 * @description 传给前端的VO层
 */
@Data
public class UserRecordVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525002806136518801L;

	/**
	  *  编号
	 */
	private Integer id;
	
	/**
	  * 用户id
	 */
	private Integer userId;
	
	/**
	  * 用户名称
	 */
	private String userName;
	
	/**
	  * 用户操作
	 */
	private String operationType;
	
	/**
	  * 用户操作的详细信息
	 */
	private String operationDetail;
	
	/**
	  * 真实IP地址
	 */
	private String realIp;
	
	/**
	  * 代理ip地址
	 */
	private String agentIp;
	
	/**
	 * ip所属地
	 */
	private String territoryIp;
	
	/**
	  * 执行时间
	 */
	private String time;
	
	/**
	  * 状态
	 */
	private String status;
	
	/**
	  * 异常信息
	 */
	private String exception;
	
}
