package com.cloud.gds.gmsanalyse.service;

import com.cloud.dips.gov.api.entity.GovPolicyGeneral;

import java.util.List;

/**
 * 政策推荐
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
public interface RecommendPolicyService {

	/**
	 * 通用政策推荐
	 *
	 * @param id
	 */
	List<GovPolicyGeneral> generalRelevant(Integer id);

}
