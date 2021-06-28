package com.cloud.dips.gov.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.dto.DeclareDTO;
import com.cloud.dips.gov.api.entity.GovPolicyDeclare;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;

/**
 * 申报政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:23:50
 */
public interface GovPolicyDeclareService extends IService<GovPolicyDeclare> {
	DeclareVO selectDeclareVOById(Integer id);
	
	DeclareVO selectDeclareVOByIdForConsole(Integer id);

	Page<DeclareVO> selectAllPage(Query query);

	/**
	 * 小程序端调用
	 * @param query
	 * @return
	 */
	Page<DeclareVO> selectAllWxPage(Query query);



	R insertDeclare(DeclareDTO declareDTO);

	GovPolicyDeclare save(GovPolicyDeclare declare);

	void updateDeclare(DeclareDTO declareDTO);

	GovPolicyDeclare update(GovPolicyDeclare declare);

	void deleteDeclare(Integer id);

	Page<DeclareVO> selectPageByTagId(Query query);

	Page<DeclareVO> selectRecyclePage(Query query);

	DeclareVO selectRecycleById(Integer id);

	Boolean recycleDelete(Integer id);

	Boolean repeat(String title);

	Boolean retreatPolicy(Query query);

	Page<DeclareVO> selectSelfPage(Query query);

	Boolean commit(List<Integer> ids);

	Boolean reCommit(List<Integer> ids);

	Boolean giveUpProcess(List<Integer> ids);

	Boolean accept(Integer id);

	Boolean doExamine(List<Integer> id);

	Boolean disExamine(Query query);

	Page<DeclareVO> selectExaminePage(Query query);

	Boolean insertDeclareAndCommit(DeclareDTO declareDTO);

	Boolean updateDeclareAndCommit(DeclareDTO declareDTO);

	Page<DeclareVO> selectConsolePage(Query query);
	/**
	 * 相关申报政策匹配度查询
	 * @param ids
	 * @return
	 */
	List<DeclareVO> selectMatching(String ids);


	/**
	 * 根据政策依据、匹配申报政策
	 * @param ids
	 * @return
	 */
	List<DeclareVO> selctRelation(String ids,Integer id);
	
	/**
	 * 申报对比
	 * @param ids
	 * @return
	 */
	public List<ComparisonVO> selectComparisonByDeclare(Integer[] ids);

}

