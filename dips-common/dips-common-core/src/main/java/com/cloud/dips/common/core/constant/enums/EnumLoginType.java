/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BigPan
 * @date 2018/8/15
 * 社交登录类型
 */
@Getter
@AllArgsConstructor
public enum EnumLoginType {
	/**
	 * 账号密码登录
	 */
	PWD("PWD", "账号密码登录"),

	/**
	 * QQ登录
	 */
	QQ("QQ", "QQ登录"),

	/**
	 * 微信登录
	 */
	WECHAT("WX", "微信登录"),

	/**
	 * 微信公众号授权登录
	 */
	APPLETS_LOGIN("Applets", "小程序登录"),
	USER_TYPE("0", "后端账号"),
	WEB_TYE("1", "web端账号"),
	IS_Experience("1", "体验版会员"),
	NOT_Experience("0", "正式会员");

	/**
	 * 类型
	 */
	private final String type;
	/**
	 * 描述
	 */
	private final String description;
	
}
