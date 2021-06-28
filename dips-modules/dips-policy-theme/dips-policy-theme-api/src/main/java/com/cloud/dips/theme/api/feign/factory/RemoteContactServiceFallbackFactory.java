package com.cloud.dips.theme.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.theme.api.feign.RemoteContactService;
import com.cloud.dips.theme.api.feign.fallback.RemoteContactServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteContactServiceFallbackFactory implements FallbackFactory<RemoteContactService> {

	@Override
	public RemoteContactService create(Throwable throwable) {
		RemoteContactServiceFallbackImpl remoteContactServiceFallback = new RemoteContactServiceFallbackImpl();
		remoteContactServiceFallback.setCause(throwable);
		return remoteContactServiceFallback;
	}
}
