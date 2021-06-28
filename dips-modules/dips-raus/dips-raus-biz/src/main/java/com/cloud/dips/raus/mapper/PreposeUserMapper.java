package com.cloud.dips.raus.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.raus.api.dto.PreposeUserDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;

/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@Mapper
public interface PreposeUserMapper extends BaseMapper<PreposeUser> {

	/**
	 * 查找是否存在token
	 */
	public Integer findHasToken(@Param("tokenName")String tokenName);
	/**
	 * 用户增加对应的信息(token,失效时间等)
	 */
	public void insertToken(PreposeUser preposeUser);
	/**
	 * 根据用户名查找主题限制个数和字段个数
	 */
	public PreposeUserDTO selectThemeCount(@Param("userName")String userName);
}
