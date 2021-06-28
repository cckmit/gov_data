package com.cloud.dips.gov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.gov.service.SortingDataService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author johan
 * 2018年12月13日
 * 数据整理
 */
@RestController
@RequestMapping("/sortingData")
public class SortingDataController {

	@Autowired
	private SortingDataService sortingDataService;
	@Autowired
	private RemoteDictService remoteDictService;
	
	
	/**
	  * 订阅信息推送
	 * @param sortingDataDTO
	 * @return
	 */
	@PostMapping("/queryPush")
	public Page<SortDataVO> queryPush(String jsonString,Integer page,Integer limit,String mark) {
		SortingDataDTO sortingDataDTO = JSON.parseObject(jsonString, SortingDataDTO.class);
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("page", page);
		map.put("limit", limit);
		Page<SortDataVO> sortData = sortingDataService.sortData(sortingDataDTO,new Query<>(map),mark);
		return sortData;
	}
	
	/**
	 * 全局模糊搜索
	 */
	@GetMapping("/globalSearch")
	@ApiOperation(value = "全局模糊搜索", notes = "全局模糊搜索", httpMethod = "GET")
	@SysLog(value ="全局模糊搜索",role = EnumRole.WEB_TYE)
	public Page globalSearch(@RequestParam Map<String, Object> params) {
		Page page = sortingDataService.totalSearch(new Query<>(params));
		return page;
	}
	
	/**
	 * 统计引用的主题数量
	 */
	@GetMapping("/selectThemeCount")
	@ApiOperation(value = "引用的主题数量", notes = "引用的主题数量", httpMethod = "GET")
	public JSONObject selectThemeCount() {
		List<DictValueVO> dictValueParents = remoteDictService.dictValueParents(GovConstant.DICT_VALUE);
		JSONObject selectAllCount = sortingDataService.selectAllCount(dictValueParents);
		return selectAllCount;
	}
	
	/**
	 * 查询各类政策条数
	 */
	@GetMapping("/selectPolicyCount")
	@ApiOperation(value = "查询各类政策条数", notes = "查询各类政策条数", httpMethod = "GET")
	public List<Integer> selectPolicyCount() {
		return sortingDataService.selectPolicyCount();
	} 
}
