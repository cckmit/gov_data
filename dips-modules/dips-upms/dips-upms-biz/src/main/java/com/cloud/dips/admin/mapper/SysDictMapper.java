package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.vo.DictVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
	/**
	 * 查询所有字典
	 *
	 * @return
	 */
	List<DictVO> selectAllDict();

}
