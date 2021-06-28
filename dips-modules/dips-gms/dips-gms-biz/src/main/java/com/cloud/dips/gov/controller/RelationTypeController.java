/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.entity.GovRelationType;
import com.cloud.dips.gov.service.GovRelationTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 部门关联类型管理 前端控制器
 *
 * @author C.Z.H
 */
@RestController
@RequestMapping("/relation/type")
public class RelationTypeController {
	@Autowired
	private GovRelationTypeService service;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysRelationType
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询关联类型", notes = "根据ID查询关联类型: params{ID: id}", httpMethod = "GET")
	public GovRelationType get(@PathVariable Integer id) {
		return service.selectById(id);
	}

	/**
	 * 分页查询关联分类
	 *
	 * @param params 参数集
	 * @return 通知公告集合
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询关联类型", notes = "关联类型分页集合", httpMethod = "GET")
	public Page<GovRelationType> page(@RequestParam Map<String, Object> params) {
		return service.selectAllPage(new Query<>(params));
	}


	/**
	 * 添加
	 *
	 * @param type 实体
	 * @return success/false
	 */
	@SysLog("添加关联类型")
	@PostMapping("/create")
	@ApiOperation(value = "添加关联类型", notes = "添加关联类型", httpMethod = "POST")
	public R<Boolean> add(@RequestBody GovRelationType type) {
		return new R<>(service.insert(type));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除关联类型")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除关联类型", notes = "根据ID删除关联类型: params{类型ID: id}", httpMethod = "DELETE")
	public R<Boolean> delete(@PathVariable Integer id) {
		return new R<>(service.deleteById(id));
	}

	/**
	 * 更新
	 *
	 * @param type 实体
	 * @return success/false
	 */
	@SysLog("更新关联类型")
	@PostMapping("/update")
	@ApiOperation(value = "更新关联类型", notes = "更新关联类型", httpMethod = "PUT")
	public Boolean edit(@RequestBody GovRelationType type) {
		return service.updateById(type);
	}
}
