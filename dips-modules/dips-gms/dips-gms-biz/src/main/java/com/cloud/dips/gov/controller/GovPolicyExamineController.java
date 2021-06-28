package com.cloud.dips.gov.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.vo.ExamineVO;
import com.cloud.dips.gov.service.GovPolicyDeclareService;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;
import com.cloud.dips.gov.service.GovPolicyExamineRecordService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;


/**
 * 政策审核模型
 *
 * @author Wave
 * @date 2019-01-17 16:23:50
 */
@RestController
@RequestMapping("/policy/examine")
@AllArgsConstructor
public class GovPolicyExamineController {

	private final GovPolicyExamineCountService govPolicyExamineCountService;
	private final GovPolicyExamineRecordService govPolicyExamineRecordService;
	
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/countPage")
	@ApiOperation(value = "分页查询审核统计", notes = "分页查询审核统计")
	public Page countPage(@RequestParam Map<String, Object> params) {
		return govPolicyExamineCountService.selectPolicyExamineCountPage(new Query<>(params));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/recordPage")
	@ApiOperation(value = "分页查询审核记录", notes = "分页查询审核记录")
	public Page recordPage(@RequestParam Map<String, Object> params) {
		return govPolicyExamineRecordService.selectPolicyExamineRecordPage(new Query<>(params));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/countForProcessor")
	@ApiOperation(value = "分页查询审核统计记录", notes = "分页查询审核统计记录")
	public Page countForProcessor(@RequestParam Map<String, Object> params) {
		return govPolicyExamineRecordService.selectCountForProcessorPage(new Query<>(params));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/countAll")
	@ApiOperation(value = "查询审核统计总数", notes = "查询审核统计总数")
	public R countAll(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyExamineRecordService.selectExamineCount(new Query<>(params)));
	}
}
