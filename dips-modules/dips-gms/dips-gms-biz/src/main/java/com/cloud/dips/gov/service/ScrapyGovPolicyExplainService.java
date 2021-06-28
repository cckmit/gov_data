package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyExplain;

import java.util.List;

/**
 * 政策解读模型
 *
 * @author zx
 */
public interface ScrapyGovPolicyExplainService extends IService<ScrapyGovPolicyExplain> {
	Boolean copy(ScrapyGovPolicyExplain scrapyGovPolicyExplain);

	Page<ScrapyGovPolicyExplain> selectAllPage(Query query);

	List<String> listDictByNumber(String number);

	Page<ScrapyGovPolicyExplain> getRecyclePage(Query query);

	Boolean deleteRecycle(Integer id);

	Boolean recycleRecover(Integer id);

	Boolean batchDeleteRecycle(Integer[] ids);

	Boolean batchRecycleRecover(Integer[] ids);
}
