package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.dto.InformationDTO;
import com.cloud.dips.gov.api.entity.GovInformation;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.InformationVO;

import java.util.List;

/**
 * 政策资讯模型服务类
 *
 * @author CUI.CAN
 * @date 2018-09-11 10:27:23
 */
public interface GovInformationService extends IService<GovInformation> {

	/**
	 * 查询资讯列表
	 *
	 * @param query
	 * @return
	 */
	Page selectInformationPage(Query query);/**

	 * 微信小程序查询资讯列表
	 *
	 * @param query
	 * @return
	 */
	Page selectInformationWechatPage(Query query);

	/**
	 * 根据id查询资讯信息
	 *
	 * @param id
	 * @return
	 */
	InformationVO selectInformationById(Integer id);

	/**
	 * 插入资讯信息
	 *
	 * @param informationDTO
	 * @return
	 */
	R insertInformation(InformationDTO informationDTO);

	/**
	 * 修改资讯信息
	 *
	 * @param informationDTO
	 * @return
	 */
	Boolean updateGovInformationById(InformationDTO informationDTO);

	/**
	 * 删除资讯
	 *
	 * @param ids
	 * @return
	 */
	Boolean deleteByIds(List<Integer> ids);

	/**
	 * 根据标签id查询列表
	 *
	 * @param query
	 * @return
	 */
	Page selectPageByTagId(Query query);

	/**
	 * 回收站列表
	 *
	 * @param query
	 * @return
	 */
	Page selectRecyclePage(Query<Object> query);

	/**
	 * 回收站彻底删除
	 *
	 * @param id
	 * @return
	 */
	Boolean recycleDelete(Integer id);

	/**
	 * 回收站查询单个
	 * @param id
	 * @return
	 */
	InformationVO selectRecycleById(Integer id);

	/**
	 * 查重
	 * */
	Boolean repeat(String title);
	
	Boolean retreatPolicy(Query query);

	Page<InformationVO> selectSelfPage(Query query);

	Boolean commit(List<Integer> ids);

	Boolean reCommit(List<Integer> ids);

	Boolean giveUpProcess(List<Integer> ids);

	Boolean accept(Integer id);

	Boolean doExamine(List<Integer> id);

	Boolean disExamine(Query query);

	Page<InformationVO> selectExaminePage(Query query);

	Boolean insertInformationAndCommit(InformationDTO informationDTO);

	Boolean updateGovInformationByIdAndCommit(InformationDTO informationDTO);

	Page<InformationVO> selectConsolePage(Query query);

	/**
	 * 相关政策资讯匹配度查询
	 * @param ids
	 * @return
	 */
	List<InformationVO> selectMatching(String ids,Long id);

	/**
	 * 根据资讯标签匹配 通用、申报 政策
	 * @param ids
	 *
	 * @return
	 */
	List<InformationVO> selectCommonMatching(String ids);

	/**
	 * 根据id查询资讯信息
	 *
	 * @param id
	 * @return
	 */
	InformationVO selectInformationByIdForConsole(Integer id);
}

