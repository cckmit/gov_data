package com.cloud.dips.gov.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;

/**
 * 通用政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:19:21
 */
public interface GovPolicyGeneralService extends IService<GovPolicyGeneral> {

    /**
     * 添加通用政策
     *
     * @param generalDto
     * @return
     */
    R insertPolicyGeneral(GeneralDTO generalDto) ;

    Page<GeneralVO> selectAllPage(Query query);

    /**
     * 小程序调用
     *
     * @param
     * @return
     * @author yinzan
     * @description //TODO
     * @date 下午2:51 2019/1/16
     */
    Page<GeneralVO> selectWechatPage(Query query);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    Boolean logicDelete(Integer[] ids);

    /**
     * 修改
     *
     * @param generalDTO
     */
    Boolean updateGeneral(GeneralDTO generalDTO);

    /**
     * 根据id查找单条数据
     *
     * @param id
     * @return
     */
    GeneralVO selectGeneralVOById(Integer id);

    /**
     * @param query
     * @return
     */
    Page<GeneralVO> selectPageByTagId(Query query);

    Page<GeneralVO> selectRecyclePage(Query query);

    Boolean recycleDelete(Integer id);

    Boolean repeat(String title);

    Boolean retreatPolicy(Query query);

    Page<GeneralVO> selectSelfPage(Query query);

    Boolean doExamine(List<Integer> id);

    Boolean commit(List<Integer> ids);

    Boolean reCommit(List<Integer> ids);

    Boolean giveUpProcess(List<Integer> ids);

    Boolean accept(Integer id);

    Boolean disExamine(Query query);

	Page<GeneralVO> selectConsolePage(Query query);

	List<Map<String, Object>> getGeneralAndDeclareList(Query query);
	
    Page<GeneralVO> selectExaminePage(Query query);

    Boolean insertPolicyGeneralAndCommit(GeneralDTO generalDto);

    Boolean updateGeneralAndCommit(GeneralDTO generalDTO);

    /**
     * 相关通用政策查询
     *
     * @param ids
     * @return
     */
    List<GeneralVO> selectRelevant(String ids,Integer id);

    /**
     * 根据政策依据、匹配通用政策原文
     *
     * @param ids
     * @return
     */
    List<GeneralVO> selctRelation(String ids, Integer id);
    
	/**
	  * 通用政策比对
	 */
	List<ComparisonGeneralVO> selectComparisonByGeneral(Integer[] ids);

	GeneralVO selectGeneralVOByIdForConsole(Integer ids);
	
	/**
	 * 全文下载
	 */
	public void fullDownloadGeneral(HttpServletResponse response, Integer id);

	/**
	 * 根据用户自定义条件查询信息的id
	 *
	 * @param params
	 * @return
	 */
	List<Long> gainList(Map<String, Object> params);

	List<GovPolicyGeneral> queryByInfos(List<Long> ids);
}

