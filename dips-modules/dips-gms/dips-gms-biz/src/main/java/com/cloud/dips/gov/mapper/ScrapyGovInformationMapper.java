package com.cloud.dips.gov.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.ScrapyGovInformation;

/**
 * 政策资讯模型
 *
 * @author CUI.CAN
 * @date 2018-10-24 15:27:13
 */
public interface ScrapyGovInformationMapper extends BaseMapper<ScrapyGovInformation> {

	List scrapyGovInformationPage(Query query, @Param("title") Object title, @Param("username") Object username,
			 @Param("prop") Object prop,@Param("order") Object order,@Param("exceptExamined") Object exceptExamined);

	void recycleRecover(Integer id);

	List<Object> getRecyclePage(Query<Object> query, @Param("title") Object title, @Param("username") Object username);

}
