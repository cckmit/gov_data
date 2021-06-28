/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.log.util;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.StrUtil;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.common.core.constant.CommonConstant;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 系统日志工具类
 *
 * @author L.cm
 */
public class SysLogUtils {
	public static SysLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		SysLog sysLog = new SysLog();
		sysLog.setCreateBy(Objects.requireNonNull(getUsername()));
		sysLog.setType(CommonConstant.STATUS_NORMAL);
		sysLog.setRemoteAddr(HttpUtil.getClientIP(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			AtomicReference<String> pValues = new AtomicReference<>("");
			Arrays.stream(entry.getValue()).forEach(s -> pValues.getAndAccumulate(s, (s1, s2) -> StrUtil.isBlank(s1) ? s2 : s1 + "," + s2));
			params.append(entry.getKey()).append(":").append(pValues).append(";");
		}

		sysLog.setParams(params.toString());
		if(Optional.ofNullable(request.getParameterMap()).isPresent()){
			Map<Object, Object> attribute = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			for (Map.Entry<Object,Object> enjoys : attribute.entrySet()) {
				params.append(enjoys.getKey()).append(":").append(enjoys.getValue()).append(";");
			}
			sysLog.setParams(params.toString());
		}
		sysLog.setServiceId(getClientId());
		return sysLog;
	}

	/**
	 * 获取客户端
	 *
	 * @return clientId
	 */
	private static String getClientId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		return null;
	}

	/**
	 * 获取用户名称
	 *
	 * @return username
	 */
	private static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}

		return authentication.getName();
	}

}
