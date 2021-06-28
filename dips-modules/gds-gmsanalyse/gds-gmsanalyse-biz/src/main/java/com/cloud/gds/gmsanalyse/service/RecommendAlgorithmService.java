package com.cloud.gds.gmsanalyse.service;

import com.cloud.gds.gmsanalyse.bo.PolicyRecommendBo;

/**
 * 政策推荐算法
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
public interface RecommendAlgorithmService {

	/**
	 * 政策推荐算法
	 *
	 * @param recommendBo
	 * @return
	 */
	String generalRecommend(PolicyRecommendBo recommendBo);
}
