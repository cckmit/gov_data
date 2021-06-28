package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysLog;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

	/**
	 * 查询web端用户行为
	 * @return
	 */
	List<SysLog> findByList();

}
