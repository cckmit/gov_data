package com.cloud.dips.raus.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.raus.api.entity.GovWebLog;

import org.apache.ibatis.annotations.Mapper;

/**
 * 日志表
 *
 * @author BigPan
 * @date 2018-12-04 16:48:44
 */
@Mapper
public interface GovWebLogMapper extends BaseMapper<GovWebLog> {

}
