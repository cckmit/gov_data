package com.cloud.gds.gmsanalyse.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyGeneralService;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.gds.gmsanalyse.bo.PolicyRecommendBo;
import com.cloud.gds.gmsanalyse.service.RecommendAlgorithmService;
import com.cloud.gds.gmsanalyse.service.RecommendPolicyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
@Service
public class RecommendPolicyServiceImpl implements RecommendPolicyService {

	private final RecommendAlgorithmService recommendAlgorithmService;

	private final RemoteGovPolicyGeneralService govPolicyGeneralService;

	@Autowired
	public RecommendPolicyServiceImpl(RemoteGovPolicyGeneralService govPolicyGeneralService, RecommendAlgorithmService recommendAlgorithmService) {
		this.govPolicyGeneralService = govPolicyGeneralService;
		this.recommendAlgorithmService = recommendAlgorithmService;
	}


	@Override
	public List<GovPolicyGeneral> generalRelevant(Integer id) {
		GeneralVO data = govPolicyGeneralService.info(id).getData();
		PolicyRecommendBo recommendBo = new PolicyRecommendBo();
		BeanUtils.copyProperties(data, recommendBo);
		recommendBo.setBelongToGeneral(true);
		recommendBo.setNum(5);
		String recommend = recommendAlgorithmService.generalRecommend(recommendBo);
		// 如果有推荐的政策,则进行处理
		List<Long> ids = new ArrayList<>();
		if (recommend != null) {
			ids = (List) JSON.parseArray(recommend);
		}
		if (ids.size() > 0) {
			return govPolicyGeneralService.selectByIds(ids);
		} else {
			return null;
		}

	}
}
