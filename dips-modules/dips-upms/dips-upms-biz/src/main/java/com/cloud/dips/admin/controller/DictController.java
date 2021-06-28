/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictVO;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.admin.service.SysDictService;
import com.cloud.dips.admin.service.SysDictValueService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author Wilson
 * @since 2017-11-19
 */
@RestController
@RequestMapping("/dict")
public class DictController {
	@Autowired
	private SysDictService service;

	@Autowired
	private SysDictValueService valueService;


	private  CacheManager cacheManager;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息", httpMethod = "GET")
	@SysLog(value = "通过ID查询字典信息",role = EnumRole.WEB_TYE)
	public SysDict dict(@PathVariable Integer id) {
		return service.selectById(id);
	}

	@GetMapping("/list/{number}")
	@ApiOperation(value = "通过字典编码查询字典值信息", notes = "通过字典编码查询字典值信息", httpMethod = "GET")
	public List<DictValueVO> dictValueList(@PathVariable("number") String number) {
		EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
		e.eq("number", number);
		SysDict sysDict = service.selectOne(e);
		if (sysDict != null) {
			return valueService.selectDictValueVo(sysDict.getId());
		} else {
			return null;
		}
	}

	@GetMapping("/all_list")
	@ApiOperation(value = "查询所有字典", notes = "查询所有字典", httpMethod = "GET")
	public List<DictVO> allList() {
		return service.selectAllDict();
	}

	@GetMapping("/map")
	@SysLog(value = "通过字典编码集合查询字典值信息集合",role = EnumRole.WEB_TYE)
	@ApiOperation(value = "通过字典编码集合查询字典值信息集合", notes = "通过字典编码集合查询字典值信息集合", httpMethod = "GET")
	@Cacheable(value = "dict_values", key = "#numberList",unless="#result == null")
	public Map<String, List<DictValueVO>> getDictMap(@RequestParam("numberList[]") String[] numberList) {
		Map<String, List<DictValueVO>> dictMap = new HashMap<>(0);
		for (String number : numberList) {
			dictMap.put(number, dictValueList(number));
		}
		return dictMap;
	}

	/**
	 * 分页查询字典
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/dictPage")
	@ApiOperation(value = "分页查询字典", notes = "分页查询字典", httpMethod = "GET")
	public Page<SysDict> dictPage(@RequestParam Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<SysDict> p = new Page<SysDict>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
		e.eq("system", CommonConstant.SYSTEM_NAME);
		String name = params.getOrDefault("name", "").toString();
		if (StringUtils.isNotBlank(name)) {
			name = "%" + name + "%";
			e.where("name like {0}", name);
		}
		return service.selectPage(p, e);
	}

	@SysLog("删除字典")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除字典", notes = "根据ID删除字典: params{字典ID: id}", httpMethod = "DELETE")
	@CacheEvict(value = "dict_values", allEntries=true)
	public R<Boolean> dictDel(@PathVariable Integer id) {
		SysDict sysDict = service.selectById(id);
		if (sysDict == null) {
			return new R<>(false);
		} else {
			EntityWrapper<SysDictValue> e = new EntityWrapper<SysDictValue>();
			e.eq("dict_id", sysDict.getId());
			valueService.delete(e);
//			Objects.requireNonNull(cacheManager.getCache("dict_values")).clear();
			return new R<>(service.deleteById(sysDict.getId()));
		}
	}

	@SysLog("添加字典")
	@PostMapping
	@ApiOperation(value = "添加字典", notes = "添加字典", httpMethod = "POST")
	@CacheEvict(value = "dict_values", allEntries=true)
	public R<Boolean> addDict(@Valid @RequestBody SysDict sysDict) {
		EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
		e.eq("number", sysDict.getNumber());
		Integer i = service.selectCount(e);
		if (i > 0) {
			return new R<>(Boolean.FALSE);
		} else {
			SysDict bean = new SysDict();
			BeanUtils.copyProperties(sysDict, bean);
//			Objects.requireNonNull(cacheManager.getCache("dict_values")).clear();
			return new R<>(service.insert(bean));
		}
	}

	@SysLog("更新字典")
	@PutMapping
	@ApiOperation(value = "更新字典", notes = "更新字典", httpMethod = "PUT")
	@CacheEvict(value = "dict_values",allEntries=true)
	public R<Boolean> updateDict(@Valid @RequestBody SysDict sysDict) {
		SysDict bean = service.selectById(sysDict.getId());
		if (bean == null) {
			return new R<>(Boolean.FALSE);
		} else {
			bean.setUpdateTime(new Date());
			if (StringUtils.equals(sysDict.getNumber(), bean.getNumber())) {
				BeanUtils.copyProperties(sysDict, bean);
//				Objects.requireNonNull(cacheManager.getCache("dict_values")).clear();
				return new R<>(service.updateById(bean));
			} else {
				EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
				e.eq("number", sysDict.getNumber());
				Integer i = service.selectCount(e);
				if (i > 0) {
					return new R<>(Boolean.FALSE);
				} else {
					BeanUtils.copyProperties(sysDict, bean);
//					Objects.requireNonNull(cacheManager.getCache("dict_values")).clear();
					return new R<>(service.updateById(bean));
				}
			}
		}
	}

	/**
	  *  根据主题id找到对应的主题
	 * @param themeId
	 * @return
	 */
	@GetMapping("/listDictValue/themeId")
	public List<DictValueVO> getlistDictValue(@RequestParam("themeId") Integer themeId){
		
		return valueService.selectThemeById(themeId);
		
	}

}
