package com.cloud.dips.user.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysUser;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @author BigPan
 * @date 2018-12-05 16:47:56
 */
@Mapper
public interface WebUsersMapper extends BaseMapper<SysUser> {

}
