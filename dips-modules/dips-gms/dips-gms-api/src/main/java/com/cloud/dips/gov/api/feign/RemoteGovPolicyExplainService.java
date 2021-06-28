package com.cloud.dips.gov.api.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.feign.factory.RemoteGovPolicyDeclareServiceFallbackFactory;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;


/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.GMS_SERVICE, fallbackFactory = RemoteGovPolicyDeclareServiceFallbackFactory.class)
public interface RemoteGovPolicyExplainService {


	@GetMapping("/policy/explain/{id}")
	 ExplainVO getId(@PathVariable("id") Integer id);
	

}
