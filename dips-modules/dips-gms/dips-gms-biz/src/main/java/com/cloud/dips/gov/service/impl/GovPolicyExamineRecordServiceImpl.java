package com.cloud.dips.gov.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineRecord;
import com.cloud.dips.gov.api.vo.ExamineVO;
import com.cloud.dips.gov.mapper.GovPolicyExamineRecordMapper;
import com.cloud.dips.gov.service.GovPolicyExamineRecordService;

import lombok.AllArgsConstructor;

@Service("govPolicyExamineRecordService")
@AllArgsConstructor
public class GovPolicyExamineRecordServiceImpl extends ServiceImpl<GovPolicyExamineRecordMapper,GovPolicyExamineRecord> implements GovPolicyExamineRecordService {
	private final GovPolicyExamineRecordMapper govPolicyExamineRecordMapper;
	
	/**
	 * 查询审核记录
	 */
	public Page<GovPolicyExamineRecord> selectPolicyExamineRecordPage(Query query){
		Object policyId = query.getCondition().get("policyId");
		Object behavior = query.getCondition().get("behavior");
		Object examineUser = query.getCondition().get("examineUser");
		Object processorName = query.getCondition().get("processorName");
		Object title = query.getCondition().get("title");
		return govPolicyExamineRecordMapper.selectPolicyExamineRecordPage(query, policyId,behavior,examineUser,processorName,title);
	}
	
	/**
	 * 保存审核记录
	 */
	public GovPolicyExamineRecord save(GovPolicyExamineRecord govPolicyExamineRecord) {
		return this.save(govPolicyExamineRecord);
	}

	@Override
	public Page<ExamineVO> selectCountForProcessorPage(Query query) {
		Object processorName = query.getCondition().get("processorName");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
		List examineCountPage= govPolicyExamineRecordMapper.selectCountForProcessorPage(query,processorName,startTime,endTime,prop,order);
        query.setRecords(examineCountPage);
        return query;
	}

	@Override
	public List<ExamineVO> selectExamineCount(Query query) {
		Object processorName = query.getCondition().get("processorName");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
		return govPolicyExamineRecordMapper.selectExamineCount(query,processorName,startTime,endTime);
	}
}
