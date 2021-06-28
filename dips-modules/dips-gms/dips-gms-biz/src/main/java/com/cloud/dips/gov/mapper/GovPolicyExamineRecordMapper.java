package com.cloud.dips.gov.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineRecord;
import com.cloud.dips.gov.api.vo.ExamineVO;

public interface GovPolicyExamineRecordMapper extends BaseMapper<GovPolicyExamineRecord>{
	Page<GovPolicyExamineRecord> selectPolicyExamineRecordPage(Query query, Object policyId,
			 @Param("behavior") Object behavior, @Param("examineUser") Object examineUser,
			 @Param("processorName") Object processorName, @Param("title") Object title);

	List<ExamineVO> selectCountForProcessorPage(Query query,@Param("processorName") Object processorName,
			@Param("startTime") Object startTime,@Param("endTime") Object endTime,
			@Param("prop") Object prop,@Param("order") Object order);
	
	List<ExamineVO> selectExamineCount(Query query,@Param("processorName") Object processorName,
			@Param("startTime") Object startTime,@Param("endTime") Object endTime);
	
	
}
