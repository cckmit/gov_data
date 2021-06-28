package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteGovPolicyGeneralService;
import com.cloud.dips.gov.api.feign.fallback.RemoteGovPolicyGeneralServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteGovPolicyGeneralServiceFallbackFactory implements FallbackFactory<RemoteGovPolicyGeneralService> {

	@Override
	public RemoteGovPolicyGeneralService create(Throwable throwable) {
		RemoteGovPolicyGeneralServiceFallbackImpl remoteDictServiceFallback = new RemoteGovPolicyGeneralServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
