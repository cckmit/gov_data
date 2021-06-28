package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 广告管理
 *
 * @author BlackR
 * @date 2018-10-22 11:55:59
 */
@Mapper
public interface ScrapyGovPolicyGeneralMapper extends BaseMapper<ScrapyGovPolicyGeneral> {

	String selectDictKeyByNumberAndValue(@Param("value") String value, @Param("number") String number);

	List<Object> selectScrapyGeneralPage(Query<Object> query, @Param("title") Object title, @Param("username") Object username,
										 @Param("startTime") Object startTime, @Param("endTime") Object endTime, @Param("status") Object status,
										 @Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	List<String> listDictByNumber(String number);

	List<Object> getRecyclePage(Query<Object> query, @Param("title") Object title, @Param("username") Object username);

	void recycleRecover(@Param("id") Integer id);

	List<Object> selectScrapyGeneralSelfPage(Query<Object> query, @Param("title") Object title, @Param("processorId") Object processorId, @Param("status") Object status);

}
