package com.cloud.dips.gov.api.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyGeneralService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j
@Component
public class RemoteGovPolicyGeneralServiceFallbackImpl implements RemoteGovPolicyGeneralService {
	@Setter
	private Throwable cause;


	@Override
	public R info(Integer id) {
		log.error("feign 查询字典信息失败:{}", cause);
		return null;
	}

	@Override
	public List<Long> gainList(Map<String, Object> params) {
		log.error("feign 查询信息失败:{}", cause);
		return null;
	}

	@Override
	public List<GovPolicyGeneral> selectByIds(List<Long> ids) {
		log.error("feign 查询信息失败:{}", cause);
		return null;
	}


}
