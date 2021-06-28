package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineCount;

/**
 * 政策审核记录
 *
 * @author Wave
 * @date 2019-01-03 16:05:30
 */
public interface GovPolicyExamineCountService extends IService<GovPolicyExamineCount>{
	/**
	 * 分页查询
	 *
	 * @param query
	 * @return
	 */
	Page<GovPolicyExamineCount> selectPolicyExamineCountPage(Query query);
	
	GovPolicyExamineCount save(GovPolicyExamineCount govPolicyExamineCount);
	
	GovPolicyExamineCount selectByProcessorId(Integer processorId);
}
