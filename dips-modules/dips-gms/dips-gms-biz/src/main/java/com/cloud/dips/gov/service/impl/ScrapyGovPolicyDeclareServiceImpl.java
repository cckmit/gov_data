package com.cloud.dips.gov.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.entity.GovPolicyDeclare;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import com.cloud.dips.gov.mapper.ScrapyGovPolicyDeclareMapper;
import com.cloud.dips.gov.service.GovPolicyDeclareService;
import com.cloud.dips.gov.service.ScrapyGovPolicyDeclareService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author C.Z.H
 */
@Service("scrapyGovPolicyDeclareService")
@AllArgsConstructor
public class ScrapyGovPolicyDeclareServiceImpl extends ServiceImpl<ScrapyGovPolicyDeclareMapper, ScrapyGovPolicyDeclare> implements ScrapyGovPolicyDeclareService {

	private final RemoteTagRelationService remoteTagRelationService;
	private final GovPolicyDeclareService govPolicyDeclareService;
	private final ScrapyGovPolicyDeclareMapper scrapyGovPolicyDeclareMapper;

	@Override
	public Boolean copy(ScrapyGovPolicyDeclare scrapyGovPolicyDeclare) {

		scrapyGovPolicyDeclare.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(scrapyGovPolicyDeclare);
		GovPolicyDeclare declare = new GovPolicyDeclare();
		declare.setTitle(scrapyGovPolicyDeclare.getTitle());
		declare.setSummary(scrapyGovPolicyDeclare.getSummary());
		declare.setSource(scrapyGovPolicyDeclare.getSource());
		declare.setReference(scrapyGovPolicyDeclare.getReference());
		declare.setIssue(scrapyGovPolicyDeclare.getIssue());
		// 文体
		declare.setStyle(scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue("POLICY_STYLE", scrapyGovPolicyDeclare.getStyle()));
		// 层级
		declare.setLevel(scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue("POLICY_LEVEL", scrapyGovPolicyDeclare.getLevel()));
		// 申报方式
		declare.setMethod(scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue("DECLARE_METHOD", scrapyGovPolicyDeclare.getMethod()));
		// 申报状态
		declare.setStatus(scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue("DECLARE_STATUS", scrapyGovPolicyDeclare.getStatus()));
		// 专项类型
		declare.setSpecial(scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue("DECLARE_SPECIAL", scrapyGovPolicyDeclare.getSpecial()));

		declare.setCondition(scrapyGovPolicyDeclare.getCondition());
		declare.setStandard(scrapyGovPolicyDeclare.getStandard());
		declare.setProcess(scrapyGovPolicyDeclare.getProcess());
		declare.setRequirement(scrapyGovPolicyDeclare.getRequirement());
		declare.setWriteTime(scrapyGovPolicyDeclare.getWriteTime());
		declare.setPublishTime(scrapyGovPolicyDeclare.getPublishTime());
		declare.setEffectTime(scrapyGovPolicyDeclare.getEffectTime());
		declare.setInvalidTime(scrapyGovPolicyDeclare.getInvalidTime());
		declare.setText(scrapyGovPolicyDeclare.getText());
		declare.setUrl(scrapyGovPolicyDeclare.getUrl());
		//审核流程初始化
		declare.setScrapyId(scrapyGovPolicyDeclare.getId());
		declare.setProcessorId(SecurityUtils.getUser().getId());
		declare.setExamineStatus(0);
		// 申报对象
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getTarget())) {
			declare.setTarget(getValues(scrapyGovPolicyDeclare.getTarget(), "DECLARE_TARGET"));
		}
		// 扶持方式
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getMode())) {
			declare.setMode(getValues(scrapyGovPolicyDeclare.getMode(), "DECLARE_MODE"));
		}
		// 扶持形式
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getFormality())) {
			declare.setFormality(getValues(scrapyGovPolicyDeclare.getFormality(), "DECLARE_FORMALITY"));
		}
		// 支持方式
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getSupport())) {
			declare.setSupport(getValues(scrapyGovPolicyDeclare.getSupport(), "DECLARE_SUPPORT"));
		}
		// 主题
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getTheme())) {
			declare.setTheme(getValues(scrapyGovPolicyDeclare.getTheme(), "POLICY_THEME"));
		}
		// 扶持资金规模
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getFund())) {
			declare.setFund(getValues(scrapyGovPolicyDeclare.getFund(), "DECLARE_FUND"));
		}
		// 适用规模
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getScale())) {
			declare.setScale(getValues(scrapyGovPolicyDeclare.getScale(), "POLICY_SCALE"));
		}
		// 行业
		if (StrUtil.isNotBlank(scrapyGovPolicyDeclare.getIndustry())) {
			declare.setIndustry(getValues(scrapyGovPolicyDeclare.getIndustry(), "POLICY_INDUSTRY"));
		}

		declare.setDelFlag("0");
		Integer userId = SecurityUtils.getUser().getId();
		declare.setCreatorId(userId);
		declare.setCreateTime(new Date());
		declare = govPolicyDeclareService.save(declare);

		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", declare.getId());
		params.put("node", GovConstant.DECLARE_NODE);
		params.put("tagKeyWords", scrapyGovPolicyDeclare.getTag());
		remoteTagRelationService.saveTagRelation(params);

		// 复制完设置为已导入状态
		scrapyGovPolicyDeclare.setDelFlag("2");
		this.updateById(scrapyGovPolicyDeclare);
		return true;
	}

	@Override
	public List<String> listDictByNumber(String number) {
		return scrapyGovPolicyDeclareMapper.listDictByNumber(number);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page getRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(scrapyGovPolicyDeclareMapper.getRecyclePage(query, title, username));
		return query;
	}

	@Override
	public void recoverRecycle(Integer id) {
		ScrapyGovPolicyDeclare declare = scrapyGovPolicyDeclareMapper.selectById(id);
		declare.setDelFlag(CommonConstant.STATUS_NORMAL);
		scrapyGovPolicyDeclareMapper.updateById(declare);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovPolicyDeclare> selectAllPage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scrapyGovPolicyDeclareMapper.selectScrapyDeclarePage(query, title, username, startTime, endTime,prop,order,exceptExamined));
		return query;
	}

	/**
	 * 把字符串转为id字符串
	 */
	private String getValues(String str, String number) {
		String[] labelList = str.split(",");
		StringBuilder sb = new StringBuilder();
		if (labelList.length > 0) {
			for (String value : labelList) {
				String s = scrapyGovPolicyDeclareMapper.selectDictKeyByNumberAndValue(number, value);
				if (s != null) {
					sb.append(s).append(",");
				}
			}
		}
		return sb.toString();
	}

}
