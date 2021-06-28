package com.cloud.dips.gov.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineCount;
import com.cloud.dips.gov.mapper.GovPolicyExamineCountMapper;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;

import lombok.AllArgsConstructor;

@Service("govPolicyExamineCountService")
@AllArgsConstructor
public class GovPolicyExamineCountServiceImpl extends ServiceImpl<GovPolicyExamineCountMapper,GovPolicyExamineCount> implements GovPolicyExamineCountService {
	private final GovPolicyExamineCountMapper govPolicyExamineCountMapper;
	
	/**
	 * 查询统计记录
	 */
	public Page<GovPolicyExamineCount> selectPolicyExamineCountPage(Query query){
		Object policyId = query.getCondition().get("policyId");
		return govPolicyExamineCountMapper.selectPolicyExamineCountPage(query, policyId);
	}
	
	/**
	 * 保存统计记录
	 */
	public GovPolicyExamineCount save(GovPolicyExamineCount govPolicyExamineCount) {
		return this.save(govPolicyExamineCount);
	}

	@Override
	public GovPolicyExamineCount selectByProcessorId(Integer processorId) {
		return govPolicyExamineCountMapper.selectByProcessorId(processorId);
	}
}
