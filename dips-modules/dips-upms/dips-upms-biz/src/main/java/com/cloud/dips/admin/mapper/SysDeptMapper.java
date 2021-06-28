package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptCityVO;
import com.cloud.dips.admin.api.vo.DeptVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 *
	 * @param delFlag 删除标记
	 * @return 数据列表
	 */
	List<SysDept> selectDeptDtoList(String delFlag);

	/**
	 * 通过ID查询部门信息
	 *
	 * @param id 部门ID
	 * @return 部门信息
	 */
	DeptVO selectDeptVoById(@Param("id") Integer id);

	/**
	 * 机构 城市 集合
	 *
	 * @return R
	 */
	List<DeptCityVO> selectDeptList();
	
	/**
	 * 获取部门列表
	 *
	 * @return 部门列表
	 */
	List<SysDept> selectAllDeptList();
}
