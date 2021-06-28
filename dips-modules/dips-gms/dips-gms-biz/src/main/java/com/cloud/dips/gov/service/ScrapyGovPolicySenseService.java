package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicySense;

/**
 * 政策常识采集
 *
 * @author yy
 * @date 2018-10-22 11:55:59
 */
public interface ScrapyGovPolicySenseService extends IService<ScrapyGovPolicySense> {

	Boolean copySave(ScrapyGovPolicySense scrapyGovPolicySense);

	Page<ScrapyGovPolicyDeclare> selectAllScrapyGovPolicySense(Query query);

	Page getRecyclePage(Query query);

	void recoverRecycle(Integer id);
}

