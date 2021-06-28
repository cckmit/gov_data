package com.cloud.dips.raus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.entity.UserApplication;
import com.cloud.dips.raus.api.vo.UserApplicationVO;

/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@Mapper
public interface UserApplicationMapper extends BaseMapper<UserApplication> {

	public String selectMaxOrderId();
	
	List<UserApplicationVO> selectApplicationPage(Query query, @Param("connectName") Object connectName, 
			@Param("createTime") Object createTime,@Param("handleResult") Object handleResult,@Param("type") Object type,@Param("userId") Object userId); 
}
