package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申报政策模型
 *
 * @author BigPan
 * @date 2018-10-24 11:25:41
 */
@Mapper
public interface ScrapyGovPolicyDeclareMapper extends BaseMapper<ScrapyGovPolicyDeclare> {

	/**
	 * 获取字典value
	 *
	 * @param number
	 * @param value
	 * @return value
	 */
	String selectDictKeyByNumberAndValue(@Param("number") Object number, @Param("value") Object value);

	@SuppressWarnings("rawtypes")
	List selectScrapyDeclarePage(Query query, @Param("title") Object title, @Param("username") Object username,
								 @Param("startTime") Object startTime, @Param("endTime") Object endTime,
								 @Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	List<String> listDictByNumber(@Param("number") String number);

	List getRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);
}
