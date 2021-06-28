package com.cloud.gov.theme.mapper;

import com.cloud.dips.theme.api.entity.WebUserCollection;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户收藏表
 *
 * @author BigPan
 * @date 2018-12-11 10:56:16
 */
@Mapper
public interface WebUserCollectionMapper extends BaseMapper<WebUserCollection> {

	String queryCollectionTitle(@Param("collectionId") Integer collectionId,@Param("mark") String mark);
}
