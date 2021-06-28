package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import com.cloud.dips.gov.api.vo.ComparisonVO;

import java.util.List;

/**
 * 申报政策模型
 *
 * @author BigPan
 * @date 2018-10-24 11:25:41
 */
public interface ScrapyGovPolicyDeclareService extends IService<ScrapyGovPolicyDeclare> {
	Boolean copy(ScrapyGovPolicyDeclare scrapyGovPolicyDeclare);

	Page<ScrapyGovPolicyDeclare> selectAllPage(Query<Object> query);

	List<String> listDictByNumber(String number);

	Page getRecyclePage(Query query);

	void recoverRecycle(Integer id);
}

