package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构模型
 *
 * @author BlackR
 * @date 2018-10-24 14:50:37
 */
@Mapper
public interface ScrapyGovOrganizationMapper extends BaseMapper<ScrapyGovOrganization> {

	String selectDictKeyByNumberAndValue(@Param("number") Object number, @Param("value") Object value);

	List<Object> selectOrganizationPage(Query<Object> query, @Param("title") Object title,@Param("status") Object status,
			@Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	List<String> listDictByNumber(@Param("number") String number);

	List<Object> getRecyclePage(Query<Object> query, Map<String, Object> map);

	void recycleRecover(Integer id);
}
