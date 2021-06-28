package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.entity.GovOrganization;
import com.cloud.dips.gov.api.entity.ScrapyGovOrganization;
import com.cloud.dips.gov.mapper.ScrapyGovOrganizationMapper;
import com.cloud.dips.gov.service.GovOrganizationService;
import com.cloud.dips.gov.service.ScrapyGovOrganizationService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */

@Service("scrapyGovOrganizationService")
@AllArgsConstructor
public class ScrapyGovOrganizationServiceImpl extends ServiceImpl<ScrapyGovOrganizationMapper, ScrapyGovOrganization> implements ScrapyGovOrganizationService {

	private final RemoteTagRelationService remoteTagRelationService;
	private final ScrapyGovOrganizationMapper scrapyGovOrganizationMapper;
	private final GovOrganizationService govOrganizationService;

	@Override
	public Boolean copy(ScrapyGovOrganization scrapyGovOrganization) {
		GovOrganization organization = new GovOrganization();
		organization.setName(scrapyGovOrganization.getName());
		organization.setUrl(scrapyGovOrganization.getUrl());
		organization.setIntroduce(scrapyGovOrganization.getIntroduce());
		organization.setAddress(scrapyGovOrganization.getAddress());
		// 机构层级
		organization.setLevel(scrapyGovOrganizationMapper.selectDictKeyByNumberAndValue("POLICY_LEVEL", scrapyGovOrganization.getLevel()));
		// 机构分类
		organization.setClassification(scrapyGovOrganizationMapper.selectDictKeyByNumberAndValue("ORGANIZATION_CLASSIFICATION", scrapyGovOrganization.getClassification()));
		organization.setCreatorId(SecurityUtils.getUser().getId());
		organization.setDelFlag(CommonConstant.STATUS_NORMAL);
		organization.setCreateTime(new Date());
		organization = govOrganizationService.insertReturn(organization);

		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", organization.getId());
		params.put("node", GovConstant.ORGANIZTION_NODE);
		params.put("tagKeyWords", scrapyGovOrganization.getTag());
		remoteTagRelationService.saveTagRelation(params);

		// 复制完设置为已导入状态
		scrapyGovOrganization.setDelFlag("2");
		this.updateById(scrapyGovOrganization);
		return true;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovOrganization> selectOrganizationPage(Query query) {
		Object title = query.getCondition().get("title");
		Object status = query.getCondition().get("status");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scrapyGovOrganizationMapper.selectOrganizationPage(query,title,status, prop, order, exceptExamined));
		return query;
	}

	@Override
	public List<String> listDictByNumber(String number) {
		return scrapyGovOrganizationMapper.listDictByNumber(number);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page getRecyclePage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("title", query.getCondition().get("title"));
		map.put("username", query.getCondition().get("username"));
		query.setRecords(scrapyGovOrganizationMapper.getRecyclePage(query, map));
		return query;
	}

	@Override
	public void deleteRecycle(Integer id) {
		scrapyGovOrganizationMapper.deleteById(id);

	}

	@Override
	public void recycleRecover(Integer id) {
		scrapyGovOrganizationMapper.recycleRecover(id);
	}

	@Override
	public void batchDeleteRecycle(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovOrganizationMapper.deleteById(id);
		}
	}

	@Override
	public void batchRecycleRecover(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovOrganizationMapper.recycleRecover(id);
		}
	}

}
