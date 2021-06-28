package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteGovInformationService;
import com.cloud.dips.gov.api.feign.fallback.RemoteGovInformationServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteGovInformationServiceFallbackFactory implements FallbackFactory<RemoteGovInformationService> {

	@Override
	public RemoteGovInformationService create(Throwable throwable) {
		RemoteGovInformationServiceFallbackImpl remoteDictServiceFallback = new RemoteGovInformationServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
