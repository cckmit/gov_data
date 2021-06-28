package com.cloud.dips.gov.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.SortDataVO;

/**
 * 政策解读
 *
 * @author C.Z.H
 * @date 2018-09-11 11:27:05
 */
@Mapper
public interface GovPolicyExplainMapper extends BaseMapper<GovPolicyExplain> {

    List<ExplainVO> explainPageVO(Query query, @Param("title") Object title,
                                  @Param("main") Object main, @Param("priority") Object priority,
                                  @Param("sort") Object sort,
                                  @Param("startTime") Object startTime, @Param("endTime") Object endTime,
                                  @Param("level") Object level, @Param("username") Object username,
                                  @Param("theme") Object theme, @Param("industry") Object industry, @Param("regionArr") Object regionArr);

    /**
     * 小程序政策解读分页
     *
     * @param query
     * @param title
     * @param main
     * @param priority
     * @param views
     * @param time
     * @param startTime
     * @param endTime
     * @param level
     * @param username
     * @param theme
     * @param industry
     * @param regionArr
     * @return
     */
    List<ExplainVO> selectWechatPage(Query query, @Param("title") Object title,@Param("main") Object main,@Param("priority") Object priority,
                                     @Param("startTime") Object startTime, @Param("endTime") Object endTime,
                                     @Param("level") Object level, @Param("username") Object username,
                                     @Param("theme") Object theme, @Param("industry") Object industry, @Param("regionArr") Object regionArr);

    Boolean updateDelFlag(@Param("id") int id);

    List<ExplainVO> selectExplainVoPageByTagId(Query query, @Param("tagId") Object tagId);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ExplainVO selectAllVo(Integer id);

    /**
     * 浏览次数加1
     *
     * @param id
     */
    void updateViews(Integer id);

    /**
     * 回收站政策解读列表
     */
    List selectRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);

    /**
     * 查重
     */
    List<GovPolicyExplain> repeat(String title);

    /**
     * 根据个人标签查找符合的政策
     */
    List<SortDataVO> queryIncludTheme(@Param("sortingDataVo") SortingDataDTO sortingDataDTO, @Param("industryList") String industryList,
                                      @Param("theme") String theme, @Param("beforeMonth") String beforeMonth, @Param("currentMonth") String currentMonth);

    List<ExplainVO> selectVagueExplain(@Param("time") Object time, @Param("views") Object views, @Param("title") Object title,
                                       @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 统计解读主题
     */
    Integer selectCountExplain(@Param("theme") String theme);


	List<ExplainVO> explainPageVOForCpnserl(Query query, @Param("title") Object title,
			  @Param("main") Object main, @Param("priority") Object priority,
			  @Param("views") Object views, @Param("time") Object time,
			  @Param("startTime") Object startTime, @Param("endTime") Object endTime,
			  @Param("level") Object level, @Param("username") Object username,
			  @Param("theme") Object theme, @Param("industry") Object industry,@Param("regionArr") Object regionArr);

	List<ExplainVO> explainPageVOForConsole(Query query, @Param("title") Object title,
								  @Param("main") Object main, @Param("priority") Object priority,
								  @Param("views") Object views, @Param("time") Object time,
								  @Param("startTime") Object startTime, @Param("endTime") Object endTime,
								  @Param("level") Object level, @Param("username") Object username,
								  @Param("theme") Object theme, @Param("industry") Object industry,@Param("regionArr") Object regionArr,@Param("prop") Object prop,@Param("order") Object order);


    /**
     * 统计全局模糊条数
     *
     * @param title
     * @return
     */
    Integer selectVagueCount(@Param("title") String title);

    List<ExplainVO> selectSelfPage(Query<Object> query, @Param("title") Object title, @Param("processorId") Object processorId,
                                   @Param("examineStatus") Object examineStatus, @Param("main") Object main, @Param("priority") Object priority,
                                   @Param("views") Object views, @Param("time") Object time,
                                   @Param("startTime") Object startTime, @Param("endTime") Object endTime,
                                   @Param("level") Object level, @Param("username") Object username,
                                   @Param("theme") Object theme, @Param("industry") Object industry, @Param("regionArr") Object regionArr,
                      			   @Param("prop") Object prop,@Param("order") Object order);

    List<ExplainVO> selectExaminePage(Query<Object> query, @Param("title") Object title, @Param("processorName") Object processorName,
                                      @Param("examineStatus") Object examineStatus, @Param("main") Object main, @Param("priority") Object priority,
                                      @Param("views") Object views, @Param("time") Object time,
                                      @Param("startTime") Object startTime, @Param("endTime") Object endTime,
                                      @Param("level") Object level, @Param("username") Object username,
                                      @Param("theme") Object theme, @Param("industry") Object industry, @Param("regionArr") Object regionArr,
                         			  @Param("prop") Object prop,@Param("order") Object order);

    Integer updateForGiveUpProcess(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

    Integer updateForGiveUpProcessToExplain(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);


    /**
     * 查询相关政策解读  通用政策
     *
     * @return
     */
    List<ExplainVO> selectgenerlist(@Param("id") Object id);


    /**
     * 相关解读 根据政策原文匹配  申报、通用政策
     *
     * @param policyList
     * @return
     */
    List<ExplainVO> findPolicyText(@Param("policyList") List<Integer> policyList);

    /**
     * 其它解读 根据政策原文 匹配政策解读政策
     *
     * @param policyList
     * @return
     */
    List<ExplainVO> findInterPreTaboo(@Param("policyList") List<Integer> policyList, @Param("ids") Integer ids);

	ExplainVO selectAllVoForConsole(Integer id);

	Integer checkTag(@Param("id") Integer id);
	/**
	 * 查询通用政策正文
	 */
	public String selectExplainText(@Param("id") Integer id);
	/**
	  *  选择标签
	 * @param tagApiId
	 * @return
	 */
	List<CommonVO> selectNewExplainTag(@Param("tagApiId")List<Integer> tagApiId);
	/**
	  *  选择发文部门
	 * @param dispatchId
	 * @return
	 */
	List<CommonVO> selectNewExplainDispatch(@Param("dispatchId")List<Integer> dispatchId);

}
