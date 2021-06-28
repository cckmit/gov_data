package com.cloud.dips.raus.service.impl;



import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.raus.api.entity.GovWebLog;
import com.cloud.dips.raus.mapper.GovWebLogMapper;
import com.cloud.dips.raus.service.GovWebLogService;
import com.cloud.dips.raus.util.GetIpUtil;


/**
 * 
 * @author johan
 * 2018年12月5日
 *GovWebLogServiceImpl.java
 */
@Service("govWebLogService")
public class GovWebLogServiceImpl extends ServiceImpl<GovWebLogMapper, GovWebLog> implements GovWebLogService {

	

	@Override
	public JSONObject getAllInformation(HttpServletRequest request) {
		GetIpUtil ipUtil = new GetIpUtil();
		JSONObject realIp = ipUtil.getRealIp(request);
		
		return realIp;
	}
	
	
	

}
