package com.cloud.dips.gov.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.entity.*;
import com.cloud.dips.gov.mapper.GovPolicyGeneralMapper;
import com.cloud.dips.gov.mapper.ScrapyGovPolicyGeneralMapper;
import com.cloud.dips.gov.service.*;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.netflix.discovery.util.StringUtil;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author C.Z.H
 */
@Service("scrapyGovPolicyGeneralService")
@AllArgsConstructor
public class ScrapyGovPolicyGeneralServiceImpl extends ServiceImpl<ScrapyGovPolicyGeneralMapper, ScrapyGovPolicyGeneral> implements ScrapyGovPolicyGeneralService {

	private final GovPolicyGeneralMapper generalMapper;
	private final RemoteTagRelationService remoteTagRelation;
	private final ScrapyGovPolicyGeneralMapper scarpyMapper;
	private final GovPolicyDeclareService declareService;
	private final GovPolicyExplainService explainService;
	private final GovInformationService informationService;
	private final GovOrganizationService organizationService;
	private final GovPolicySenseService senseService;
	

	@Override
	public Boolean copySave(ScrapyGovPolicyGeneral scrapy) {
		if ("2".equals(scrapy.getDelFlag())) {
			return false;
		}
		scrapy.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(scrapy);
		GovPolicyGeneral general = new GovPolicyGeneral();
		general.setTitle(scrapy.getTitle());
		general.setSummary(scrapy.getSummary());
		general.setSource(scrapy.getSource());
		general.setReference(scrapy.getReference());
		general.setIssue(scrapy.getIssue());
//		general.setEffective(scrapy.getEffective());
//		general.setWriteTime(scrapy.getWriteTime());
		general.setPublishTime(scrapy.getPublishTime());
		general.setEffectTime(scrapy.getEffectTime());
		general.setInvalidTime(scrapy.getInvalidTime());
		general.setText(scrapy.getText());
		general.setUrl(scrapy.getUrl());
		general.setDelFlag("0");
		general.setRegion(scrapy.getRegion());
		//审核流程初始化
		general.setScrapyId(scrapy.getId());
		general.setProcessorId(SecurityUtils.getUser().getId());
		general.setExamineStatus(0);
		
		general.setStyle(scarpyMapper.selectDictKeyByNumberAndValue(scrapy.getStyle(), "POLICY_STYLE"));
		general.setLevel(scarpyMapper.selectDictKeyByNumberAndValue(scrapy.getLevel(), "POLICY_LEVEL"));
		general.setTimeliness(scarpyMapper.selectDictKeyByNumberAndValue(scrapy.getTimeliness(), "IS_USEFUL"));
		general.setStage(scarpyMapper.selectDictKeyByNumberAndValue(scrapy.getStage(), "POLICY_STAGE"));
		general.setFormality(scarpyMapper.selectDictKeyByNumberAndValue(scrapy.getFormality(), "POLICY_SEND"));
		// 主题
		if (StrUtil.isNotBlank(scrapy.getTheme())) {
			general.setTheme(getValues(scrapy.getTheme(), "POLICY_THEME"));
		}
		//适用对象
		if (StrUtil.isNotBlank(scrapy.getTarget())) {
			general.setTarget(getValues(scrapy.getTarget(), "DECLARE_TARGET"));
		}
		//适用规模
		if (StrUtil.isNotBlank(scrapy.getFund())) {
			general.setScale(getValues(scrapy.getFund(), "POLICY_SCALE"));
		}
		//使用行业
		if (StrUtil.isNotBlank(scrapy.getIndustry())) {
			general.setIndustry(getValues(scrapy.getIndustry(), "POLICY_INDUSTRY"));
		}
		Integer userId = SecurityUtils.getUser().getId();
		general.setCreatorId(userId);
		general.setCreateTime(new Date());
		generalMapper.insert(general);

		//标签
		//关联标签
		Map<String, Object> map = new HashMap<>(0);
		map.put("node", GovConstant.GENERAL_NODE);
		map.put("relationId", general.getId());
		map.put("tagKeyWords", scrapy.getTag());
		remoteTagRelation.saveTagRelation(map);

		scrapy.setDelFlag("2");
		this.updateById(scrapy);

		return true;
	}

