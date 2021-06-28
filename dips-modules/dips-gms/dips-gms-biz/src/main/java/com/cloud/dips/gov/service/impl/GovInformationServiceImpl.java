package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.core.util.StringMatching;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.dto.InformationDTO;
import com.cloud.dips.gov.api.entity.*;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.api.vo.InformationVO;
import com.cloud.dips.gov.mapper.GovInformationMapper;
import com.cloud.dips.gov.service.GovInformationService;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;
import com.cloud.dips.gov.service.GovPolicyExamineRecordService;
import com.cloud.dips.gov.service.ScrapyGovInformationService;
import com.cloud.dips.gov.service.ScrapyGovPolicyGeneralService;
import com.cloud.dips.gov.service.SortingDataService;
import com.cloud.dips.gov.utils.MapUtils;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 政策资讯模型实现类
 *
 * @author CUI.CAN
 * @date 2018-09-11 10:27:23
 */
@Service("govInformationService")
@AllArgsConstructor
public class GovInformationServiceImpl extends ServiceImpl<GovInformationMapper, GovInformation> implements GovInformationService {

	private final GovInformationMapper govInformationMapper;
	private final RemoteTagRelationService remoteTagService;
	private final GovPolicyExamineRecordService govPolicyExamineRecordService;
	private final GovPolicyExamineCountService govPolicyExamineCountService;
	private final SortingDataService sortingDataService;

	/**
	 * 分页查询资讯列表
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<InformationVO> selectInformationPage(Query query) {
		String title = null;
		Object source = query.getCondition().get("source");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object priority = query.getCondition().get("priority");
		Object username = query.getCondition().get("username");
		Object sort = query.getCondition().get("sort");
		Object regionCode = query.getCondition().get("region");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		// 去除空格，并添加匹配符
    		 title = sortingDataService.replaceSpecialSign(string);
			
		}
			List<InformationVO> informationVO = govInformationMapper.selectInformationVOList(query, title, source,
				startTime, endTime, priority, username, sort, regionCode, prop, order);
			setInformationText(informationVO,null,"web");
			List<InformationVO> conovertList = conovertList(informationVO);
			query.setRecords(conovertList);
			return query;
	}

	@Override
	public Page selectInformationWechatPage(Query query) {
		Object title = query.getCondition().get("title");
		Object source = query.getCondition().get("source");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object priority = query.getCondition().get("priority");
		Object username = query.getCondition().get("username");
		Object sort = query.getCondition().get("sort");
		Object regionCode = query.getCondition().get("region");
		if (GovConstant.PUSH_TIME.equals(sort)) {
			List<InformationVO> informationVO = govInformationMapper.selectInformationWechat(query, title, source,
				startTime, endTime, priority, null, username, sort, regionCode);
			List<InformationVO> conovertList = conovertList(informationVO);
			query.setRecords(conovertList);
			return query;
		}
		if (GovConstant.CLICK_VIEWS.equals(sort)) {
			List<InformationVO> informationVO = govInformationMapper.selectInformationWechat(query, title, source,
				startTime, endTime, priority, sort, username, null, regionCode);
			List<InformationVO> conovertList = conovertList(informationVO);
			query.setRecords(conovertList);
			return query;
		}

		List<InformationVO> informationVO = govInformationMapper.selectInformationWechat(query, title, source,
			startTime, endTime, priority, null, username, null, regionCode);
		setInformationText(informationVO,null,"web");
		List<InformationVO> conovertList = conovertList(informationVO);
		query.setRecords(conovertList);

		return query;
	}

	private List<InformationVO> conovertList(List<InformationVO> informationVO) {
		for (InformationVO information : informationVO) {
			String regionArr = information.getRegionArrString();
			if (null == regionArr || "".equals(regionArr)) {
				continue;
			}
			String[] split = regionArr.split(",");
			information.setRegionArr(Arrays.asList(split));
		}
		return informationVO;
	}

	/**
	 * 根据id查询资讯信息
	 */
	@Override
	public InformationVO selectInformationById(Integer id) {
		//增加浏览次数
		govInformationMapper.viewUp(id);
		InformationVO informationVO = govInformationMapper.selectInformationById(id);
		String text = govInformationMapper.selectInformationText(informationVO.getId());
		informationVO.setText(text);
		List<InformationVO> allForConsole = new ArrayList<InformationVO>();
		setInformationText(allForConsole,informationVO,"web");
		return informationVO;
	}

