package com.cloud.dips.gov.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.SenseDTO;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.api.vo.SenseVO;

/**
 * 政策常识模型业务层
 *
 * @author Z.Y.S
 * @date 2018-09-11 10:14:26
 */
public interface GovPolicySenseService extends IService<GovPolicySense> {

	/**
	 * 逻辑删除，只修改状态值
	 *
	 * @param id
	 * @return
	 */
	Boolean deletePolicySense(Integer id);

	/**
	 * 根据ID批量删除
	 *
	 * @param ids
	 * @return
	 */
	Boolean deletePolicySenseAll(Integer[] ids);

	/**
	 * 添加政策常识
	 */
	Boolean insertPolicySense(SenseDTO senseDTO);

	/**
	 * 修改政策常识
	 *
	 * @param senseDTO
	 * @return
	 */
	Boolean updatePolicySenseById(SenseDTO senseDTO);

	/**
	 * 查看政策常识
	 */
	Page<SenseVO> selectAllPolicySense(Query query);

	/**
	 * 回收站政策常识列表
	 */
	Page<SenseVO> selectRecyclePage(Query query);

	/**
	 * 回收站彻底删除
	 */
	Boolean recycleDelete(Integer id);

	Boolean repeat(String title);

	Boolean retreatPolicy(Query query);

	Page<SenseVO> selectSelfPage(Query query);

	Boolean commit(List<Integer> ids);

	Boolean reCommit(List<Integer> ids);

	Boolean giveUpProcess(List<Integer> ids);

	Boolean accept(Integer id);

	Boolean doExamine(List<Integer> id);

	Boolean disExamine(Query query);

	Page<GeneralVO> selectExaminePage(Query query);

	Page<SenseVO> selectConsolePolicySense(Query query);
}

