package com.cloud.dips.gov.api.feign;


import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.feign.factory.RemoteGovPolicyDeclareServiceFallbackFactory;
import com.cloud.dips.gov.api.vo.GeneralVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.GMS_SERVICE, fallbackFactory = RemoteGovPolicyDeclareServiceFallbackFactory.class)
public interface RemoteGovPolicyGeneralService {


	@GetMapping("/policy/general/{id}")
	public R<GeneralVO> info(@PathVariable("id") Integer id);


	/**
	 * 根据条件获取id数组
	 *
	 * @param params
	 * @return
	 * @name yaonuan
	 */
	@PostMapping("/policy/general/ids")
	List<Long> gainList(@RequestBody Map<String, Object> params);

	/**
	 * 根据id集查询政策标题、正文信息
	 *
	 * @param ids ids集
	 * @return 政策信息
	 * @name yaonuan
	 */
	@PostMapping("/policy/general/selectByIds")
	List<GovPolicyGeneral> selectByIds(@RequestBody List<Long> ids);

}
