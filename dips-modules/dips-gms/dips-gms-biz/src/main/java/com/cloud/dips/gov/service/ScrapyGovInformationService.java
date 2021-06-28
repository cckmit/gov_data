package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovInformation;


/**
 * 政策资讯模型
 *
 * @author CUI.CAN
 * @date 2018-10-24 15:27:13
 */
public interface ScrapyGovInformationService extends IService<ScrapyGovInformation> {

	Boolean copy(ScrapyGovInformation scrapyGovInformation);

	Page<ScrapyGovInformation> selectAllPage(Query query);

	Page getRecyclePage(Query query);

	void deleteRecycle(Integer id);

	void recycleRecover(Integer id);

	void batchDeleteRecycle(Integer[] ids);

	void batchRecycleRecover(Integer[] ids);


}
