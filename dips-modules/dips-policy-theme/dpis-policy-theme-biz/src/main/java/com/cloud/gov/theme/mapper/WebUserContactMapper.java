package com.cloud.gov.theme.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.vo.WebusercontactVo;

/**
 * 
 *
 * @author BigPan
 * @date 2018-12-07 10:33:33
 */
@Mapper
public interface WebUserContactMapper extends BaseMapper<WebUserContact> {

	List<WebusercontactVo> queryThemeByUser();
}