	/**
	 * 通用导入申报
	 */
	@Override
	public Boolean copyDeclare(ScrapyGovPolicyGeneral sGeneral) {
		sGeneral.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(sGeneral);
		GovPolicyDeclare declare = new GovPolicyDeclare();
		declare.setTitle(sGeneral.getTitle());
		declare.setSummary(sGeneral.getSummary());
		declare.setSource(sGeneral.getSource());
		declare.setReference(sGeneral.getReference());
		declare.setIssue(sGeneral.getIssue());
		declare.setStyle(scarpyMapper.selectDictKeyByNumberAndValue(sGeneral.getStyle(), "POLICY_STYLE"));
		declare.setLevel(scarpyMapper.selectDictKeyByNumberAndValue(sGeneral.getLevel(), "POLICY_LEVEL"));
//		declare.setWriteTime(sGeneral.getWriteTime());
		declare.setPublishTime(sGeneral.getPublishTime());
		declare.setEffectTime(sGeneral.getEffectTime());
		declare.setInvalidTime(sGeneral.getInvalidTime());
		declare.setUrl(sGeneral.getUrl());
		declare.setText(sGeneral.getText());
		declare.setDelFlag("0");
		//审核流程初始化
		declare.setScrapyId(sGeneral.getId());
		declare.setProcessorId(SecurityUtils.getUser().getId());
		declare.setExamineStatus(0);
		
		//主题
		if (StrUtil.isNotBlank(sGeneral.getTheme())) {
			declare.setTheme(getValues(sGeneral.getTheme(), "POLICY_THEME"));
		}
		//适用对象
		if (StrUtil.isNotBlank(sGeneral.getTarget())) {
			declare.setTarget(getValues(sGeneral.getTarget(), "DECLARE_TARGET"));
		}
		//适用规模
		if (StrUtil.isNotBlank(sGeneral.getFund())) {
			declare.setScale(getValues(sGeneral.getFund(), "POLICY_SCALE"));
		}
		//使用行业
		if (StrUtil.isNotBlank(sGeneral.getIndustry())) {
			declare.setIndustry(getValues(sGeneral.getIndustry(), "POLICY_INDUSTRY"));
		}
		declare.setCreatorId(SecurityUtils.getUser().getId());
		declare.setCreateTime(new Date());
		declare = declareService.save(declare);

		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", declare.getId());
		params.put("node", GovConstant.DECLARE_NODE);
		params.put("tagKeyWords", sGeneral.getTag());
		remoteTagRelation.saveTagRelation(params);

		sGeneral.setDelFlag("2");
		this.updateById(sGeneral);
		return true;
	}

	/**
	 * 通用导入解读
	 */
	@Override
	public Boolean copyExplain(ScrapyGovPolicyGeneral sGeneral) {
		sGeneral.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(sGeneral);
		GovPolicyExplain explain = new GovPolicyExplain();
		explain.setTitle(sGeneral.getTitle());
		explain.setPublishTime(sGeneral.getPublishTime());
		explain.setSource(sGeneral.getSource());
		explain.setLevel(scarpyMapper.selectDictKeyByNumberAndValue(sGeneral.getLevel(), "POLICY_LEVEL"));
		explain.setSummary(sGeneral.getSummary());
		explain.setText(sGeneral.getText());
		explain.setUrl(sGeneral.getUrl());
		explain.setCreatorId(SecurityUtils.getUser().getId());
		explain.setDelFlag("0");
		//审核流程初始化
		explain.setScrapyId(sGeneral.getId());
		explain.setProcessorId(SecurityUtils.getUser().getId());
		explain.setExamineStatus(0);
		// 解读主题
		if (StrUtil.isNotBlank(sGeneral.getTheme())) {
			explain.setTheme(getValues(sGeneral.getTheme(), "POLICY_THEME"));
		}
		// 行业
		if (StrUtil.isNotBlank(sGeneral.getIndustry())) {
			explain.setIndustry(getValues(sGeneral.getIndustry(), "POLICY_INDUSTRY"));
		}
		explain.setCreateTime(new Date());
		explainService.insert(explain);

		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", explain.getId());
		params.put("node", GovConstant.GOV_EXPLAIN_NODE);
		params.put("tagKeyWords", sGeneral.getTag());
		remoteTagRelation.saveTagRelation(params);

		sGeneral.setDelFlag("2");
		this.updateById(sGeneral);
		return true;
	}

