package com.cloud.gds.gmsanalyse.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.vo.AnalyseDetails;

import java.util.Map;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
public interface PolicyAnalyseService extends IService<PolicyAnalyse> {

	/**
	 * 分页查询
	 *
	 * @param params 参数
	 * @return
	 */
	Page queryPage(Map<String, Object> params);

	/**
	 * 保存信息
	 *
	 * @param govPolicyDto
	 * @return
	 */
	PolicyAnalyse save(GovPolicyDto govPolicyDto);

	/**
	 * 自定义更新
	 *
	 * @param policyAnalyse
	 * @return
	 */
	boolean individuationUpdate(PolicyAnalyse policyAnalyse);

	/**
	 * 根据id删除分析数据
	 *
	 * @param id
	 * @return
	 */
	boolean queryDelete(Long id);

	AnalyseDetails info(Long id);
}
