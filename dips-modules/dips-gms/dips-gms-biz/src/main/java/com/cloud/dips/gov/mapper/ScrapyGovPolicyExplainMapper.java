package com.cloud.dips.gov.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyExplain;
/**
 * 
 * @author C.Z.H
 * 2019年1月3日
 *ScrapyGovPolicyExplainMapper.java
 */
@Mapper
public interface ScrapyGovPolicyExplainMapper extends BaseMapper<ScrapyGovPolicyExplain> {

	/**
	 * 获取字典value
	 *
	 * @param number
	 * @param value
	 * @return value
	 */
	String selectDictKeyByNumberAndValue(@Param("number") Object number, @Param("value") Object value);

	List selectScrapyExplainPage(Query query, @Param("title") Object title, @Param("username") Object username,
			 @Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	List<String> listDictByNumber(@Param("number") String number);

	List getRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);

	void recycleRecover(@Param("id") Integer id);

}
