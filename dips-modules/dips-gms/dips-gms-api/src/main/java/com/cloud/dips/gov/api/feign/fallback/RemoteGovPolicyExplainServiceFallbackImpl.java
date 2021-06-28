package com.cloud.dips.gov.api.feign.fallback;

import org.springframework.stereotype.Component;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyExplainService;
import com.cloud.dips.gov.api.vo.ExplainVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j
@Component
public class RemoteGovPolicyExplainServiceFallbackImpl implements RemoteGovPolicyExplainService {
	@Setter
	private Throwable cause;



	@Override
	public ExplainVO getId(Integer id) {
		log.error("feign 查询字典信息失败:{}", cause);
		return null;
	}

	

	

}
