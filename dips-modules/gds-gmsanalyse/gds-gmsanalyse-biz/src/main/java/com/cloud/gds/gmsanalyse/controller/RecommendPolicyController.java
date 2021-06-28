package com.cloud.gds.gmsanalyse.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gmsanalyse.service.RecommendPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用政策推荐
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
@RestController
@RequestMapping("/recommend")
public class RecommendPolicyController {

	private final RecommendPolicyService recommendPolicyService;

	@Autowired
	public RecommendPolicyController(RecommendPolicyService recommendPolicyService) {
		this.recommendPolicyService = recommendPolicyService;
	}

	/**
	 * 通用政策推荐
	 * TODO 2019-5-6 15:24:00
	 *
	 * @param id 通用政策id
	 */
	@GetMapping("/relevant/{id}")
	public R generalRelevant(@PathVariable Integer id) {
		return new R<>(recommendPolicyService.generalRelevant(id));
	}

}
