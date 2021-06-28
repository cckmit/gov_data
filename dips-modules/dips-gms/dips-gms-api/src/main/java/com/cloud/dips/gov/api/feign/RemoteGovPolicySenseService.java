package com.cloud.dips.gov.api.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.feign.factory.RemoteGovPolicyDeclareServiceFallbackFactory;




/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.GMS_SERVICE, fallbackFactory = RemoteGovPolicyDeclareServiceFallbackFactory.class)
public interface RemoteGovPolicySenseService {

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/policy/sense/{id}")
	public GovPolicySense info(@PathVariable("id") Integer id);


}
