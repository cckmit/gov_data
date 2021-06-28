package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovInformation;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.InformationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 政策资讯模型Mappper 接口
 *
 * @author CUI.CAN
 * @date 2018-09-11 10:27:23
 */
@Mapper
public interface GovInformationMapper extends BaseMapper<GovInformation> {

	/**
	 * 查询表中资讯列表
	 * @param order 
	 * @param prop 
	 *
	 * @param query, params
	 * @return
	 */
	List<InformationVO> selectInformationVOList(Query query, @Param("title") Object title, @Param("source") Object source,
												@Param("startTime") Object startTime, @Param("endTime") Object endTime,
												@Param("priority") Object priority, @Param("username") Object username,
												@Param("sort") Object sort,@Param("regionArr") Object regionArr,
												@Param("prop") Object prop,@Param("order") Object order);
   /**
	 * 微信小程序查询表中资讯列表
	 *
	 * @param query, params
	 * @return
	 */
	List<InformationVO> selectInformationWechat(Query query, @Param("title") Object title, @Param("source") Object source,
												@Param("startTime") Object startTime, @Param("endTime") Object endTime,
												@Param("priority") Object priority, @Param("views") Object views, @Param("username") Object username,
												@Param("time") Object time,@Param("regionArr") Object regionArr);

	/**
	 * 根据id查询资讯信息
	 *
	 * @param id
	 * @return
	 */
	InformationVO selectInformationById(Integer id);

	/**
	 * 根据标签id查询列表
	 *
	 * @param query
	 * @param tagId
	 * @return
	 */
	List<InformationVO> selectInformationVOPageByTagId(Query query, @Param("tagId") Object tagId);

	/**
	 * 回收站列表
	 *
	 * @param query
	 * @return
	 */
	List<Object> selectRecyclePage(Query<Object> query, @Param("title") Object title, @Param("username") Object username);

	/**
	 * 回收站查询单个
	 * @param id
	 * @return
	 */
	InformationVO selectRecycleById(Integer id);

	/**
	 * 查重
	 * @param title
	 * @return
	 * */
	List<GovInformation> repeat(String title);
	
	
	List<InformationVO> selectVagueInformation(@Param("time") Object time,@Param("views") Object views,@Param("title") Object title,
			@Param("page") Integer page,@Param("size") Integer size);
	
	/**
	 * 统计全局模糊条数
	 * @param title
	 * @return
	 */
	Integer selectVagueCount(@Param("title") String title);

	List<InformationVO> selectSelfPage(Query<Object> query, @Param("title") Object title, @Param("processorId") Object processorId,
												@Param("examineStatus") Object examineStatus,  @Param("source") Object source,
												@Param("startTime") Object startTime, @Param("endTime") Object endTime,
												@Param("priority") Object priority, @Param("views") Object views,
												@Param("time") Object time,@Param("regionArr") Object regionArr,
												@Param("prop") Object prop,@Param("order") Object order
												);
	
	List<InformationVO> selectExaminePage(Query<Object> query, @Param("title") Object title, @Param("processorName") Object processorName, 
												@Param("examineStatus") Object examineStatus,  @Param("source") Object source,
												@Param("startTime") Object startTime, @Param("endTime") Object endTime,
												@Param("priority") Object priority, @Param("views") Object views,
												@Param("time") Object time,@Param("regionArr") Object regionArr,
												@Param("prop") Object prop,@Param("order") Object order);

	Integer updateForGiveUpProcess(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	Integer updateForGiveUpProcessToInformation(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);
	Boolean viewUp(@Param("id") Object id);
	/**
	 * 查询表中资讯列表
	 *
	 * @param query, params
	 * @return
	 */
	List<InformationVO> selectInformationVOListForConsole(Query query, @Param("title") Object title, @Param("source") Object source,
												@Param("startTime") Object startTime, @Param("endTime") Object endTime,
												@Param("priority") Object priority,  @Param("username") Object username,
												@Param("regionArr") Object regionArr,@Param("prop") Object prop,@Param("order") Object order,
												@Param("sort") Object sort);


	/**
	 * 查询根据标签匹配
	 * @param ids
	 * @param tags
	 * @return
	 */
	List<InformationVO> selectMatching(@Param("ids") Object ids, @Param("tags")List<Integer> tags,@Param("id") Long id);

	/**
	 * 根据资讯标签匹配 通用、申报 政策
	 * @param ids
	 * @param tags
	 * @return
	 */
	List<InformationVO> selectCommonMatching(@Param("ids") Object ids, @Param("tags")List<Integer> tags);
	
	/**
	 * 根据id查询资讯信息
	 *
	 * @param id
	 * @return
	 */
	InformationVO selectInformationByIdForConsole(Integer id);
	
	Integer checkTag(@Param("id") Integer id);
	
	/**
	 * 查询通用政策正文
	 */
	public String selectInformationText(@Param("id") Integer id);
	/**
	  *  选择标签
	 * @param tagApiId
	 * @return
	 */
	List<CommonVO> selectNewInformationTag(@Param("tagApiId")List<Integer> tagApiId);


}
