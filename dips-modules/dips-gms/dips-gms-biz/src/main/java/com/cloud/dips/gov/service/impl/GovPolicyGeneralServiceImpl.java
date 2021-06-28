package com.cloud.dips.gov.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.core.util.StringMatching;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExamineCount;
import com.cloud.dips.gov.api.entity.GovPolicyExamineRecord;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.entity.GovRelation;
import com.cloud.dips.gov.api.entity.GovRelationType;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.mapper.GovPolicyDeclareMapper;
import com.cloud.dips.gov.mapper.GovPolicyGeneralMapper;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;
import com.cloud.dips.gov.service.GovPolicyExamineRecordService;
import com.cloud.dips.gov.service.GovPolicyGeneralService;
import com.cloud.dips.gov.service.SortingDataService;
import com.cloud.dips.gov.utils.MapUtils;
import com.cloud.dips.gov.utils.WordUtil;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;


/**
 * @author C.Z.H
 */
@Service("govPolicyGeneralService")
@AllArgsConstructor
public class GovPolicyGeneralServiceImpl extends ServiceImpl<GovPolicyGeneralMapper, GovPolicyGeneral> implements GovPolicyGeneralService {

	private final GovPolicyGeneralMapper policyGeneralMapper;
	private final RemoteTagRelationService remoteTagRelation;
	private final GovRelationMapper govRelationMapper;
	private final GovRelationTypeMapper govRelationTypeMapper;
	private final GovPolicyExamineRecordService govPolicyExamineRecordService;
	private final GovPolicyExamineCountService govPolicyExamineCountService;
	private final SortingDataService sortingDataService;
	@Autowired
	private RemoteDictService remoteDictService;

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<GeneralVO> selectAllPage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		map.put("prop", query.getCondition().get("prop"));
		map.put("order", query.getCondition().get("order"));
		map.put("sort", query.getCondition().get("sort"));
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
			// 去除空格，并添加匹配符
			String title = sortingDataService.replaceSpecialSign(string);
			map.put("title", title);
		}
		if (null == query.getCondition().get("title")) {
			map.put("title", query.getCondition().get("title"));
		}
		List<GeneralVO> selectAll = policyGeneralMapper.selectAll(query, map);
		setGeneralText(selectAll,null,"web");
		query.setRecords(selectAll);
		return query;
	}

	@Override
	public Page<GeneralVO> selectWechatPage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		map.put("title", query.getCondition().get("title"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		Object sort = query.getCondition().get("sort");
		if (GovConstant.PUSH_TIME.equals(sort)) {
			map.put(GovConstant.PUSH_TIME, sort);
		}
		if (GovConstant.CLICK_VIEWS.equals(sort)) {
			map.put(GovConstant.CLICK_VIEWS, sort);
		}
		List<GeneralVO> selectWxAll = policyGeneralMapper.selectWxAll(query, map);
		setGeneralText(selectWxAll,null,"web");
		query.setRecords(selectWxAll);
		return query;
	}


	/**
	 * 逻辑删除
	 */
	@Override
	public Boolean logicDelete(Integer[] ids) {
		for (Integer id : ids) {
			//逻辑删除主表
			policyGeneralMapper.updateByGeneralId(id);
		}
		return true;
	}

	@Override
	public Boolean updateGeneral(GeneralDTO generalDTO) {
		GovPolicyGeneral general = new GovPolicyGeneral();
		BeanUtils.copyProperties(generalDTO, general);
		general.setDelFlag(CommonConstant.STATUS_NORMAL);
		List<String> regionArrstring = generalDTO.getRegionArr();
		if (null != regionArrstring && regionArrstring.size() > 0) {
			String regionArr = StringUtils.substringBetween(regionArrstring.toString(), "[", "]");
			String replaceAll = regionArr.replaceAll(" ", "");
			general.setRegionArray(replaceAll);
		}
		this.updateById(general);
		sortingDataService.addRelation(generalDTO);

		//关联标签
		Map<String, Object> map5 = new HashMap<>(0);
		map5.put("node", GovConstant.GENERAL_NODE);
		map5.put("relationId", generalDTO.getId());
		map5.put("tagKeyWords", getTagKeyWords(generalDTO.getTagList()));
		remoteTagRelation.saveTagRelation(map5);
		return Boolean.TRUE;
	}

	@Override
	public Boolean updateGeneralAndCommit(GeneralDTO generalDTO) {
		updateGeneral(generalDTO);
		List<Integer> ids = new ArrayList<>();
		ids.add(generalDTO.getId());
		return commit(ids);
	}


	/**
	 * 添加通用政策
	 */
	@Override
	public R insertPolicyGeneral(GeneralDTO generalDto) {
		GovPolicyGeneral policyGeneral = new GovPolicyGeneral();
		BeanUtils.copyProperties(generalDto, policyGeneral);
		policyGeneral.setExamineStatus(0);
		Optional<Object> id = Optional.ofNullable(generalDto.getId());
		if (id.isPresent()) {
			Map<String, Object> entity = MapUtils.beanToMap(policyGeneral);
			entity.remove("id");
			entity.remove("formality");
			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				policyGeneral = new GovPolicyGeneral();
				org.apache.commons.beanutils.BeanUtils.populate(policyGeneral, entity);
				policyGeneral.setExamineStatus(6);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		policyGeneral.setCreateTime(new Date());
		policyGeneral.setDelFlag(CommonConstant.STATUS_NORMAL);
		policyGeneral.setCreatorId(SecurityUtils.getUser().getId());
		policyGeneral.setProcessorId(SecurityUtils.getUser().getId());

		if (generalDto.getRegionArr() != null && generalDto.getRegionArr().size() > 0) {
			String regionArrstring = generalDto.getRegionArr().toString();
			String regionArr = StringUtils.substringBetween(regionArrstring, "[", "]");
			String replaceAll = regionArr.replaceAll(" ", "");
			policyGeneral.setRegionArray(replaceAll);
		}
		policyGeneralMapper.insert(policyGeneral);
		generalDto.setId(policyGeneral.getId());
		  // 标签
        Map<String, Object> params = new HashMap<>(0);
        params.put("relationId", generalDto.getId());
        params.put("node", GovConstant.GENERAL_NODE);
        params.put("tagKeyWords", getTagKeyWords(generalDto.getTagList()));
        remoteTagRelation.saveTagRelation(params);
		sortingDataService.addRelation(generalDto);
		return new R<>(Boolean.TRUE, policyGeneral.getId().toString());
	}

	/**
	 * 添加通用政策并提交
	 */
	@Override
	public Boolean insertPolicyGeneralAndCommit(GeneralDTO generalDto) {
		insertPolicyGeneral(generalDto);
		List<Integer> ids = new ArrayList<>();
		ids.add(generalDto.getId());
		return commit(ids);
	}




	@Override
	public GeneralVO selectGeneralVOById(Integer generalId) {
//		policyGeneralMapper.viewUp(generalId);
		GeneralVO generalVO = policyGeneralMapper.selectGeneralVO(generalId);
		String text = policyGeneralMapper.selectGeneralText(generalVO.getId());
		generalVO.setText(text);
		setGeneralValue(generalVO);
		List<GeneralVO> allForConsole = new ArrayList<GeneralVO>();
		setGeneralText(allForConsole,generalVO,"web");
		return generalVO;
	}

	/**
	 * 重构单个政策逻辑代码，增加查询速度
	 * @param generalVO
	 */
	private void setGeneralValue(GeneralVO generalVO) {
		if (StringUtils.isNotBlank(generalVO.getRegionArrString())) {
			String[] split = generalVO.getRegionArrString().split(",");
			generalVO.setRegionArr(Arrays.asList(split));
		}
		String[] numberList = new String[] {"DECLARE_TARGET","POLICY_THEME","POLICY_SCALE","POLICY_INDUSTRY","POLICY_LEVEL","POLICY_STYLE"};
        Map<String, List<DictValueVO>> dictMap = remoteDictService.getDictMap(numberList);
        if (StringUtils.isNotBlank(generalVO.getTarget())) {
        	String[] targetSplit = generalVO.getTarget().split(",");
        	List<DictValueVO> targetList = dictMap.get("DECLARE_TARGET");
        	for (int i = 0; i < targetSplit.length; i++) {
        		for (DictValueVO dictValueVO : targetList) {
        			if (dictValueVO.getKey().equals(targetSplit[i])) {
        				generalVO.getTargetList().add(dictValueVO.getValue());
        			}
        		}
        		
        	}
        	
        }
		if (StringUtils.isNotBlank(generalVO.getTheme())) {
			String[] themeSplit = generalVO.getTheme().split(",");
			List<DictValueVO> themeList = dictMap.get("POLICY_THEME");
			for (int i = 0; i < themeSplit.length; i++) {
				for (DictValueVO dictValueVO : themeList) {
					if (dictValueVO.getKey().equals(themeSplit[i])) {
						generalVO.getThemeList().add(dictValueVO.getValue());
					}
				}
				
			}
		}		
		if (StringUtils.isNotBlank(generalVO.getScale())) {
			 String[] scaleSplit = generalVO.getScale().split(",");
			 List<DictValueVO> scaleList = dictMap.get("POLICY_SCALE");
			 for (int i = 0; i < scaleSplit.length; i++) {
				 for (DictValueVO dictValueVO : scaleList) {
					 if (dictValueVO.getKey().equals(scaleSplit[i])) {
						 generalVO.getScaleList().add(dictValueVO.getValue());
					 }
				 }
						  
			 }
		 }		
		if (StringUtils.isNotBlank(generalVO.getIndustry())) {
			String[] industrySplit = generalVO.getIndustry().split(",");
			List<DictValueVO> industryList = dictMap.get("POLICY_INDUSTRY");
			for (int i = 0; i < industrySplit.length; i++) {
				for (DictValueVO dictValueVO : industryList) {
					if (dictValueVO.getKey().equals(industrySplit[i])) {
						generalVO.getIndustryList().add(dictValueVO.getValue());
					}
				}
				
			}
	     }
				String level = generalVO.getLevel();
				if (null != level) {
					
					List<DictValueVO> levelList = dictMap.get("POLICY_LEVEL");
					for (DictValueVO dictValueVO : levelList) {
						if (dictValueVO.getKey().equals(level)) {
							generalVO.setLevelName(dictValueVO.getValue());
						}
					}
				}
				String style = generalVO.getStyle();
				if (null != style) {
					List<DictValueVO> styleList = dictMap.get("POLICY_STYLE");
					for (DictValueVO dictValueVO : styleList) {
						if (dictValueVO.getKey().equals(style)) {
							generalVO.setStyleName(dictValueVO.getValue());
						}
					}	
					
				}
	}
	

	@Override
	public GeneralVO selectGeneralVOByIdForConsole(Integer id) {
		GeneralVO generalVO = policyGeneralMapper.selectGeneralVOForConsole(id);
		String text = policyGeneralMapper.selectGeneralText(generalVO.getId());
		generalVO.setText(text);
		setGeneralValue(generalVO);
		return generalVO;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Page selectPageByTagId(Query query) {
		Object tagId = query.getCondition().get("tagId");
		query.setRecords(policyGeneralMapper.selectGeneralVoPageByTagId(query, tagId));
		return query;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Page<GeneralVO> selectRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		return query.setRecords(policyGeneralMapper.selectRecyclePage(query, title, username));
	}

	@Override
	public Boolean recycleDelete(Integer id) {
		//删除关联
		policyGeneralMapper.deleteRelation(GovConstant.GENERAL_NODE, id);
		// 删除关联标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", id);
		params.put("node", GovConstant.GENERAL_NODE);
		remoteTagRelation.deleteTagRelation(id, GovConstant.GENERAL_NODE);
		//删除被关联的数据
		//申报政策关联
		govRelationMapper.deleteBeRelation(GovConstant.DECLARE_NODE, GovConstant.GENERAL_NODE, id);
		//解读关联政策
		govRelationMapper.deleteBeRelation(GovConstant.GOV_EXPLAIN_NODE, GovConstant.GENERAL_NODE, id);
		//政策关联政策
		govRelationMapper.deleteBeRelation(GovConstant.GENERAL_NODE, GovConstant.GENERAL_NODE, id);

		// 彻底删除
		policyGeneralMapper.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean repeat(String title) {
		List<GovPolicyGeneral> list = policyGeneralMapper.repeat(title);
		if (list != null && list.size() > 0) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}


	@Override
	public Boolean retreatPolicy(Query query) {
		Object id = query.getCondition().get("id");
		Object retreatContent = query.getCondition().get("retreatContent");
		int ids = Integer.parseInt(id.toString());
		GovPolicyGeneral govPolicyGeneral = this.selectById(ids);
		int isExamined = 3;
		if (isExamined == govPolicyGeneral.getExamineStatus()) {
			if (null == govPolicyGeneral.getRetreatCount() || govPolicyGeneral.getRetreatCount() == 0) {
				govPolicyGeneral.setRetreatCount(1);
			} else {
				govPolicyGeneral.setRetreatCount(govPolicyGeneral.getRetreatCount() + 1);
			}
			govPolicyGeneral.setRetreatContent((String) retreatContent);
			govPolicyGeneral.setRetreatUser(SecurityUtils.getUser().getId());
			govPolicyGeneral.setExamineStatus(5);
			this.updateById(govPolicyGeneral);
			markRecord(4, govPolicyGeneral.getId(), govPolicyGeneral.getProcessorId(), (String) retreatContent, govPolicyGeneral.getTitle());
			reduceCount(govPolicyGeneral.getProcessorId());
		}
		return Boolean.TRUE;
	}


	@Override
	public Page<GeneralVO> selectSelfPage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
			try {
				query.getCondition().put("title", URLDecoder.decode(string, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("processorId", SecurityUtils.getUser().getId());
		map.put("examineStatus", query.getCondition().get("examineStatus"));
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		map.put("title", query.getCondition().get("title"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		map.put("prop", query.getCondition().get("prop"));
		map.put("order", query.getCondition().get("order"));
		List<GeneralVO> selectSelfPage = policyGeneralMapper.selectSelfPage(query, map);
		setGeneralText(selectSelfPage,null,"web");
		query.setRecords(selectSelfPage);
		return query;
	}

	@Override
	public Boolean doExamine(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyGeneral govPolicyGeneral = this.selectById(id);
			if (1 == govPolicyGeneral.getExamineStatus() || 2 == govPolicyGeneral.getExamineStatus()) {
				govPolicyGeneral.setExamineStatus(3);
				govPolicyGeneral.setExamineDate(new Date());
				govPolicyGeneral.setExamineUser(SecurityUtils.getUser().getId());
				this.updateById(govPolicyGeneral);
				markRecord(2, id, govPolicyGeneral.getProcessorId(), "审核通过", govPolicyGeneral.getTitle());
				increaseCount(govPolicyGeneral.getProcessorId());
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Page<GeneralVO> selectExaminePage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
			try {
				query.getCondition().put("title", URLDecoder.decode(string, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("processorName", query.getCondition().get("processorName"));
		map.put("examineStatus", query.getCondition().get("examineStatus"));
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		map.put("title", query.getCondition().get("title"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		map.put("prop", query.getCondition().get("prop"));
		map.put("order", query.getCondition().get("order"));
		List<GeneralVO> examinePage = policyGeneralMapper.selectExaminePage(query, map);
		query.setRecords(examinePage);
		return query;
	}


	@Override
	public Boolean commit(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyGeneral govPolicyGeneral = this.selectById(id);
			if (null == govPolicyGeneral.getRegion() || null == govPolicyGeneral.getSource()
				|| null == govPolicyGeneral.getSource() || null == govPolicyGeneral.getTarget()
				|| null == govPolicyGeneral.getTheme() || null == govPolicyGeneral.getScale()
				|| null == govPolicyGeneral.getIndustry() || null == govPolicyGeneral.getRegion()
				|| null == govPolicyGeneral.getText() || null == govPolicyGeneral.getPublishTime()) {
				return Boolean.FALSE;
			}
			if (StringUtils.isEmpty(govPolicyGeneral.getRegion()) || StringUtils.isEmpty(govPolicyGeneral.getSource())
				|| StringUtils.isEmpty(govPolicyGeneral.getSource()) || StringUtils.isEmpty(govPolicyGeneral.getTarget())
				|| StringUtils.isEmpty(govPolicyGeneral.getTheme()) || StringUtils.isEmpty(govPolicyGeneral.getScale())
				|| StringUtils.isEmpty(govPolicyGeneral.getIndustry()) || StringUtils.isEmpty(govPolicyGeneral.getRegion())
				|| StringUtils.isEmpty(govPolicyGeneral.getText()) || StringUtils.isEmpty(govPolicyGeneral.getPublishTime().toString())) {
				return Boolean.FALSE;
			}
			Integer tagCount=policyGeneralMapper.checkTag(govPolicyGeneral.getId());
			Integer dispatchCount=policyGeneralMapper.checkDispatch(govPolicyGeneral.getId());
			if(tagCount <= 0 || dispatchCount <= 0) {
            	return Boolean.FALSE;
            }
			if (0 == govPolicyGeneral.getExamineStatus() || 4 == govPolicyGeneral.getExamineStatus() || 6 == govPolicyGeneral.getExamineStatus()) {
				govPolicyGeneral.setExamineStatus(1);
				govPolicyGeneral.setModifiedTime(new Date());
				govPolicyGeneral.setCommitTime(new Date());
				markRecord(1, govPolicyGeneral.getId(), govPolicyGeneral.getProcessorId(), "提交", govPolicyGeneral.getTitle());
				this.updateById(govPolicyGeneral);
			}
		}
		return Boolean.TRUE;
	}


	@Override
	public Boolean reCommit(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyGeneral govPolicyGeneral = this.selectById(id);
			if (1 == govPolicyGeneral.getExamineStatus()) {
				govPolicyGeneral.setExamineStatus(0);
				this.updateById(govPolicyGeneral);
				markRecord(1, govPolicyGeneral.getId(), govPolicyGeneral.getProcessorId(), "重新提交", govPolicyGeneral.getTitle());
			}
		}
		return Boolean.TRUE;
	}


	@Override
	public Boolean giveUpProcess(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyGeneral govPolicyGeneral = this.selectById(id);
			if (0 == govPolicyGeneral.getExamineStatus() || -1 == govPolicyGeneral.getExamineUser()) {
				String scrapyId = govPolicyGeneral.getScrapyId().toString();
				String processorId = govPolicyGeneral.getProcessorId().toString();
				if (StringUtils.isNotEmpty(scrapyId) && StringUtils.isNotEmpty(processorId)) {
					policyGeneralMapper.updateForGiveUpProcess(scrapyId, processorId);
				}
				markRecord(5, govPolicyGeneral.getId(), govPolicyGeneral.getProcessorId(), "自放弃加工", govPolicyGeneral.getTitle());
				this.deleteById(govPolicyGeneral.getId());
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}


	@Override
	public Boolean disExamine(Query query) {
		Object id = query.getCondition().get("id");
		Object retreatContent = query.getCondition().get("retreatContent");
		int ids = Integer.parseInt(id.toString());
		GovPolicyGeneral govPolicyGeneral = this.selectById(ids);
		int isCommit = 1;
		int isAccept = 2;
		if (isCommit == govPolicyGeneral.getExamineStatus() || isAccept == govPolicyGeneral.getExamineStatus()) {
			if (null == govPolicyGeneral.getRetreatCount() || govPolicyGeneral.getRetreatCount() == 0) {
				govPolicyGeneral.setRetreatCount(1);
			} else {
				govPolicyGeneral.setRetreatCount(govPolicyGeneral.getRetreatCount() + 1);
			}
			govPolicyGeneral.setRetreatContent((String) retreatContent);
			govPolicyGeneral.setRetreatUser(SecurityUtils.getUser().getId());
			govPolicyGeneral.setExamineStatus(4);
			this.updateById(govPolicyGeneral);
			markRecord(3, govPolicyGeneral.getId(), govPolicyGeneral.getProcessorId(), (String) retreatContent, govPolicyGeneral.getTitle());
			reduceCount(govPolicyGeneral.getProcessorId());
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}


	private void increaseCount(Integer processorId) {
		GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
		if (null == govPolicyExamineCount) {
			govPolicyExamineCount = new GovPolicyExamineCount();
			govPolicyExamineCount.setMark(1.0);
			//初始化
			govPolicyExamineCount.setCreateTime(new Date());
			govPolicyExamineCount.setProcessorId(processorId);
			govPolicyExamineCount.setDelFlag("0");
			govPolicyExamineCount.setAgreeCount(1);
		} else {
			govPolicyExamineCount.setMark(govPolicyExamineCount.getMark() + 1);
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

	@Override
	public Boolean accept(Integer id) {
		GovPolicyGeneral govPolicyGeneral = this.selectById(id);
		if (1 == govPolicyGeneral.getExamineStatus()) {
			govPolicyGeneral.setExamineStatus(2);
			this.updateById(govPolicyGeneral);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
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
		govPolicyExamineRecord.setPolicyType(0);
		govPolicyExamineRecordService.insert(govPolicyExamineRecord);
	}


	@Override
	public Page<GeneralVO> selectConsolePage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
			try {
				query.getCondition().put("title", URLDecoder.decode(string, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("title", query.getCondition().get("title"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		map.put("prop", query.getCondition().get("prop"));
		map.put("order", query.getCondition().get("order"));
		Object sort = query.getCondition().get("sort");
		if (GovConstant.PUSH_TIME.equals(sort)) {
			map.put(GovConstant.PUSH_TIME, sort);
		}
		if (GovConstant.CLICK_VIEWS.equals(sort)) {
			map.put(GovConstant.CLICK_VIEWS, sort);
		}
		List<GeneralVO> allForConsole = policyGeneralMapper.selectAllForConsole(query, map);
		query.setRecords(allForConsole);
		return query;
	}
	
	/**
	 * 重构页面逻辑
	 * @param allForConsole
	 * @param mark
	 */
	private void setGeneralText(List<GeneralVO> allForConsole,GeneralVO general,String mark) {
		if (null != mark && allForConsole.size() > 0) {
		List<Integer> generalIds = new ArrayList<Integer>();
		for (GeneralVO generalId : allForConsole) {
			generalIds.add(generalId.getId());
		}
		List<CommonVO> selectGeneralTag = policyGeneralMapper.selectNewGeneralTag(generalIds);
		List<CommonVO> selectGeneralDispatch = policyGeneralMapper.selectNewGeneralDispatch(generalIds);
		for (GeneralVO generalVO : allForConsole) {
			for (CommonVO commonVO : selectGeneralTag) {
				if (generalVO.getId().equals(commonVO.getRelationId())) {
					commonVO.setCommonId(commonVO.getTagId());
					commonVO.setCommonName(commonVO.getTagName());
					generalVO.getTagList().add(commonVO);
				}
			}
			for (CommonVO commonVO : selectGeneralDispatch) {
				if (generalVO.getId().equals(commonVO.getDispatchId())) {
					commonVO.setCommonId(commonVO.getDispatchId());
					commonVO.setCommonName(commonVO.getDispatchName());
					generalVO.getDispatchList().add(commonVO);
				}
			}

		 }
		}
		if (null != mark && null != general) {
			List<Integer> generalIds = new ArrayList<Integer>();
			generalIds.add(general.getId());
			List<CommonVO> selectGeneralTag = policyGeneralMapper.selectNewGeneralTag(generalIds);
			List<CommonVO> selectGeneralDispatch = policyGeneralMapper.selectNewGeneralDispatch(generalIds);
			for (CommonVO commonVO : selectGeneralTag) {
				if (general.getId().equals(commonVO.getRelationId())) {
					commonVO.setCommonId(commonVO.getTagId());
					commonVO.setCommonName(commonVO.getTagName());
					general.getTagList().add(commonVO);
				}
			}
			for (CommonVO commonVO : selectGeneralDispatch) {
				if (general.getId().equals(commonVO.getDispatchId())) {
					commonVO.setCommonId(commonVO.getDispatchId());
					commonVO.setCommonName(commonVO.getDispatchName());
					general.getDispatchList().add(commonVO);
				}
			}
		}
		
	}
	
	
	
	
	
	
	

	@Override
	public List<Map<String, Object>> getGeneralAndDeclareList(Query query) {
		Object title = query.getCondition().get("title");
		return policyGeneralMapper.getGeneralAndDeclareList(query, title);
	}

	@Override
	public List<GeneralVO> selectRelevant(String ids,Integer id) {
		if (StringUtils.isNotEmpty(ids)) {


			String[] split = StringUtils.split(ids, ",");
			List<Integer> integers = Lists.newArrayList();
			for (int i = 0; i < split.length; i++) {
				integers.add(Integer.valueOf(split[i]));
			}
			List<GeneralVO> generalVOS = policyGeneralMapper.selectRelevant(ids, integers,id);

			generalVOS.stream().forEach(generalVO ->
				generalVO.setSimilarityDegree
					(StringMatching.levenshtein(ids, generalVO.getRelatedTags())));

			Collections.sort(generalVOS, Comparator.comparing(GeneralVO::getSimilarityDegree).reversed());

			if (generalVOS.size() > 5) {
				return generalVOS.subList(0, 5);
			}
			return generalVOS;
		}
		return Lists.newArrayList();
	}

	@Override
	public List<GeneralVO> selctRelation(String ids, Integer id) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] split = StringUtils.split(ids, ",");
			List<Integer> integers = Lists.newArrayList();
			for (int i = 0; i < split.length; i++) {
				integers.add(Integer.valueOf(split[i]));
			}
			return policyGeneralMapper.selctRelation(integers, id);
		}
		return Lists.newArrayList();
	}

	/**
	 * 通用对比
	 */
	@Override
	public List<ComparisonGeneralVO> selectComparisonByGeneral(Integer[] ids) {
		List<Integer> asList = Arrays.asList(ids);
		return policyGeneralMapper.selectComparisonByGeneral(asList);
	}

	/**
	 * 全文下载
	 */
	@Override
	public void fullDownloadGeneral(HttpServletResponse response, Integer id) {
		GeneralVO generalVO = selectGeneralVOById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", generalVO.getTitle());
		map.put("issue", generalVO.getIssue());
		map.put("source", generalVO.getSource());
		Date publishTime = generalVO.getPublishTime();
		String formatTimeEight = formatTimeEight(publishTime);
		map.put("publishTime", formatTimeEight);
		map.put("url", generalVO.getUrl());
		map.put("styleName", generalVO.getStyleName());
		map.put("levelName", generalVO.getLevelName());
		map.put("summary", generalVO.getSummary());
		String text = generalVO.getText().replaceAll("src=\"//", "src=\"http://");
		map.put("text", text);
		map.put("tagList", generalVO.getTagList());
		map.put("themeList", generalVO.getThemeList());
		map.put("industryList", generalVO.getIndustryList());
		map.put("dispatchList", generalVO.getDispatchList());
		map.put("scaleList", generalVO.getScaleList());
		map.put("targetList", generalVO.getTargetList());
		// 选择下载的模板并赋值
		File file = WordUtil.createDoc(map, "general", generalVO.getTitle());
		// 输出给浏览器下载
		WordUtil.downloadWord(response, file, map);

	}

	/**
	 * 全文下载发文时间增加8小时（由于系统和开发环境相差8小时，所以写此方法）
	 *
	 * @param publishTime
	 * @return
	 */
	private String formatTimeEight(Date publishTime) {
		// 定义格式
		SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
		// 增加8小时
		long rightTime = (long) (publishTime.getTime() + 8 * 60 * 60 * 1000);
		String newtime = sd.format(rightTime);
		return newtime;
	}

	// 将前台获取的接口参数标签集合转换成字符串
			private String getTagKeyWords(List<String> tags) {
				StringBuilder tagKeyWords = new StringBuilder();
				if (tags != null) {
					Set<String> set = new HashSet<>(tags);
					String[] tagNames = set.toArray(new String[0]);
					for (int i = 0; i < tagNames.length; i++) {
						if (i != tagNames.length - 1) {
							tagKeyWords.append(tagNames[i]).append(",");
						} else {
							tagKeyWords.append(tagNames[i]);
						}
					}
				}
				return tagKeyWords.toString();
			}

	@Override
	public List<Long> gainList(Map<String, Object> params) {
		if (null != params.get("title") && !"".equals(params.get("title"))) {
			// 去除空格，并添加匹配符
			String string = params.get("title").toString().trim();
			String title = sortingDataService.replaceSpecialSign(string);
			params.put("title", title);
		}
		if (null == params.get("title")) {
			params.put("title", params.get("title"));
		}
		if (params.size() > 1) {
			return  policyGeneralMapper.gainList(params);
		}else {
			return null;
		}
	}

	@Override
	public List<GovPolicyGeneral> queryByInfos(List<Long> ids){
		return policyGeneralMapper.queryByInfos(ids);
	}
}



