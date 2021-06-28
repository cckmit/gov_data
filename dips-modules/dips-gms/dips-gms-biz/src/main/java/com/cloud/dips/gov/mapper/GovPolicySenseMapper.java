package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.vo.SenseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 政策常识模型持久层
 *
 * @author Z.Y.S
 * @date 2018-09-11 10:14:26
 */
@Mapper
public interface GovPolicySenseMapper extends BaseMapper<GovPolicySense> {

	/**
	 * 常识分页
	 */
	List<SenseVO> selectPolicySenseList(Query query, @Param("title") Object title, @Param("username") Object username,
			@Param("prop") Object prop,@Param("order") Object order);

	/**
	 * 垃圾分页
	 */
	List selectRecyclePage(Query query, @Param("title") Object title, @Param("username") Object username);

	/**
	 * 查重
	 */
	List<GovPolicySense> repeat(String title);

	List<GovPolicySense> selectSelfPage(Query<Object> query, @Param("title") Object title, @Param("processorId") Object processorId, @Param("examineStatus") Object examineStatus);

	List<GovPolicySense> selectExaminePage(Query<Object> query, @Param("title") Object title, @Param("processorName") Object processorName, @Param("examineStatus") Object examineStatus);

	Integer updateForGiveUpProcess(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	Integer updateForGiveUpProcessToSense(@Param("scrapyId") Object scrapyId, @Param("processorId") Object processorId);

	List<SenseVO> selectPolicySenseListForConsole(Query query, @Param("title") Object title, @Param("username") Object username);

}
