package com.cloud.dips.raus.api.feign;

import java.util.Map;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.raus.api.entity.GovWebLog;

/**
 * 
 * @author johan
 * @Date 2018年12月6日
 * @Company 佛山司马钱技术有限公司
 * @description 数据传输层
 */
@FeignClient(value = ServiceNameConstant.RAUS_SERVICE)
public interface RemoteUserRecrodService {

	@GetMapping("/weblog/page")
	R<GovWebLog> userRecordList( @RequestParam(required = false) Map<String, Object> params);

	@GetMapping("/weblog/getip")
	R<JSONObject> getip();
}
