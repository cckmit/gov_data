package com.cloud.gds.gmsanalyse.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.vo.Echarts;

import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
public interface PolicyAnalyseFeatureService extends IService<PolicyAnalyseFeature> {


	/**
	 * 批量保存
	 *
	 * @param list
	 * @return
	 */
	boolean batchInsert(List<PolicyAnalyseFeature> list);

	/**
	 * 根据分析表的id查询其特征信息
	 *
	 * @param analyseId 分析表的id
	 * @return
	 */
	List<PolicyAnalyseFeature> info(Long analyseId);

}
