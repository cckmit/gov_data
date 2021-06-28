/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import com.cloud.dips.admin.api.feign.factory.RemoteDeptServiceFallbackFactory;
import com.cloud.dips.admin.api.vo.DeptCityVO;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Wilson
 * @date 2018/6/22
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteDeptServiceFallbackFactory.class)
public interface RemoteDeptService {
	/**
	 * 机构 城市 集合
	 *
	 * @return R
	 */
	@GetMapping("/dept/city/list")
	List<DeptCityVO> list();

}
