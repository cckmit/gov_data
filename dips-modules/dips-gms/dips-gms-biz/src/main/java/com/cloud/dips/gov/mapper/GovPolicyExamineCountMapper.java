package com.cloud.dips.gov.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineCount;

public interface GovPolicyExamineCountMapper extends BaseMapper<GovPolicyExamineCount>{
	
	Page<GovPolicyExamineCount> selectPolicyExamineCountPage(Query query, @Param("policyId") Object policyId);

	GovPolicyExamineCount selectByProcessorId(@Param("processorId") Integer processorId);
}
