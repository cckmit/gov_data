package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:04:59
 */
@Mapper
public interface GovOrganizationMapper extends BaseMapper<GovOrganization> {
	/**
	 * 查询（分页查询、条件查询）
	 */
	List selectOrPage(Query query, Map<String, Object> map);

	/**
	 * 根据标签ID查询
	 *
	 * @author BlackR
	 */
	List<GovOrganization> selectByTagId(Query query, @Param("tagId") Integer tagId);

	/**
	 * 回收站机构列表
	 */
	List<Object> selectRecyclePage(Query query, Map<String, Object> map);

	List<GovOrganization> repeat(String title);
}
