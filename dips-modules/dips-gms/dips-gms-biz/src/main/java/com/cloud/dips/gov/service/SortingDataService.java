package com.cloud.dips.gov.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.ExplainDTO;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.SortDataVO;
/**
 * 
 * @author johan
 * 2019年1月3日
 *SortingDataService.java
 */
public interface SortingDataService {

	public Page<SortDataVO> sortData(SortingDataDTO sortingDataDTO,Query query,String mark);
	
	public Page<SortDataVO> totalSearch(Query query);
	
	public  JSONObject selectAllCount(List<DictValueVO> dictValueParents);

	public List<Integer> selectPolicyCount();
	/**
	  * 匹配特殊字符和空格，并添加全文搜索匹配符
	 */
	public String replaceSpecialSign(String titleSObject);
	
	Boolean addRelation(GeneralDTO generalDTO);
	public void explainMethod(ExplainDTO explainDTO, GovPolicyExplain govPolicyExplain);
}
