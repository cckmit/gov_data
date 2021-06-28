package com.cloud.dips.gov.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.gov.api.feign.factory.RemoteSortingDataServiceFallbackFactory;
import com.cloud.dips.gov.api.vo.SortDataVO;



/**
 * 
 * @author johan
 * 2018年12月15日
 *RemoteSortingDataService.java
 */
@FeignClient(value = ServiceNameConstant.GMS_SERVICE, fallbackFactory = RemoteSortingDataServiceFallbackFactory.class)
public interface RemoteSortingDataService {

	/**
	 * 汇总推送的信息数据
	 * @param sortingDataDTO
	 * @return
	 */
	@PostMapping("/sortingData/queryPush")
	public Page<SortDataVO> queryPush(@RequestParam("jsonString") String jsonString,@RequestParam("page") Integer page, 
			@RequestParam("limit") Integer limit,@RequestParam("mark")String mark); 
}
