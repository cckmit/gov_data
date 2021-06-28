package com.cloud.gov.theme.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.entity.WebUserTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户d订阅个人标签
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
@Mapper
public interface WebUserTagMapper extends BaseMapper<WebUserTag> {

}
