package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用政策模型
 *
 * @author yangyin
 * @date 2018-09-11 11:19:21
 */
public interface GovPolicyGeneralMapper extends BaseMapper<GovPolicyGeneral> {
	@SuppressWarnings("rawtypes")
	List<GeneralVO> selectAll(Query query, Map<String, Object> map);

	/**
	 * 小程序调用接口
	 *
	 * @param query
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<GeneralVO> selectWxAll(Query query, Map<String, Object> map);

	/**
	 * 删除关联
	 *
	 * @param node, relationId
	 * @return
	 */
	Boolean deleteRelation(@Param("node") String node, @Param("relationId") Integer relationId);


	/**
	 * 修改状态  逻辑删除
	 *
	 * @param id
	 * @return
	 */
	Boolean updateByGeneralId(@Param("id") Integer id);

	/**
	 * 根据id查找
	 *
	 * @param id
	 * @return
	 */
	GeneralVO selectGeneralVO(@Param("id") Integer id);

	/**
	 * 根据标签id查通用政策
	 */
	List<GeneralVO> selectGeneralVoPageByTagId(Query query, @Param("tagId") Object tagId);

	/**
	 * 垃圾站
	 */
	List<GeneralVO> selectRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);

	/**
	 * 查重
	 */
	List<GovPolicyGeneral> repeat(String title);


	List<GeneralVO> selectVagueGeneral(@Param("time") Object sort, @Param("views") Object views, @Param("title") Object title,
									   @Param("page") Integer page, @Param("size") Integer size);

	/**
	 * 统计通用主题
	 */
	Integer selectCountGeneral(@Param("theme") String theme);

	/**
	 * 查询个人政策
	 */
	List<GeneralVO> selectSelfPage(Query query, Map<String, Object> map);


	/**
	 * 查询审核政策
	 */
	List<GeneralVO> selectExaminePage(Query query, Map<String, Object> map);

	/**
	 * 统计全局模糊条数
	 *
	 * @param title
	 * @return
	 */
	Integer selectVagueCount(@Param("title") String title);

	Integer updateForGiveUpProcess(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	Boolean viewUp(@Param("id") Object id);

	/**
	 * 通用比对
	 */
	List<ComparisonGeneralVO> selectComparisonByGeneral(@Param("ids") List<Integer> ids);


	List<GeneralVO> selectAllForConsole(Query query, Map<String, Object> map);

	List<Map<String, Object>> getGeneralAndDeclareList(Query query, @Param("title") Object title);


	/**
	 * 相关通用政策查询
	 *
	 * @param ids
	 * @param tags
	 * @param id
	 * @return
	 */
	List<GeneralVO> selectRelevant(@Param("ids") Object ids, @Param("tags") List<Integer> tags, @Param("id") Integer id);

	/**
	 * 根据政策依据 匹配通用政策原文
	 *
	 * @param policyList
	 * @return
	 */
	List<GeneralVO> selctRelation(@Param("policyList") List<Integer> policyList, @Param("id") Integer id);

	List<Integer> selectAllPolicyCount();

	GeneralVO selectGeneralVOForConsole(@Param("id") Integer id);

	Integer checkTag(@Param("id") Integer id);

	Integer checkDispatch(@Param("id") Integer id);

	/**
	 * 查询通用政策正文
	 */
	public String selectGeneralText(@Param("id") Integer id);

	/**
	 * 选择标签
	 *
	 * @param tagApiId
	 * @return
	 */
	List<CommonVO> selectNewGeneralTag(@Param("tagApiId") List<Integer> tagApiId);

	/**
	 * 选择发文部门
	 *
	 * @param dispatchId
	 * @return
	 */
	List<CommonVO> selectNewGeneralDispatch(@Param("dispatchId") List<Integer> dispatchId);

	/**
	 * 根据查询条件获取信息的id
	 *
	 * @param params
	 * @return
	 */
	List<Long> gainList(Map<String, Object> params);

	/**
	 * 根据ids查询政策相关信息
	 *
	 * @param ids
	 * @return
	 */
	List<GovPolicyGeneral> queryByInfos(@Param("ids") List<Long> ids);
}
