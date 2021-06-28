package com.cloud.gov.theme.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.theme.api.entity.WebUserFootprint;

/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
@Mapper
public interface WebUserFootprintMapper extends BaseMapper<WebUserFootprint> {

}