	/**
	 * 批量复制入资讯库
	 */
	@Override
	public Boolean copyInformation(ScrapyGovPolicyGeneral sGeneral) {
		sGeneral.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(sGeneral);
		GovInformation information = new GovInformation();
		information.setTitle(sGeneral.getTitle());
		information.setPublishTime(sGeneral.getPublishTime());
		information.setSummary(sGeneral.getSummary());
		information.setSource(sGeneral.getSource());
		information.setText(sGeneral.getText());
		information.setUrl(sGeneral.getUrl());
		information.setDelFlag("0");
		information.setCreatorId(SecurityUtils.getUser().getId());
		information.setCreateTime(new Date());
		//审核流程初始化
		information.setScrapyId(sGeneral.getId());
		information.setProcessorId(SecurityUtils.getUser().getId());
		information.setExamineStatus(0);
		informationService.insert(information);

		//关联标签
		Map<String, Object> map = new HashMap<>(0);
		map.put("node", GovConstant.GOV_INFORMATION);
		map.put("relationId", information.getId());
		map.put("tagKeyWords", sGeneral.getTag());
		remoteTagRelation.saveTagRelation(map);
		return true;
	}

	/**
	 * 批量复制入机构库
	 */
	@Override
	public Boolean copyOrganization(ScrapyGovPolicyGeneral sGeneral) {
		sGeneral.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(sGeneral);
		GovOrganization organization = new GovOrganization();
		organization.setName(sGeneral.getTitle());
		if (scarpyMapper.selectDictKeyByNumberAndValue(sGeneral.getLevel(), "POLICY_LEVEL") != null) {
			organization.setLevel(scarpyMapper.selectDictKeyByNumberAndValue(sGeneral.getLevel(), "POLICY_LEVEL"));
		}
		organization.setUrl(sGeneral.getUrl());
		organization.setDelFlag("0");
		organization.setCreatorId(SecurityUtils.getUser().getId());
		organization.setCreateTime(new Date());
		organization = organizationService.insertReturn(organization);
		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", organization.getId());
		params.put("node", GovConstant.ORGANIZTION_NODE);
		params.put("tagKeyWords", sGeneral.getTag());
		remoteTagRelation.saveTagRelation(params);
		return true;
	}

	/**
	 * 批量复制入常识库
	 */
	@Override
	public Boolean copySense(ScrapyGovPolicyGeneral sGeneral) {
		sGeneral.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(sGeneral);
		GovPolicySense sense = new GovPolicySense();
		sense.setTitle(sGeneral.getTitle());
		sense.setPublishTime(sGeneral.getPublishTime());
		sense.setSource(sGeneral.getSource());
		sense.setSummary(sGeneral.getSummary());
		sense.setText(sGeneral.getText());
		sense.setDelFlag("0");
		sense.setCreatorId(SecurityUtils.getUser().getId());
		sense.setCreateTime(new Date());
		//审核流程初始化
		sense.setScrapyId(sGeneral.getId());
		sense.setProcessorId(SecurityUtils.getUser().getId());
		sense.setExamineStatus(0);
		senseService.insert(sense);

		//关联标签
		Map<String, Object> map = new HashMap<>(0);
		map.put("node", "gov_policy_sense");
		map.put("relationId", sense.getId());
		map.put("tagKeyWords", sGeneral.getTag());
		remoteTagRelation.saveTagRelation(map);
		return true;
	}

	/**
	 * 把字符串转为id集合
	 */
	private String getValues(String str, String number) {
		String[] labelList = str.split(",");
		StringBuilder sb = new StringBuilder();
		if (labelList.length > 0) {
			for (String value : labelList) {
				String s = scarpyMapper.selectDictKeyByNumberAndValue(value, number);
				if (s != null) {
					sb.append(s).append(",");
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Page<ScrapyGovPolicyGeneral> selectAllPage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object status = query.getCondition().get("status");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scarpyMapper.selectScrapyGeneralPage(query, title, username, startTime, endTime, status,prop,order,exceptExamined));
		return query;
	}

	@Override
	public List<String> listDictByNumber(String number) {
		return scarpyMapper.listDictByNumber(number);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Page<ScrapyGovPolicyGeneral> getRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(scarpyMapper.getRecyclePage(query, title, username));
		return query;
	}

	@Override
	public void deleteRecycle(Integer id) {
		scarpyMapper.deleteById(id);

	}

	@Override
	public void recycleRecover(Integer id) {
		scarpyMapper.recycleRecover(id);

	}

	@Override
	public void batchDeleteRecycle(Integer[] ids) {
		for (Integer id : ids) {
			scarpyMapper.deleteById(id);
		}

	}

	@Override
	public void batchRecycleRecover(Integer[] ids) {
		for (Integer id : ids) {
			scarpyMapper.recycleRecover(id);
		}
	}
}
