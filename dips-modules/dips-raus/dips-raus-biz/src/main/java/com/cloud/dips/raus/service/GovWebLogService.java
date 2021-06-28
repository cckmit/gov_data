package com.cloud.dips.raus.service;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.raus.api.entity.GovWebLog;

/**
 * 日志表
 *
 * @author BigPan
 * @date 2018-12-04 16:48:44
 */
public interface GovWebLogService extends IService<GovWebLog> {
	
	/**
	  *  获取用户的ip和归属地
	 * @param request
	 * @return
	 */
	JSONObject getAllInformation(HttpServletRequest request);
}

