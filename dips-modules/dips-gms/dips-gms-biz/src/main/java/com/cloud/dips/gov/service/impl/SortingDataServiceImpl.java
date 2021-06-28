package com.cloud.dips.gov.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.ExplainDTO;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.entity.GovRelation;
import com.cloud.dips.gov.api.entity.GovRelationType;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.gov.mapper.GovPolicyDeclareMapper;
import com.cloud.dips.gov.mapper.GovPolicyExplainMapper;
import com.cloud.dips.gov.mapper.GovPolicyGeneralMapper;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.SortingDataService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;


/**
 * 
 * @author johan
 * 2018年12月20日
 *SortingDataServiceImpl.java
 */
@Service("SortingDataService")
public class SortingDataServiceImpl implements SortingDataService{

	@Autowired
	private GovPolicyDeclareMapper govPolicyDeclareMapper;
	@Autowired
	private GovPolicyGeneralMapper govPolicyGeneralMapper;
	@Autowired
	private GovPolicyExplainMapper govPolicyExplainMapper;
	@Autowired
	private GovRelationMapper govRelationMapper;
	@Autowired
	private GovRelationTypeMapper govRelationTypeMapper;
	
	/**
	 * 根据用户标签查询相关的政策，并整理
	 * @param page 当前页数
	 * @param limit 显示数量
	 * @param sortingDataDTO 订阅的筛选条件（对象，规模，地区，行业等） 
	 * @param theme 订阅的主题
	 * @return JSON  整理的数据用JSON格式返回
	 */
	public Page<SortDataVO> sortData(SortingDataDTO sortingDataDTO,Query query,String mark) {
		Page<SortDataVO> page = queryPushPolicy(sortingDataDTO,query,mark);
		return page;
	}
	
	private Page<SortDataVO> queryPushPolicy(SortingDataDTO sortingDataDTO,Query query,String mark) {
		// 获取当前年月和前三个月
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String currentMonth = format.format(c.getTime());
		c.add(Calendar.MONTH, -3);
		String beforeMonth = format.format(c.getTime());
		// 根据标签订阅
		if (sortingDataDTO.getType().intValue() == 1) {
			List<Integer> policyId = govPolicyDeclareMapper.selectRelationId(sortingDataDTO.getTagStatus());
			List<SortDataVO> tagPolicy = govPolicyDeclareMapper.selectPolicyById(query,policyId,beforeMonth,currentMonth,mark);
			query.setRecords(tagPolicy);
			return query;
		}
		// 根据字段订阅
		List<SortDataVO> policyDeclare = govPolicyDeclareMapper.queryThemeForTwo(query,sortingDataDTO.getTheme(), sortingDataDTO.getLevelString(), 
				sortingDataDTO.getIndustry(), sortingDataDTO.getRegion(), sortingDataDTO.getTargetString(), sortingDataDTO.getDeparment(),
				sortingDataDTO.getScaleString(),beforeMonth,currentMonth,mark);
		query.setRecords(policyDeclare);
		return query;
	}
	
