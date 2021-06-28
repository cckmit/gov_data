package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;

import java.util.List;

/**
 * @author yy
 * @date 2018-10-22 11:55:59
 */
public interface ScrapyGovPolicyGeneralService extends IService<ScrapyGovPolicyGeneral> {

	Boolean copySave(ScrapyGovPolicyGeneral scrapyGovPolicyGeneral);

	Page<ScrapyGovPolicyGeneral> selectAllPage(Query query);

	List<String> listDictByNumber(String number);

	/**
	 * 通用导入申报
	 */
	Boolean copyDeclare(ScrapyGovPolicyGeneral sGeneral);

	/**
	 * 通用导入解读
	 */
	Boolean copyExplain(ScrapyGovPolicyGeneral sGeneral);

	/**
	 * 批量复制入资讯库
	 */
	Boolean copyInformation(ScrapyGovPolicyGeneral sGeneral);

	/**
	 * 批量复制入机构库
	 */
	Boolean copyOrganization(ScrapyGovPolicyGeneral sGeneral);

	/**
	 * 批量复制入常识库
	 */
	Boolean copySense(ScrapyGovPolicyGeneral sGeneral);

	Page getRecyclePage(Query query);

	void deleteRecycle(Integer id);

	void recycleRecover(Integer id);

	void batchDeleteRecycle(Integer[] ids);

	void batchRecycleRecover(Integer[] ids);

}

