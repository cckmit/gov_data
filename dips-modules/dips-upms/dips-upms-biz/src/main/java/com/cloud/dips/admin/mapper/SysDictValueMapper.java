/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典值表 Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface SysDictValueMapper extends BaseMapper<SysDictValue> {
	/**
	 * 通过字典id查询字典值列表
	 *
	 * @param dId 字典id
	 * @return
	 */
	List<DictValueVO> selectDictValueVo(@Param("dId") Integer dId);
	
	/**
	 * 根据主题id查找对应的主题
	 * @param themeId
	 * @return
	 */
	List<DictValueVO> selectThemeById(@Param("themeId")Integer themeId);
}
