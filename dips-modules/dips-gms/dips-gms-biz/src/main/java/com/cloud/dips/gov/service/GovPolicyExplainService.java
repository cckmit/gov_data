package com.cloud.dips.gov.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.dto.ExplainDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;

/**
 * 政策解读
 *
 * @author C.Z.H
 * @date 2018-09-11 11:27:05
 */
public interface GovPolicyExplainService extends IService<GovPolicyExplain> {
	/**
	 * 分页查询
	 *
	 * @param query
	 * @return
	 */
	Page<ExplainVO> selectAllPage(Query query);

	/**
	 * 小程序分页解读查询
	 * @param query
	 * @return
	 */
	Page<ExplainVO> selectWechatPage(Query query);

	/**
	 * 新增
	 */
	R insertExplain(ExplainDTO explainDTO);

	/**
	 * 修改
	 *
	 * @param explainDTO
	 * @return
	 */
	Boolean updateExplain(ExplainDTO explainDTO);

	/**
	 * 逻辑删除   修改状态值
	 */
	Boolean deleteExplain(Integer[] ids);

	GovPolicyExplain update(GovPolicyExplain govPolicyExplain);

	GovPolicyExplain save(GovPolicyExplain govPolicyExplain);

	ExplainVO selectAllVo(Integer id);

	/**
	 * 浏览次数加1
	 *
	 * @param id
	 */
	void updateViews(Integer id);

	Page<ExplainVO> selectPageByTagId(Query query);

	Page<ExplainVO> selectRecyclePage(Query query);

	ExplainVO selectRecycleById(Integer id);

	Boolean recycleDelete(Integer id);

	Boolean repeat(String title);

	Page<ExplainVO> selectSelfPage(Query query);

	Boolean commit(List<Integer> ids);

	Boolean reCommit(List<Integer> ids);

	Boolean giveUpProcess(List<Integer> ids);

	Boolean accept(Integer id);

	Boolean doExamine(List<Integer> id);

	Boolean disExamine(Query query);

	Boolean retreatPolicy(Query query);

	Page<GeneralVO> selectExaminePage(Query query);

	Boolean insertExplainAndCommit(ExplainDTO explainDTO);

	Boolean updateExplainAndCommit(ExplainDTO explainDTO);

	Page<ExplainVO> selectConsolePage(Query query);

	/**
	 * 查询相关政策解读   通用政策
	 * @param id
	 * @return
	 */
	List<ExplainVO> selectGenerList(Integer id);


	/**
	 * 查询相关解读 根据政策原文 匹配申报、通用政策
	 * @param ids
	 * @return
	 */
	List<ExplainVO> findPolicyText (String ids);
	/**
	 * 其它解读 根据政策原文 匹配政策解读政策
	 * @param ids
	 * @return
	 */
	List<ExplainVO> findInterPreTaboo(String ids,Integer id);

	ExplainVO selectAllVoForConsole(Integer ids);
}

