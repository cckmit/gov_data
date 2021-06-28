package com.cloud.dips.gov.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicyDeclare;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.SortDataVO;

/**
 * 申报政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:23:50
 */
@Mapper
public interface GovPolicyDeclareMapper extends BaseMapper<GovPolicyDeclare> {

	DeclareVO selectDeclareVOById(Integer id);

	List selectDeclareVoPage(Query query, @Param("title") Object title, @Param("level") Object level,
							 @Param("status") Object status, @Param("special") Object special,
							 @Param("startTime") Object startTime, @Param("endTime") Object endTime,
							 @Param("username") Object username,
							 @Param("target") Object target, @Param("mode") Object mode,
							 @Param("formality") Object formality, @Param("support") Object support,
							 @Param("theme") Object theme, @Param("fund") Object fund,
							 @Param("scale") Object scale, @Param("industry") Object industry,
							 @Param("style") Object style,@Param("sort") Object sort,@Param("regionArr") Object regionArr);

	List<GovPolicyDeclare> selectDeclareVoPageByTagId(Query query, @Param("tagId") Object tagId);

	List selectRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);

	DeclareVO selectRecycleById(Integer id);

	List<GovPolicyDeclare> repeat(@Param("title") String title);

	/**
	 * 根据key number查询字典value，该方法可以提出来单独使用
	 */
	String selectDictLabelByKey(@Param("key") String key, @Param("number") String number);
	/**
	 * 根据个人标签查找符合的政策
	 */
	List<SortDataVO> queryThemeForTwo(Query query,@Param("themeId")Object themeId,@Param("level") Object level,@Param("industryList") Object industryList,
			@Param("region") Object region,@Param("target") Object target,@Param("deparment") Object deparment,@Param("scale") Object scale,@Param("beforeMonth") Object beforeMonth,
			@Param("currentMonth") Object currentMonth,@Param("mark") Object mark);
	
	/**
	 * 模糊查询
	 */
	List<SortDataVO>  selectAllVague(Query query,@Param("time") Object time,@Param("views") Object views,@Param("title") Object title);
	/**
	 * 统计申报主题
	 */
	Integer selectCountDeclare(@Param("theme") String theme);
	
	/**
	 * 统计全局模糊条数
	 * @param title
	 * @return
	 */
	Integer selectVagueCount(@Param("title") String title);

	List<GovPolicyDeclare> selectSelfPage(Query<Object> query, @Param("title") Object title, @Param("processorId") Object processorId, @Param("examineStatus") Object examineStatus,@Param("level") Object level,
			 @Param("status") Object status, @Param("special") Object special,
			 @Param("startTime") Object startTime, @Param("endTime") Object endTime,
			 @Param("views") Object views, @Param("username") Object username,
			 @Param("target") Object target, @Param("mode") Object mode,
			 @Param("formality") Object formality, @Param("support") Object support,
			 @Param("theme") Object theme, @Param("fund") Object fund,
			 @Param("scale") Object scale, @Param("industry") Object industry,
			 @Param("style") Object style,@Param("time") Object time,@Param("regionArr") Object regionArr,
			 @Param("prop") Object prop,@Param("order") Object order);
	
	List<GovPolicyDeclare> selectExaminePage(Query<Object> query, @Param("title") Object title, @Param("processorName") Object processorId, @Param("examineStatus") Object examineStatus,@Param("level") Object level,
			 @Param("status") Object status, @Param("special") Object special,
			 @Param("startTime") Object startTime, @Param("endTime") Object endTime,
			 @Param("views") Object views, @Param("username") Object username,
			 @Param("target") Object target, @Param("mode") Object mode,
			 @Param("formality") Object formality, @Param("support") Object support,
			 @Param("theme") Object theme, @Param("fund") Object fund,
			 @Param("scale") Object scale, @Param("industry") Object industry,
			 @Param("style") Object style,@Param("time") Object time,@Param("regionArr") Object regionArr,
			 @Param("prop") Object prop,@Param("order") Object order);

	Integer updateForGiveUpProcess(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	Integer updateForGiveUpProcessToDeclare(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	Boolean upToAccept(@Param("id") Object id);

	void viewUp(@Param("id") Object id);

	List selectDeclareVoPageForConsole(Query query, @Param("title") Object title, @Param("level") Object level,
			 @Param("status") Object status, @Param("special") Object special,
			 @Param("startTime") Object startTime, @Param("endTime") Object endTime,
			 @Param("views") Object views, @Param("username") Object username,
			 @Param("target") Object target, @Param("mode") Object mode,
			 @Param("formality") Object formality, @Param("support") Object support,
			 @Param("theme") Object theme, @Param("fund") Object fund,
			 @Param("scale") Object scale, @Param("industry") Object industry,
			 @Param("style") Object style,@Param("time") Object time,@Param("regionArr") Object regionArr,@Param("prop") Object prop,@Param("order") Object order);

	/**
	 * 根据发文部门（机构）查找对应出的政策
	 */
	List<Integer> selectOrganizationById(@Param("deparment") String deparment);
	/**
	 * 根据用户订阅的标签查找对应的政策id
	 */
	List<Integer> selectRelationId(@Param("tagStatus") String tagStatus);
	/**
	 * 根据用户订阅的标签查找对应的政策id
	 */
	List<SortDataVO> selectPolicyById(Query query,@Param("policyId") List<Integer> policyId,@Param("beforeMonth") Object beforeMonth,
			@Param("currentMonth") Object currentMonth,@Param("mark") Object mark);
	/**
	 * 重写优化主题查询语句（暂订阅用）
	 */
	String selectThemeByKey(@Param("key") String key);
	/**
	  * 申报政策比对
	 */
	List<ComparisonVO> selectComparisonByDeclare(@Param("ids") List<Integer> ids);

	/**
	 * 查询根据标签匹配
	 * @param ids
	 * @param tags
	 * @return
	 */
	List<DeclareVO> selectMatching(@Param("ids") Object ids,@Param("tags")List<Integer> tags);

	/**
	 * 根据政策依据 匹配申报政策
	 * @param policyList
	 * @return
	 */
	List<DeclareVO> selctRelation(@Param("policyList") List<Integer> policyList,@Param("id") Integer id);

	/**
	 * 小程序端调用
	 * @param query
	 * @param title
	 * @param level
	 * @param status
	 * @param special
	 * @param startTime
	 * @param endTime
	 * @param views
	 * @param username
	 * @param target
	 * @param mode
	 * @param formality
	 * @param support
	 * @param theme
	 * @param fund
	 * @param scale
	 * @param industry
	 * @param style
	 * @param time
	 * @param regionArr
	 * @return
	 */
	List<DeclareVO> selectDeclareVoPageWx(Query query, @Param("title") Object title, @Param("level") Object level,
						  @Param("status") Object status, @Param("special") Object special,
						  @Param("startTime") Object startTime, @Param("endTime") Object endTime,
						  @Param("username") Object username,
						  @Param("target") Object target, @Param("mode") Object mode,
						  @Param("formality") Object formality, @Param("support") Object support,
						  @Param("theme") Object theme, @Param("fund") Object fund,
						  @Param("scale") Object scale, @Param("industry") Object industry,
						  @Param("style") Object style,@Param("regionArr") Object regionArr);

	List<SortDataVO>  selectNewGlobalSearch(Query query,@Param("sort") Object sort,@Param("title") Object title);

	DeclareVO selectDeclareVOByIdForConsole(Integer id);
	/**
	 * 查询通用政策正文
	 */
	public String selectDeclareText(@Param("id") Integer id);
	/**
	  *  选择标签
	 * @param tagApiId
	 * @return
	 */
	List<CommonVO> selectNewDeclareTag(@Param("tagApiId")List<Integer> tagApiId);
	/**
	  *  选择发文部门
	 * @param dispatchId
	 * @return
	 */
	List<CommonVO> selectNewDeclareDispatch(@Param("dispatchId")List<Integer> dispatchId);
}
