package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyExplain;
import com.cloud.dips.gov.mapper.ScrapyGovPolicyExplainMapper;
import com.cloud.dips.gov.service.GovPolicyExplainService;
import com.cloud.dips.gov.service.ScrapyGovPolicyExplainService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author C.Z.H
 */
@Service("scrapyGovPolicyExplainService")
@AllArgsConstructor
public class ScrapyGovPolicyExplainServiceImpl extends ServiceImpl<ScrapyGovPolicyExplainMapper, ScrapyGovPolicyExplain>
	implements ScrapyGovPolicyExplainService {

	private final RemoteTagRelationService remoteTagRelationService;
	private final GovPolicyExplainService govPolicyExplainService;
	private final ScrapyGovPolicyExplainMapper scrapyGovPolicyExplainMapper;

	@Override
	public Boolean copy(ScrapyGovPolicyExplain scrapyGovPolicyExplain) {
		// 判断是否复制过
		if ("2".equals(scrapyGovPolicyExplain.getDelFlag())) {
			return false;
		}
		scrapyGovPolicyExplain.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(scrapyGovPolicyExplain);
		// 基本字段取出复制
		GovPolicyExplain explain = new GovPolicyExplain();
		explain.setTitle(scrapyGovPolicyExplain.getTitle());
		explain.setPublishTime(scrapyGovPolicyExplain.getPublishTime());
		explain.setSource(scrapyGovPolicyExplain.getSource());
		explain.setSummary(scrapyGovPolicyExplain.getSummary());
		explain.setText(scrapyGovPolicyExplain.getText());
		explain.setImage(scrapyGovPolicyExplain.getImage());
		explain.setUrl(scrapyGovPolicyExplain.getUrl());
		explain.setDelFlag(scrapyGovPolicyExplain.getDelFlag());
		explain.setPriority(scrapyGovPolicyExplain.getPriority());
		explain.setViews(scrapyGovPolicyExplain.getViews());
		Integer userId = SecurityUtils.getUser().getId();
		explain.setCreatorId(userId);
		explain.setDelFlag("0");
		//审核流程初始化
		explain.setScrapyId(scrapyGovPolicyExplain.getId());
		explain.setProcessorId(SecurityUtils.getUser().getId());
		explain.setExamineStatus(0);
		// 解读主体
		explain.setMain(scrapyGovPolicyExplainMapper.selectDictKeyByNumberAndValue("POLICY_MAIN", scrapyGovPolicyExplain.getMain()));
		// 层级
		explain.setLevel(scrapyGovPolicyExplainMapper.selectDictKeyByNumberAndValue("POLICY_LEVEL", scrapyGovPolicyExplain.getLevel()));
		// 解读主题
		if (scrapyGovPolicyExplain.getTheme() != null && !"".equals(scrapyGovPolicyExplain.getTheme())) {
			explain.setTheme(getValues(scrapyGovPolicyExplain.getTheme(), "POLICY_THEME"));
		}
		// 行业
		if (scrapyGovPolicyExplain.getIndustry() != null && !"".equals(scrapyGovPolicyExplain.getIndustry())) {
			explain.setIndustry(getValues(scrapyGovPolicyExplain.getIndustry(), "POLICY_INDUSTRY"));
		}
		explain.setCreateTime(new Date());
		explain = govPolicyExplainService.save(explain);

		// 标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", explain.getId());
		params.put("node", GovConstant.GOV_EXPLAIN_NODE);
		params.put("tagKeyWords", scrapyGovPolicyExplain.getTag());
		remoteTagRelationService.saveTagRelation(params);

		// 复制完设置为已导入状态
		scrapyGovPolicyExplain.setDelFlag("2");
		this.updateById(scrapyGovPolicyExplain);

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
				String s = scrapyGovPolicyExplainMapper.selectDictKeyByNumberAndValue(number, value);
				if (s != null) {
					sb.append(s).append(",");
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovPolicyExplain> selectAllPage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scrapyGovPolicyExplainMapper.selectScrapyExplainPage(query, title, username,prop,order,exceptExamined));
		return query;
	}

	@Override
	public List<String> listDictByNumber(String number) {
		return scrapyGovPolicyExplainMapper.listDictByNumber(number);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovPolicyExplain> getRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(scrapyGovPolicyExplainMapper.getRecyclePage(query, title, username));
		return query;
	}

	@Override
	public Boolean deleteRecycle(Integer id) {
		scrapyGovPolicyExplainMapper.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean recycleRecover(Integer id) {
		scrapyGovPolicyExplainMapper.recycleRecover(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean batchDeleteRecycle(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicyExplainMapper.deleteById(id);
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean batchRecycleRecover(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicyExplainMapper.recycleRecover(id);
		}
		return Boolean.TRUE;
	}
}