	/**
	 * 保存咨询信息
	 */
	@Override
	@Transactional
	public R insertInformation(InformationDTO informationDTO) {
		GovInformation govInformation = new GovInformation();
		BeanUtils.copyProperties(informationDTO, govInformation);
		govInformation.setExamineStatus(0);
		Optional<Object> id = Optional.ofNullable(informationDTO.getId());
		if (id.isPresent()) {
			Map<String, Object> entity = MapUtils.beanToMap(govInformation);
			entity.remove("id");
			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				govInformation = new GovInformation();
				org.apache.commons.beanutils.BeanUtils.populate(govInformation, entity);
				govInformation.setExamineStatus(6);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		govInformation.setCreatorId(SecurityUtils.getUser().getId());
		govInformation.setCreateTime(new Date());
		govInformation.setDelFlag(CommonConstant.STATUS_NORMAL);
		govInformation.setProcessorId(SecurityUtils.getUser().getId());
		if (informationDTO.getRegionArr() != null && informationDTO.getRegionArr().size() > 0) {
			String regionArrstring = informationDTO.getRegionArr().toString();
			String regionArr = StringUtils.substringBetween(regionArrstring, "[", "]");
			String replaceAll = regionArr.replaceAll(" ", "");
			govInformation.setRegionArray(replaceAll);
		}
		govInformationMapper.insert(govInformation);
		//标签保存
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", govInformation.getId());
		params.put("node", GovConstant.GOV_INFORMATION);
		params.put("tagKeyWords", getTagKeyWords(informationDTO.getTagList()));
		params.put("number", "def");
		remoteTagService.saveTagRelation(params);
		informationDTO.setId(govInformation.getId());
		return new R<>(Boolean.TRUE, govInformation.getId().toString());

	}

	/**
	 * 保存咨询信息
	 */
	@Override
	@Transactional
	public Boolean insertInformationAndCommit(InformationDTO informationDTO) {
		insertInformation(informationDTO);
		List<Integer> ids = new ArrayList<>();
		ids.add(informationDTO.getId());
		return commit(ids);
	}

	/**
	 * 修改资讯信息
	 */
	@Override
	@Transactional
	public Boolean updateGovInformationById(InformationDTO informationDTO) {
		GovInformation govInformation = new GovInformation();
		BeanUtils.copyProperties(informationDTO, govInformation);
		govInformation.setDelFlag(CommonConstant.STATUS_NORMAL);
		List<String> regionArrstring = informationDTO.getRegionArr();
		if (null != regionArrstring && regionArrstring.size() > 0) {
			String regionArr = StringUtils.substringBetween(regionArrstring.toString(), "[", "]");
			String replaceAll = regionArr.replaceAll(" ", "");
			govInformation.setRegionArray(replaceAll);
		}
		govInformationMapper.updateById(govInformation);
		//标签保存
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", govInformation.getId());
		params.put("node", GovConstant.GOV_INFORMATION);
		params.put("tagKeyWords", getTagKeyWords(informationDTO.getTagList()));
		params.put("number", "def");
		remoteTagService.saveTagRelation(params);
		return Boolean.TRUE;
	}


	/**
	 * 修改资讯信息
	 */
	@Override
	@Transactional
	public Boolean updateGovInformationByIdAndCommit(InformationDTO informationDTO) {
		updateGovInformationById(informationDTO);
		List<Integer> ids = new ArrayList<>();
		ids.add(informationDTO.getId());
		return commit(ids);
	}

	/**
	 * 删除资讯
	 */
	@Override
	@Transactional
	public Boolean deleteByIds(List<Integer> ids) {
		GovInformation govInformation = new GovInformation();
		for (Integer id : ids) {
			govInformation.setId(id);
			govInformation.setDelFlag(CommonConstant.STATUS_DEL);
			govInformationMapper.updateById(govInformation);
		}

		return Boolean.TRUE;
	}

	/**
	 * 根据标签id查询列表
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<InformationVO> selectPageByTagId(Query query) {
		Object tagId = query.getCondition().get("tagId");
		query.setRecords(govInformationMapper.selectInformationVOPageByTagId(query, tagId));
		return query;
	}

	/**
	 * 回收站列表
	 *
	 * @param query
	 * @return
	 */
	@Override
	public Page selectRecyclePage(Query<Object> query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(govInformationMapper.selectRecyclePage(query, title, username));
		return query;
	}

	/**
	 * 回收站彻底删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Boolean recycleDelete(Integer id) {
		// 删除标签
//		Map<String, Object> params = new HashMap<>(0);
//		params.put("relationId", id);
//		params.put("node", GovConstant.GOV_INFORMATION);
//		remoteTagService.deleteTagRelation(params);
		remoteTagService.deleteTagRelation(id, GovConstant.GOV_INFORMATION);
		// 彻底删除
		govInformationMapper.deleteById(id);
		return Boolean.TRUE;
	}

	/**
	 * 回收站查询单个
	 *
	 * @param id
	 * @return
	 */
	@Override
	public InformationVO selectRecycleById(Integer id) {
		return govInformationMapper.selectRecycleById(id);
	}

	@Override
	public Boolean repeat(String title) {
		List<GovInformation> list = govInformationMapper.repeat(title);
		if (list != null && list.size() > 0) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	/**
	 * 字符串拼接
	 *
	 * @param tags
	 * @return
	 */
	private String getTagKeyWords(List<String> tags) {
		StringBuilder tagKeyWords = new StringBuilder();
		if (tags != null) {
			Set<String> set = new HashSet<>(tags);
			String[] godtagNames = set.toArray(new String[0]);
			for (int i = 0; i < godtagNames.length; i++) {
				if (i != godtagNames.length - 1) {
					tagKeyWords.append(godtagNames[i]).append(",");
				} else {
					tagKeyWords.append(godtagNames[i]);
				}
			}
		}
		return tagKeyWords.toString();
	}

	@Override
	public Boolean retreatPolicy(Query query) {
		int id = Integer.parseInt(query.getCondition().get("id").toString());
		Object retreatContent = query.getCondition().get("retreatContent");
		GovInformation govInformation = this.selectById(id);
		int isExamined = 3;
		if (isExamined == govInformation.getExamineStatus()) {
			if (null == govInformation.getRetreatCount() || govInformation.getRetreatCount() == 0) {
				govInformation.setRetreatCount(1);
			} else {
				govInformation.setRetreatCount(govInformation.getRetreatCount() + 1);
			}
			govInformation.setRetreatContent((String) retreatContent);
			govInformation.setRetreatUser(SecurityUtils.getUser().getId());
			govInformation.setExamineStatus(5);
			this.updateById(govInformation);
			markRecord(4, govInformation.getId(), govInformation.getProcessorId(), (String) retreatContent, govInformation.getTitle());
			reduceCount(govInformation.getProcessorId());
		}
		return Boolean.TRUE;
	}

	@Override
	public Page<InformationVO> selectSelfPage(Query query) {
		Object title = null;
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Object processorId = SecurityUtils.getUser().getId();
		Object examineStatus = query.getCondition().get("examineStatus");
		Object source = query.getCondition().get("source");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object priority = query.getCondition().get("priority");
		Object sort = query.getCondition().get("sort");
		Object regionCode = query.getCondition().get("region");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		List<InformationVO> inforList = govInformationMapper.selectSelfPage(query, title, processorId, examineStatus, source, startTime, startTime,
			endTime, priority, sort, regionCode, prop, order);
		setInformationText(inforList,null,"web");
		List<InformationVO> conovertList = conovertList(inforList);
		query.setRecords(conovertList);
		return query;
	}

	@Override
	public Page<InformationVO> selectExaminePage(Query query) {
		Object title = null;
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Object processorName = query.getCondition().get("processorName");
		Object examineStatus = query.getCondition().get("examineStatus");
		Object source = query.getCondition().get("source");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object priority = query.getCondition().get("priority");
		Object sort = query.getCondition().get("sort");
		Object regionCode = query.getCondition().get("region");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		List<InformationVO> inforList = govInformationMapper.selectExaminePage(query, title, processorName, examineStatus, source, startTime, startTime,
			endTime, priority, sort, regionCode, prop, order);
		setInformationText(inforList,null,"web");
		List<InformationVO> conovertList = conovertList(inforList);
		query.setRecords(conovertList);
		return query;
	}

	@Override
	public Boolean commit(List<Integer> ids) {
		for (Integer id : ids) {
			GovInformation govInformation = this.selectById(id);
			if (null == govInformation.getTitle() || null == govInformation.getPublishTime()
				|| null == govInformation.getSource() || null == govInformation.getUrl()
				|| null == govInformation.getRegion() || null == govInformation.getSummary()
				|| null == govInformation.getText()) {
				return Boolean.FALSE;
			}
			if (StringUtils.isEmpty(govInformation.getTitle()) || StringUtils.isEmpty(govInformation.getPublishTime().toString())
				|| StringUtils.isEmpty(govInformation.getSource()) || StringUtils.isEmpty(govInformation.getUrl())
				|| StringUtils.isEmpty(govInformation.getRegion()) || StringUtils.isEmpty(govInformation.getSummary())
				|| StringUtils.isEmpty(govInformation.getText())) {
				return Boolean.FALSE;
			}
			if (govInformationMapper.checkTag(govInformation.getId()) <=0 ) {
				return Boolean.FALSE;
			}
			if (0 == govInformation.getExamineStatus() || 4 == govInformation.getExamineStatus() || 6 == govInformation.getExamineStatus()) {
				govInformation.setExamineStatus(1);
				govInformation.setModifiedTime(new Date());
				govInformation.setCommitTime(new Date());
				this.updateById(govInformation);
				markRecord(1, govInformation.getId(), govInformation.getProcessorId(), "提交", govInformation.getTitle());
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean reCommit(List<Integer> ids) {
		for (Integer id : ids) {
			GovInformation govInformation = this.selectById(id);
			if (1 == govInformation.getExamineStatus()) {
				govInformation.setExamineStatus(0);
				this.updateById(govInformation);
				markRecord(1, govInformation.getId(), govInformation.getProcessorId(), "重新提交", govInformation.getTitle());
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean giveUpProcess(List<Integer> ids) {
		for (Integer id : ids) {
			GovInformation govInformation = this.selectById(id);
			if (0 == govInformation.getExamineStatus() || -1 == govInformation.getExamineUser()) {
				if (null != govInformation.getProcessorId()) {
					String scrapyId = "";
					if (null != govInformation.getScrapyId()) {
						scrapyId = Integer.toString(govInformation.getScrapyId());
					}
					String processorId = Integer.toString(govInformation.getProcessorId());
					if (StringUtils.isNotEmpty(scrapyId) && StringUtils.isNotEmpty(processorId)) {
						govInformationMapper.updateForGiveUpProcess(scrapyId, processorId);
						govInformationMapper.updateForGiveUpProcessToInformation(scrapyId, processorId);
					}
				}
			} else {
				return Boolean.FALSE;
			}
			markRecord(5, govInformation.getId(), govInformation.getProcessorId(), "自放弃加工", govInformation.getTitle());
			this.deleteById(govInformation.getId());
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean accept(Integer id) {
		GovInformation govInformation = this.selectById(id);
		if (1 == govInformation.getExamineStatus()) {
			govInformation.setExamineStatus(2);
			this.updateById(govInformation);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean doExamine(List<Integer> ids) {
		for (Integer id : ids) {
			GovInformation govInformation = this.selectById(id);
			if (1 == govInformation.getExamineStatus() || 2 == govInformation.getExamineStatus()) {
				govInformation.setExamineStatus(3);
				govInformation.setExamineDate(new Date());
				govInformation.setExamineUser(SecurityUtils.getUser().getId());
				this.updateById(govInformation);
				markRecord(2, govInformation.getId(), govInformation.getProcessorId(), "审核通过", govInformation.getTitle());
				increaseCount(govInformation.getProcessorId());
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean disExamine(Query query) {
		int id = Integer.parseInt(query.getCondition().get("id").toString());
		Object retreatContent = query.getCondition().get("retreatContent");
		GovInformation govInformation = this.selectById(id);
		int isCommit = 1;
		int isAccept = 2;
		if (isCommit == govInformation.getExamineStatus() || isAccept == govInformation.getExamineStatus()) {
			if (null == govInformation.getRetreatCount() || govInformation.getRetreatCount() == 0) {
				govInformation.setRetreatCount(1);
			} else {
				govInformation.setRetreatCount(govInformation.getRetreatCount() + 1);
			}
			govInformation.setRetreatContent((String) retreatContent);
			govInformation.setRetreatUser(SecurityUtils.getUser().getId());
			govInformation.setExamineStatus(4);
			this.updateById(govInformation);
			markRecord(3, govInformation.getId(), govInformation.getProcessorId(), (String) retreatContent, govInformation.getTitle());
			reduceCount(govInformation.getProcessorId());
			return Boolean.TRUE;
		}else {
			return Boolean.FALSE;
		}
	}

	private void increaseCount(Integer processorId) {
		GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
		if (null == govPolicyExamineCount) {
			govPolicyExamineCount = new GovPolicyExamineCount();
			govPolicyExamineCount.setMark(0.5);
			//初始化
			govPolicyExamineCount.setCreateTime(new Date());
			govPolicyExamineCount.setProcessorId(processorId);
			govPolicyExamineCount.setDelFlag("0");
			govPolicyExamineCount.setAgreeCount(1);
		} else {
			govPolicyExamineCount.setMark(govPolicyExamineCount.getMark() + 0.5);
			govPolicyExamineCount.setAgreeCount(govPolicyExamineCount.getAgreeCount() + 1);
		}
		govPolicyExamineCount.setModifiedTime(new Date());
		govPolicyExamineCountService.insertOrUpdate(govPolicyExamineCount);
	}

	private void reduceCount(Integer processorId) {
		GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
		if (null == govPolicyExamineCount) {
			govPolicyExamineCount = new GovPolicyExamineCount();
			//初始化
			govPolicyExamineCount.setCreateTime(new Date());
			govPolicyExamineCount.setProcessorId(processorId);
			govPolicyExamineCount.setDelFlag("0");
			govPolicyExamineCount.setDisagreeCount(1);
		} else {
			govPolicyExamineCount.setDisagreeCount(govPolicyExamineCount.getDisagreeCount() + 1);
		}
		govPolicyExamineCount.setModifiedTime(new Date());
		govPolicyExamineCountService.insertOrUpdate(govPolicyExamineCount);
	}

	private void markRecord(Integer behavior, Integer policyId, Integer processorId, String content, String title) {
		GovPolicyExamineRecord govPolicyExamineRecord = new GovPolicyExamineRecord();
		govPolicyExamineRecord.setBehavior(behavior);
		govPolicyExamineRecord.setPolicyId(policyId);
		govPolicyExamineRecord.setCreate_user(SecurityUtils.getUser().getId());
		govPolicyExamineRecord.setCreateTime(new Date());
		govPolicyExamineRecord.setModifiedTime(new Date());
		govPolicyExamineRecord.setDelFlag("0");
		govPolicyExamineRecord.setProcessorId(processorId);
		govPolicyExamineRecord.setContent(content);
		govPolicyExamineRecord.setTitle(title);
		govPolicyExamineRecord.setPolicyType(3);
		govPolicyExamineRecordService.insert(govPolicyExamineRecord);
	}

	/**
	 * 分页查询资讯列表
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<InformationVO> selectConsolePage(Query query) {
		Object title = query.getCondition().get("title");
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Object source = query.getCondition().get("source");
		Object startTime = query.getCondition().get("startTime");
		Object endTime = query.getCondition().get("endTime");
		Object priority = query.getCondition().get("priority");
		Object username = query.getCondition().get("username");
		Object sort = query.getCondition().get("sort");
		Object regionCode = query.getCondition().get("region");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		List<InformationVO> informationVO = govInformationMapper.selectInformationVOListForConsole(query, title, source,
			startTime, endTime, priority, username,regionCode, prop, order,sort);
		setInformationText(informationVO,null,"web");
		List<InformationVO> conovertList = conovertList(informationVO);
		query.setRecords(conovertList);
		return query;
	}

	@Override
	public List<InformationVO> selectMatching(String ids,Long id) {
		Integer size = 5;
		if (StringUtils.isNotEmpty(ids)) {
			String[] split = StringUtils.split(ids, ",");
			List<Integer> integers = Lists.newArrayList();
			for (int i = 0; i < split.length; i++) {
				integers.add(Integer.valueOf(split[i]));
			}
			List<InformationVO> informationVOS = govInformationMapper.selectMatching(ids, integers,id);

			informationVOS.stream().forEach(generalVO ->
				generalVO.setSimilarityDegree
					(StringMatching.levenshtein(ids, generalVO.getRelatedTags())));

			Collections.sort(informationVOS, Comparator.comparing(InformationVO::getSimilarityDegree).reversed());
			if (informationVOS.size() > size) {
				return informationVOS.subList(0, 5);
			}
			return informationVOS;
		}
		return Lists.newArrayList();

	}


	@Override
	public List<InformationVO> selectCommonMatching(String ids) {
		Integer size = 5;
		if (StringUtils.isNotEmpty(ids)) {
			String[] split = StringUtils.split(ids, ",");
			List<Integer> integers = Lists.newArrayList();
			for (int i = 0; i < split.length; i++) {
				integers.add(Integer.valueOf(split[i]));
			}
			List<InformationVO> informationVOS = govInformationMapper.selectCommonMatching(ids, integers);

			informationVOS.stream().forEach(generalVO ->
				generalVO.setSimilarityDegree
					(StringMatching.levenshtein(ids, generalVO.getRelatedTags())));

			Collections.sort(informationVOS, Comparator.comparing(InformationVO::getSimilarityDegree).reversed());
			if (informationVOS.size() > size) {
				return informationVOS.subList(0, 5);
			}
			return informationVOS;
		}
		return Lists.newArrayList();
	}

	/**
	 * 根据id查询资讯信息
	 */
	@Override
	public InformationVO selectInformationByIdForConsole(Integer id) {
		InformationVO informationVO = govInformationMapper.selectInformationByIdForConsole(id);
		String text = govInformationMapper.selectInformationText(informationVO.getId());
		informationVO.setText(text);
		List<InformationVO> allForConsole = new ArrayList<InformationVO>();
		setInformationText(allForConsole,informationVO,"web");
		Optional<String> regionArrString = Optional.ofNullable(informationVO.getRegionArrString());
		if (regionArrString.isPresent()) {
			String[] split = regionArrString.get().split(",");
			informationVO.setRegionArr(Arrays.asList(split));
		}


		return informationVO;
	}
	
	  /**
		 * 重构页面逻辑
		 * @param allForConsole
		 * @param mark
		 */
		private void setInformationText(List<InformationVO> informationVOList,InformationVO information,String mark) {
			if (null != mark && informationVOList.size() > 0) {
			List<Integer> generalIds = new ArrayList<Integer>();
			for (InformationVO generalId : informationVOList) {
				generalIds.add(generalId.getId());
			}
			List<CommonVO> selectGeneralTag = govInformationMapper.selectNewInformationTag(generalIds);
			for (InformationVO informationVO : informationVOList) {
				for (CommonVO commonVO : selectGeneralTag) {
					if (informationVO.getId().equals(commonVO.getRelationId())) {
						commonVO.setCommonId(commonVO.getTagId());
						commonVO.setCommonName(commonVO.getTagName());
						informationVO.getTagList().add(commonVO);
					}
				}
			 }
			}
			if (null != mark && null != information) {
				List<Integer> generalIds = new ArrayList<Integer>();
				generalIds.add(information.getId());
				List<CommonVO> selectGeneralTag = govInformationMapper.selectNewInformationTag(generalIds);
				for (CommonVO commonVO : selectGeneralTag) {
					if (information.getId().equals(commonVO.getRelationId())) {
						commonVO.setCommonId(commonVO.getTagId());
						commonVO.setCommonName(commonVO.getTagName());
						information.getTagList().add(commonVO);
					}
				}
				
			}
			
		}
}
