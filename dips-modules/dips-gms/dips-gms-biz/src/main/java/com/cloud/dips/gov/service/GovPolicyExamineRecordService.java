package com.cloud.dips.gov.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyExamineRecord;
import com.cloud.dips.gov.api.vo.ExamineVO;


/**
 * 政策审核记录
 *
 * @author Wave
 * @date 2019-01-03 16:05:30
 */
public interface GovPolicyExamineRecordService extends IService<GovPolicyExamineRecord>{
	
	/**
	 * 分页查询
	 *
	 * @param query
	 * @return
	 */
	Page<GovPolicyExamineRecord> selectPolicyExamineRecordPage(Query query);
	
	GovPolicyExamineRecord save(GovPolicyExamineRecord govPolicyExamineRecord);

	Page<ExamineVO> selectCountForProcessorPage(Query query);

	List<ExamineVO>  selectExamineCount(Query query);
	
}
