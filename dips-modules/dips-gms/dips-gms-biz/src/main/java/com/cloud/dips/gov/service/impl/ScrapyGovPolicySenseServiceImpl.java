package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicySense;
import com.cloud.dips.gov.mapper.GovPolicySenseMapper;
import com.cloud.dips.gov.mapper.ScrapyGovPolicySenseMapper;
import com.cloud.dips.gov.service.ScrapyGovPolicySenseService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Jiadong
 */
@Service("scrapyGovPolicySenseService")
@AllArgsConstructor
public class ScrapyGovPolicySenseServiceImpl extends ServiceImpl<ScrapyGovPolicySenseMapper, ScrapyGovPolicySense> implements ScrapyGovPolicySenseService {

	private final GovPolicySenseMapper senseMapper;
	private final ScrapyGovPolicySenseMapper scrapySenseMapper;
	private final RemoteTagRelationService remoteTagRelationService;

	@Override
	public Boolean copySave(ScrapyGovPolicySense scrapy) {
		// TODO Auto-generated method stub
		if ("2".equals(scrapy.getDelFlag())) {
			return false;
		}
		scrapy.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(scrapy);
		GovPolicySense sense = new GovPolicySense();
		sense.setSummary(scrapy.getSummary());
		sense.setIntroduce(scrapy.getIntroduce());
		sense.setKeywords(scrapy.getKeywords());
		sense.setPublishTime(scrapy.getPublishTime());
		sense.setSource(scrapy.getSource());
		sense.setText(scrapy.getText());
		sense.setTitle(scrapy.getTitle());
		Integer userId = SecurityUtils.getUser().getId();
		sense.setCreatorId(userId);
		sense.setDelFlag("0");
		sense.setCreateTime(new Date());
		//审核流程初始化
		sense.setScrapyId(scrapy.getId());
		sense.setProcessorId(SecurityUtils.getUser().getId());
		sense.setExamineStatus(0);
		senseMapper.insert(sense);

		//关联标签
		Map<String, Object> map = new HashMap<>(0);
		map.put("node", "gov_policy_sense");
		map.put("relationId", sense.getId());
		map.put("tagKeyWords", scrapy.getTag());
		remoteTagRelationService.saveTagRelation(map);

		scrapy.setDelFlag("2");
		this.updateById(scrapy);

		return true;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovPolicyDeclare> selectAllScrapyGovPolicySense(Query query) {
		Object senseTitle = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scrapySenseMapper.selectScrapyPolicySenseList(query, senseTitle, username,prop,order,exceptExamined));
		return query;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page getRecyclePage(Query query) {
		Object senseTitle = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(scrapySenseMapper.getRecyclePage(query, senseTitle, username));
		return query;
	}

	@Override
	public void recoverRecycle(Integer id) {
		ScrapyGovPolicySense sense = scrapySenseMapper.selectById(id);
		sense.setDelFlag(CommonConstant.STATUS_NORMAL);
		scrapySenseMapper.updateById(sense);
	}

}
