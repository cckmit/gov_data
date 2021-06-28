package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteGovPolicyDeclareService;
import com.cloud.dips.gov.api.feign.fallback.RemoteGovPolicyDeclareServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteGovPolicyDeclareServiceFallbackFactory implements FallbackFactory<RemoteGovPolicyDeclareService> {

	@Override
	public RemoteGovPolicyDeclareService create(Throwable throwable) {
		RemoteGovPolicyDeclareServiceFallbackImpl remoteDictServiceFallback = new RemoteGovPolicyDeclareServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
