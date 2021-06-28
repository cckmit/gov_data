package com.cloud.gds.gmsanalyse.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.gmsanalyse.constant.AnalyseConstant;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.mapper.PolicyAnalyseMapper;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseFeatureService;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseService;
import com.cloud.gds.gmsanalyse.vo.AnalyseDetails;
import com.cloud.gds.gmsanalyse.vo.AxisEcharts;
import com.cloud.gds.gmsanalyse.vo.Echarts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.tools.Klist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Service
public class PolicyAnalyseServiceImpl extends ServiceImpl<PolicyAnalyseMapper, PolicyAnalyse> implements PolicyAnalyseService {

	private final PolicyAnalyseMapper policyAnalyseMapper;

	@Autowired
	private PolicyAnalyseFeatureService policyAnalyseFeatureService;

	@Autowired
	public PolicyAnalyseServiceImpl(PolicyAnalyseMapper policyAnalyseMapper) {
		this.policyAnalyseMapper = policyAnalyseMapper;
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<PolicyAnalyse> p = new Page<PolicyAnalyse>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<PolicyAnalyse> e = new EntityWrapper<PolicyAnalyse>();
		String analyseName = params.getOrDefault("analyseName", "").toString();
		if (StrUtil.isNotBlank(analyseName)) {
			e.like("analyse_name", SpecialStringUtil.escapeExprSpecialWord(analyseName));
		}
		e.eq("is_deleted", AnalyseConstant.FALSE);
		// 用户只能查询自己部门的规则
		assert SecurityUtils.getUser() != null;
		e.eq("create_user", SecurityUtils.getUser().getId());
		return selectPage(p, e);
	}

	@Override
	public PolicyAnalyse save(GovPolicyDto govPolicyDto) {
		PolicyAnalyse policyAnalyse = new PolicyAnalyse();
		policyAnalyse.setAnalyseName(govPolicyDto.getAnalyseName());
		policyAnalyse.setFeatureNum(govPolicyDto.getFeatureNum());
		// 政策分析刚添加的时候政策默认处于正在分析的状态中,数据库默认值是0
//		assert SecurityUtils.getUser() != null;
//		policyAnalyse.setCreateUser(SecurityUtils.getUser().getId());
		insert(policyAnalyse);
		return policyAnalyse;
	}

	@Override
	public boolean individuationUpdate(PolicyAnalyse policyAnalyse) {
		return SqlHelper.retBool(policyAnalyseMapper.updateById(policyAnalyse));
	}

	@Override
	public boolean queryDelete(Long id) {
		return policyAnalyseMapper.queryDelete(id);
	}

	@Override
	public AnalyseDetails info(Long id) {
		PolicyAnalyse analyse = selectById(id);
		// todo 总结性字体是否使用序列化 2019年5月5日15:01:59
		String analyseSummary = analyse.getAnalyseSummary();

		AnalyseDetails details = new AnalyseDetails();
		details.setAnalyseSummary(analyseSummary);
		List<PolicyAnalyseFeature> list = policyAnalyseFeatureService.info(id);
		details.setEchartsList(info2Echarts(list));
		details.setAxisEcharts(info2AxisEcharts(list));
		return details;
	}

	private List<Echarts> info2Echarts(List<PolicyAnalyseFeature> list){
		if (list.size()>0){
			List<Echarts> echartsList = new ArrayList<>();
			for (PolicyAnalyseFeature feature :list) {
				Echarts echarts = new Echarts();
				echarts.setName(feature.getFeatureName());
				echarts.setValue(feature.getFeatureNum());
				echartsList.add(echarts);
			}
			return echartsList;
		}else {
			return null;
		}
	}

	private AxisEcharts info2AxisEcharts(List<PolicyAnalyseFeature> list){
		AxisEcharts axisEcharts = new AxisEcharts();
		List<String> xAxis = new ArrayList<>();
		List<Integer> yAxis = new ArrayList<>();
		if (list.size()>0){
			for (PolicyAnalyseFeature feature :list) {
				xAxis.add(feature.getFeatureName());
				yAxis.add(feature.getFeatureNum());
			}
			axisEcharts.setXAxis(xAxis);
			axisEcharts.setYAxis(yAxis);
			return axisEcharts;
		}else {
			return null;
		}
	}

}
