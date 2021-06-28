package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.gov.api.entity.GovRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联Mapper接口
 *
 * @author C.Z.H
 */
public interface GovRelationMapper extends BaseMapper<GovRelation> {

	/**
	 * 查单个关联
	 */
	GovRelation findOne(@Param("node") String node, @Param("relationId") Integer relationId, @Param("correlationId") Integer correlationId, @Param("number") String number);

	/**
	 * 查所有关联
	 */
	List<GovRelation> selectByNodeAndIdAndType(@Param("node") String node, @Param("relationId") Integer relationId, @Param("number") String number);

	/**
	 * 删除单个关联
	 */
	void deleteOne(@Param("node") String node, @Param("relationId") Integer relationId, @Param("correlationId") Integer correlationId, @Param("typeId") Integer typeId);

	/**
	 * 删除被关联
	 */
	void deleteBeRelation(@Param("node") String node, @Param("typeNumber") String typeNumber, @Param("correlationId") Integer correlationId);
}
