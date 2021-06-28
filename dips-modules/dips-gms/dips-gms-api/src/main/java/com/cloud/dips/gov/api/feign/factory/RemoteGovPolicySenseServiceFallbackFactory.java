package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteGovPolicySenseService;
import com.cloud.dips.gov.api.feign.fallback.RemoteGovPolicySenseServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteGovPolicySenseServiceFallbackFactory implements FallbackFactory<RemoteGovPolicySenseService> {

	@Override
	public RemoteGovPolicySenseService create(Throwable throwable) {
		RemoteGovPolicySenseServiceFallbackImpl remoteDictServiceFallback = new RemoteGovPolicySenseServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
