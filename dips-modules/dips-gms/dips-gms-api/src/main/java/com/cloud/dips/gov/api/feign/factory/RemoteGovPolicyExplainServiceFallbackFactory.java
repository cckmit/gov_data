package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteGovPolicyExplainService;
import com.cloud.dips.gov.api.feign.fallback.RemoteGovPolicyExplainServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteGovPolicyExplainServiceFallbackFactory implements FallbackFactory<RemoteGovPolicyExplainService> {

	@Override
	public RemoteGovPolicyExplainService create(Throwable throwable) {
		RemoteGovPolicyExplainServiceFallbackImpl remoteDictServiceFallback = new RemoteGovPolicyExplainServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
