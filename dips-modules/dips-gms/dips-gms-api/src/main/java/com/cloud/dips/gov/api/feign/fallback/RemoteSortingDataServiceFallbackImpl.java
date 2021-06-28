package com.cloud.dips.gov.api.feign.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.gov.api.feign.RemoteSortingDataService;
import com.cloud.dips.gov.api.vo.SortDataVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author johan
 * 2018年12月15日
 *RemoteSortingDataServiceFallbackImpl.java
 */
@Slf4j
@Component
public class RemoteSortingDataServiceFallbackImpl implements RemoteSortingDataService {

	@Setter
	private Throwable cause;

	@Override
	public Page<SortDataVO> queryPush(String sortingDataDTO,Integer page, Integer limit,String mark) {
		log.error("feign 推送消息整合数据失败:{}", cause);
		return null;
	}

}