	/**
	 * 全局搜索的结果汇总并分页
	 */
	@Override
	public Page<SortDataVO> totalSearch(Query query) {
		Object titleSObject = query.getCondition().get("title");
		Object sort = query.getCondition().get("sort");
		if (null == titleSObject) {
			List<SortDataVO> list = new ArrayList<SortDataVO>();
			query.setRecords(list);
			return query;
		}
		// 匹配特殊字符和空格，并添加全文搜索匹配符
		String title = replaceSpecialSign(titleSObject.toString());
			List<SortDataVO> declarePage = govPolicyDeclareMapper.selectNewGlobalSearch(query,sort,title);
			query.setRecords(declarePage);
			return query;
}
	public String replaceSpecialSign(String titleSObject) {
		String decodeTitle = null;
		try {
			 decodeTitle = URLDecoder.decode(titleSObject,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String regEx="[\\〈 \\〉\\(\\)\\（\\）\\[\\]\\【\\】\\{\\}\\“\\”\\<\\>\\《\\》\\‘\\’\\——\\-\\,\\，\\、]+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(decodeTitle);
		String replace = matcher.replaceAll(" ").trim();
		
		Pattern pat = Pattern.compile("\\s+");
		Matcher mat = pat.matcher(replace);
		String newReplace = mat.replaceAll(" +");
		
		StringBuilder builder = new StringBuilder(newReplace);
		builder.insert(0,"+");
		String buildString = builder.toString();
		return buildString;
	}


	@Override
	public JSONObject selectAllCount(List<DictValueVO> dictValueParents) {
		JSONObject json = new JSONObject();
		
		for (DictValueVO dictValueVO : dictValueParents) {
			Integer countDeclare = govPolicyDeclareMapper.selectCountDeclare(dictValueVO.getKey());
			Integer countExplain = govPolicyExplainMapper.selectCountExplain(dictValueVO.getKey());
			Integer countGeneral = govPolicyGeneralMapper.selectCountGeneral(dictValueVO.getKey());
			int total = countDeclare+countExplain+countGeneral;
			json.put(dictValueVO.getValue(), total);
      }
		return json;
	}

	@Override
	public List<Integer> selectPolicyCount() {
		List<Integer> countList=govPolicyGeneralMapper.selectAllPolicyCount();
		return countList;
	}

	@Override
	@Async
	public Boolean addRelation(GeneralDTO generalDTO) {
		GovPolicyGeneral general = new GovPolicyGeneral();
		BeanUtils.copyProperties(generalDTO, general);

		//发文单位（关联机构）
		List<GovRelation> dispatchList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GENERAL_NODE, generalDTO.getId(), GovConstant.GENERAL_DISPATCH);
		for (GovRelation gr : dispatchList) {
			govRelationMapper.deleteOne(GovConstant.GENERAL_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
		}
		if (generalDTO.getDispatchList() != null && generalDTO.getDispatchList().size() > 0) {
			saveRelation(generalDTO.getDispatchList(), general, GovConstant.GENERAL_DISPATCH);
		}

		// 联合发文单位（关联机构）
		List<GovRelation> unionList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GENERAL_NODE, generalDTO.getId(), GovConstant.GENERAL_UNION);
		for (GovRelation gr : unionList) {
			govRelationMapper.deleteOne(GovConstant.GENERAL_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
		}
		if (generalDTO.getUnionList() != null && generalDTO.getUnionList().size() > 0) {
			saveRelation(generalDTO.getUnionList(), general, GovConstant.GENERAL_UNION);
		}

		//政策依据（关联政策）
		List<GovRelation> policyList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GENERAL_NODE, generalDTO.getId(), GovConstant.GENERAL_NODE);
		for (GovRelation gr : policyList) {
			govRelationMapper.deleteOne(GovConstant.GENERAL_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
		}
		if (generalDTO.getPolicyList() != null && generalDTO.getPolicyList().size() > 0) {
			saveRelation(generalDTO.getPolicyList(), general, GovConstant.GENERAL_NODE);
		}

		//适用区域（关联地域）
		List<GovRelation> cityList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GENERAL_NODE, generalDTO.getId(), GovConstant.GENERAL_CITY);
		for (GovRelation gr : cityList) {
			govRelationMapper.deleteOne(GovConstant.GENERAL_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
		}
		if (generalDTO.getRegionList() != null && generalDTO.getRegionList().size() > 0) {
			saveRelation(generalDTO.getRegionList(), general, GovConstant.GENERAL_CITY);
		}

		return true;
	}
	// 保存关联
	private void saveRelation(List<Integer> ids, GovPolicyGeneral general, String number) {
		for (Integer id : ids) {
			GovRelation relation = findOrCreate(general, id, number);
			if (relation != null) {
				govRelationMapper.insert(relation);
			}
		}
	}
	// 关联新增，直接使用id
	private GovRelation findOrCreate(GovPolicyGeneral general, Integer id, String number) {
		GovRelation bean = govRelationMapper.findOne(GovConstant.GENERAL_NODE, general.getId(), id, number);
		if (bean == null) {
			GovRelationType type = govRelationTypeMapper.selectByNumber(number);
			bean = new GovRelation(GovConstant.GENERAL_NODE, general.getId(), id, type.getId());
			return bean;
		}
		return null;
	}
	
		@Override
		@Async
		public void explainMethod(ExplainDTO explainDTO, GovPolicyExplain govPolicyExplain) {
	        // 关联政策原文
	        List<GovRelation> explainPolicy = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GOV_EXPLAIN_NODE,
	                govPolicyExplain.getId(), GovConstant.GOV_EXPLAIN_GENERAL);
	        for (GovRelation gr : explainPolicy) {
	            govRelationMapper.deleteOne(GovConstant.GOV_EXPLAIN_NODE, gr.getRelationId(), gr.getCorrelationId(),
	                    gr.getTypeId());
	        }
	        if (explainDTO.getPolicyList() != null && explainDTO.getPolicyList().size() > 0) {
	            saveRelation(explainDTO.getPolicyList(), govPolicyExplain, GovConstant.GOV_EXPLAIN_GENERAL);
	        }

	        // 关联机构
	        List<GovRelation> explainOrganization = govRelationMapper.selectByNodeAndIdAndType(GovConstant.GOV_EXPLAIN_NODE,
	                govPolicyExplain.getId(), GovConstant.GOV_EXPLAIN_ORGANIZATION);
	        for (GovRelation gr : explainOrganization) {
	            govRelationMapper.deleteOne(GovConstant.GOV_EXPLAIN_NODE, gr.getRelationId(), gr.getCorrelationId(),
	                    gr.getTypeId());
	        }
	        if (explainDTO.getOrganizationList() != null && explainDTO.getOrganizationList().size() > 0) {
	            saveRelation(explainDTO.getOrganizationList(), govPolicyExplain, GovConstant.GOV_EXPLAIN_ORGANIZATION);
	        }

			
		}
	    // 关联新增，直接使用id
	    private GovRelation findOrCreate(GovPolicyExplain explain, Integer id, String number) {
	        GovRelation bean = govRelationMapper.findOne(GovConstant.GOV_EXPLAIN_NODE, explain.getId(), id, number);
	        if (bean == null) {
	            GovRelationType type = govRelationTypeMapper.selectByNumber(number);
	            bean = new GovRelation(GovConstant.GOV_EXPLAIN_NODE, explain.getId(), id, type.getId());
	            return bean;
	        }
	        return null;
	    }

	    // 保存关联
	    private void saveRelation(List<Integer> ids, GovPolicyExplain explain, String number) {
	        for (Integer id : ids) {
	            GovRelation relation = findOrCreate(explain, id, number);
	            if (relation != null) {
	                govRelationMapper.insert(relation);
	            }
	        }
	    }
		
}
