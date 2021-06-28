package com.cloud.dips.gov.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.gov.api.feign.RemoteSortingDataService;
import com.cloud.dips.gov.api.feign.fallback.RemoteSortingDataServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @author johan
 * 2018年12月15日
 *RemoteSortingDataServiceFallbackFactory.java
 */
@Component
public class RemoteSortingDataServiceFallbackFactory implements FallbackFactory<RemoteSortingDataService>{

	@Override
	public RemoteSortingDataService create(Throwable cause) {
		RemoteSortingDataServiceFallbackImpl sortingDataServiceFallback = new RemoteSortingDataServiceFallbackImpl();
		sortingDataServiceFallback.setCause(cause);
		return sortingDataServiceFallback;
	}

}
