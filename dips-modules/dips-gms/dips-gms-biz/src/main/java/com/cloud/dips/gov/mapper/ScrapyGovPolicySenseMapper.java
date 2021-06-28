package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicySense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 政策常识采集
 *
 * @author yy
 * @date 2018-10-22 11:55:59
 */
@Mapper
public interface ScrapyGovPolicySenseMapper extends BaseMapper<ScrapyGovPolicySense> {

	List selectScrapyPolicySenseList(Query query, @Param("title") Object title, @Param("username") Object username,
			@Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	List getRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);
}
