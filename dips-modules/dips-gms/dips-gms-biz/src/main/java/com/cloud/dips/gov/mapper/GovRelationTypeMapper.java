package com.cloud.dips.gov.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovRelationType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联类型Mapper接口
 *
 * @author C.Z.H
 */
public interface GovRelationTypeMapper extends BaseMapper<GovRelationType> {

	/**
	 * 根据number查询关联type
	 */
	GovRelationType selectByNumber(String number);

	/**
	 * 关联分页
	 */
	List<GovRelationType> selectAllPage(Query query, @Param("name") Object name, @Param("number") Object number);
}
