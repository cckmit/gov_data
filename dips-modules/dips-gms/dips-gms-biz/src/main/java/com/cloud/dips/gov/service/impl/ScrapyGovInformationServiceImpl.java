package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.entity.GovInformation;
import com.cloud.dips.gov.api.entity.ScrapyGovInformation;
import com.cloud.dips.gov.mapper.GovInformationMapper;
import com.cloud.dips.gov.mapper.ScrapyGovInformationMapper;
import com.cloud.dips.gov.service.ScrapyGovInformationService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author C.Z.H
 */
@Service("scrapyGovInformationService")
@AllArgsConstructor
public class ScrapyGovInformationServiceImpl extends ServiceImpl<ScrapyGovInformationMapper, ScrapyGovInformation>
	implements ScrapyGovInformationService {

	private final GovInformationMapper govInformationMapper;
	private final RemoteTagRelationService remoteTagRelation;
	private final ScrapyGovInformationMapper scrapyGovInformationMapper;

	/**
	 * 复制
	 */
	@Override
	public Boolean copy(ScrapyGovInformation scrapyGovInformation) {
		if ("2".equals(scrapyGovInformation.getDelFlag())) {
			return false;
		}
		scrapyGovInformation.setProcessorId(SecurityUtils.getUser().getId());
		this.updateById(scrapyGovInformation);
		GovInformation information = new GovInformation();
		information.setTitle(scrapyGovInformation.getTitle());
		information.setPublishTime(scrapyGovInformation.getPublishTime());
//		information.setImage(scrapyGovInformation.getImage());
		information.setSummary(scrapyGovInformation.getSummary());
		information.setSource(scrapyGovInformation.getSource());
		information.setText(scrapyGovInformation.getText());
		information.setAuthor(scrapyGovInformation.getAuthor());
		information.setUrl(scrapyGovInformation.getUrl());
		information.setViews(scrapyGovInformation.getViews());
		information.setDelFlag(CommonConstant.STATUS_NORMAL);
		information.setCreateTime(new Date());
		Integer userId = SecurityUtils.getUser().getId();
		information.setCreatorId(userId);
		//审核流程初始化
		information.setScrapyId(scrapyGovInformation.getId());
		information.setProcessorId(SecurityUtils.getUser().getId());
		information.setExamineStatus(0);
		govInformationMapper.insert(information);

		// 标签
		// 关联标签
		Map<String, Object> map = new HashMap<>(0);
		map.put("node", GovConstant.GOV_INFORMATION);
		map.put("relationId", information.getId());
		map.put("tagKeyWords", scrapyGovInformation.getTag());
		remoteTagRelation.saveTagRelation(map);
		// 复制完设置为已导入状态
		scrapyGovInformation.setDelFlag("2");
		this.updateById(scrapyGovInformation);
		return true;
	}

	/**
	 * 分页查询
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<ScrapyGovInformation> selectAllPage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		Object exceptExamined = query.getCondition().get("exceptExamined");
		query.setRecords(scrapyGovInformationMapper.scrapyGovInformationPage(query, title, username,prop,order,exceptExamined));
		return query;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page getRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		query.setRecords(scrapyGovInformationMapper.getRecyclePage(query, title, username));
		return query;
	}

	@Override
	public void deleteRecycle(Integer id) {
		scrapyGovInformationMapper.deleteById(id);
	}

	@Override
	public void recycleRecover(Integer id) {
		scrapyGovInformationMapper.recycleRecover(id);
	}

	@Override
	public void batchDeleteRecycle(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovInformationMapper.deleteById(id);
		}
	}

	@Override
	public void batchRecycleRecover(Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovInformationMapper.recycleRecover(id);
		}
	}

}
