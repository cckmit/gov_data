package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovOrganization;

import java.util.List;

/**
 * 机构模型
 *
 * @author BlackR
 * @date 2018-10-24 14:50:37
 */
public interface ScrapyGovOrganizationService extends IService<ScrapyGovOrganization> {
	Boolean copy(ScrapyGovOrganization scrapyGovOrganization);

	Page selectOrganizationPage(Query query);

	List<String> listDictByNumber(String number);

	Page getRecyclePage(Query query);

	void deleteRecycle(Integer id);

	void recycleRecover(Integer id);

	void batchDeleteRecycle(Integer[] ids);

	void batchRecycleRecover(Integer[] ids);
}

