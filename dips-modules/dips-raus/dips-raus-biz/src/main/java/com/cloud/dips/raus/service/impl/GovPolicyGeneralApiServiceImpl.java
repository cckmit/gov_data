package com.cloud.dips.raus.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.entity.GovPolicyGeneral;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.pojo.GeneralApiPOJO;
import com.cloud.dips.raus.api.vo.GeneralApiVO;
import com.cloud.dips.raus.mapper.GovPolicyGeneralApiMapper;
import com.cloud.dips.raus.service.GovPolicyGeneralApiService;

import lombok.AllArgsConstructor;


/**
 * @author C.Z.H
 */
@Service("govPolicyGeneralApiService")
@AllArgsConstructor
public class GovPolicyGeneralApiServiceImpl extends ServiceImpl<GovPolicyGeneralApiMapper, GovPolicyGeneral> implements GovPolicyGeneralApiService {

	private final GovPolicyGeneralApiMapper policyGeneralApiMapper;
	@Autowired
	private RemoteDictService remoteDictService;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<GeneralApiVO> selectGeneralApi(Query query,PreposeUser one,String queryTime) {
    	// 获取要获取的年月数据
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	String currentMonth = format.format(c.getTime());
    	if ("6M".equals(queryTime)) {
    		c.add(Calendar.MONTH, -6);	
    	}
    	if ("1Y".equals(queryTime)) {
    		c.add(Calendar.YEAR, -1);
    	}
    	if ("3Y".equals(queryTime)) {
    		c.add(Calendar.YEAR, -3);
    	}
    	if ("5Y".equals(queryTime)) {
    		c.add(Calendar.YEAR, -5);
    	}
    	String beforeMonth = format.format(c.getTime());
    	List<GeneralApiVO> generalApiVOList = new ArrayList<GeneralApiVO>();
        List<GeneralApiPOJO> selectGeneralApi = policyGeneralApiMapper.selectGeneralApi(query,one,beforeMonth,currentMonth);
        if (null != selectGeneralApi) {
        	// 设置通用的正文，数据库字段text格式，一同搜索出来速度很慢
        	List<GeneralApiPOJO> setGeneralText = setGeneralText(selectGeneralApi);
        	// 设置主题，规模，对象等字典值
        	setGeneralValue(setGeneralText);
        	// 只将需要的字段复制到VO上，显示给用户
        	for (GeneralApiPOJO generalApiPOJO : selectGeneralApi) {
        		GeneralApiVO generalApiVO = new GeneralApiVO();
        		BeanUtils.copyProperties(generalApiPOJO, generalApiVO);
        		generalApiVOList.add(generalApiVO);
        	}
        }
        query.setRecords(generalApiVOList);
        return query;
    }

	private List<GeneralApiPOJO> setGeneralText(List<GeneralApiPOJO> selectGeneralApi) {
		List<Integer> generalApiId = new ArrayList<Integer>();
		for (GeneralApiPOJO generalApiPOJO : selectGeneralApi) {
			generalApiId.add(generalApiPOJO.getId());
		}
		List<GeneralApiPOJO> selectGeneralText = policyGeneralApiMapper.selectGeneralText(generalApiId);
		List<GeneralApiPOJO> selectGeneralTag = policyGeneralApiMapper.selectGeneralTag(generalApiId);
		List<GeneralApiPOJO> selectGeneralDispatch = policyGeneralApiMapper.selectGeneralDispatch(generalApiId);
		List<GeneralApiPOJO> selectGeneralUnion = policyGeneralApiMapper.selectGeneralUnion(generalApiId);
		for (GeneralApiPOJO generalApiPOJO : selectGeneralApi) {
			for (GeneralApiPOJO generalPOJO : selectGeneralText) {
				if (generalApiPOJO.getId().equals(generalPOJO.getId())) {
					generalApiPOJO.setText(generalPOJO.getText());
					break;
				}
			}
			for (GeneralApiPOJO generalPOJO : selectGeneralTag) {
				if (generalApiPOJO.getId().equals(generalPOJO.getTagId())) {
					generalApiPOJO.getTagList().add(generalPOJO.getTagName());
				}
			}
			for (GeneralApiPOJO generalPOJO : selectGeneralDispatch) {
				if (generalApiPOJO.getId().equals(generalPOJO.getDispatchId())) {
					generalApiPOJO.getDispatchList().add(generalPOJO.getDispatchName());
				}
			}
			for (GeneralApiPOJO generalPOJO : selectGeneralUnion) {
				if (generalApiPOJO.getId().equals(generalPOJO.getUnionId())) {
					generalApiPOJO.getUnionList().add(generalPOJO.getUnionName());
				}
			}
		}
		return selectGeneralApi;
	}

	private void setGeneralValue(List<GeneralApiPOJO> setGeneralText) {
		String[] numberList = new String[] {"POLICY_LEVEL","DECLARE_TARGET","POLICY_THEME","POLICY_SCALE","POLICY_INDUSTRY"};
        Map<String, List<DictValueVO>> dictMap = remoteDictService.getDictMap(numberList);
        for (GeneralApiPOJO generalApi : setGeneralText) {
			if (StringUtils.isNotBlank(generalApi.getLevel())) {
				List<DictValueVO> list = dictMap.get("POLICY_LEVEL");
				for (DictValueVO dictValueVO : list) {
					if (dictValueVO.getKey().equals(generalApi.getLevel())) {
						generalApi.getLevelList().add(dictValueVO.getValue());
						break;
					}
				}
			}
			if (StringUtils.isNotBlank(generalApi.getTarget())) {
				String[] targetSplit = generalApi.getTarget().split(",");
				List<DictValueVO> list = dictMap.get("DECLARE_TARGET");
				for (int i = 0; i < targetSplit.length; i++) {
					for (DictValueVO dictValueVO : list) {
						if (dictValueVO.getKey().equals(targetSplit[i])) {
							generalApi.getTargetList().add(dictValueVO.getValue());
							break;
						}
					}
					
				}
			}
			if (StringUtils.isNotBlank(generalApi.getTheme())) {
				String[] themeSplit = generalApi.getTheme().split(",");
				List<DictValueVO> list = dictMap.get("POLICY_THEME");
				for (int i = 0; i < themeSplit.length; i++) {
					for (DictValueVO dictValueVO : list) {
						if (dictValueVO.getKey().equals(themeSplit[i])) {
							generalApi.getThemeList().add(dictValueVO.getValue());
							break;
						}
					}
					
				}
			}
			if (StringUtils.isNotBlank(generalApi.getScale())) {
				String[] scaleSplit = generalApi.getScale().split(",");
				List<DictValueVO> list = dictMap.get("POLICY_SCALE");
				for (int i = 0; i < scaleSplit.length; i++) {
					for (DictValueVO dictValueVO : list) {
						if (dictValueVO.getKey().equals(scaleSplit[i])) {
							generalApi.getScaleList().add(dictValueVO.getValue());
							break;
						}
					}
					
				}
			}
			if (StringUtils.isNotBlank(generalApi.getIndustry())) {
				String[] industrySplit = generalApi.getIndustry().split(",");
				List<DictValueVO> list = dictMap.get("POLICY_INDUSTRY");
				for (int i = 0; i < industrySplit.length; i++) {
					for (DictValueVO dictValueVO : list) {
						if (dictValueVO.getKey().equals(industrySplit[i])) {
							generalApi.getIndustryList().add(dictValueVO.getValue());
							break;
						}
					}
					
				}
			}
		}
	}
}



