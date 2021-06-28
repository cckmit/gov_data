package com.cloud.dips.admin.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于接收社交登录参数扩展
 * @author yinzan
 * @ClassName: SocialVO
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/10下午3:01
 */
@Data
public class SocialVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appId;

	private  String code;

	private String iv;


	private String  encryptedData;




}
