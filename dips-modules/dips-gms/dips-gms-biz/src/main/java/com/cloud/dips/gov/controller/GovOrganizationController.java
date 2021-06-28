package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.dto.OrganizationDTO;
import com.cloud.dips.gov.api.entity.GovOrganization;
import com.cloud.dips.gov.service.GovOrganizationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:04:59
 */
@RestController
@RequestMapping("/organization")
@AllArgsConstructor
public class GovOrganizationController {

	private final GovOrganizationService govOrganizationService;

	/**
	 * 机构查询（模糊查询（机构名字）、机构层级）
	 *
	 * @param params
	 * @return Page
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询机构信息", notes = "分页查询机构信息", httpMethod = "GET")
	public Page info(@RequestParam Map<String, Object> params) {
		return govOrganizationService.selectOrLike(new Query<>(params));
	}

	/**
	 * 根据标签ID查询
	 *
	 * @param tagId
	 * @return
	 */
	@GetMapping("/tag/{tagId}")
	@ApiOperation(value = "根据标签查询机构信息", notes = "根据标签查询机构信息", httpMethod = "GET")
	public Page pageTag(@PathVariable("tagId") Integer tagId,@RequestParam Map<String, Object> params) {
		params.put("tagId", tagId);
		return govOrganizationService.selectPageByTagId(new Query<>(params));
	}

	/**
	 * 新增
	 *
	 * @param organizationDTO
	 * @return
	 */
	@PostMapping("/create")
	@SysLog("国策库机构新增")
	@ApiOperation(value = "新增机构", notes = "新增机构", httpMethod = "POST")
	public R save(@RequestBody OrganizationDTO organizationDTO) {
		return govOrganizationService.save(organizationDTO);
	}

	/**
	 * 逻辑删除（批量删除）
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/delete")
	@SysLog("国策库机构逻辑删除")
	@ApiOperation(value = "逻辑删除机构", notes = "逻辑删除机构", httpMethod = "DELETE")
	public R delete(@RequestBody Integer[] ids) {
		return new R<>(govOrganizationService.deleteOrMore(ids));
	}

	/**
	 * 修改
	 *
	 * @param organizationDTO
	 * @return
	 */
	@PostMapping("/update")
	@SysLog("国策库机构修改")
	@ApiOperation(value = "更新机构", notes = "更新机构", httpMethod = "POST")
	public R update(@RequestBody OrganizationDTO organizationDTO) {
		return new R<>(govOrganizationService.update(organizationDTO));
	}

	/**
	 * 回收站机构列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站机构信息", notes = "分页查询回收站机构信息", httpMethod = "GET")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govOrganizationService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("国策库机构彻底删除")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除机构", notes = "彻底删除机构", httpMethod = "DELETE")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govOrganizationService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("国策库机构恢复")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复机构", notes = "恢复机构", httpMethod = "POST")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovOrganization govOrganization = govOrganizationService.selectById(id);
		govOrganization.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govOrganizationService.updateById(govOrganization));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("国策库机构批量彻底删除")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除机构", notes = "批量彻底删除机构", httpMethod = "DELETE")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govOrganizationService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("国策库机构批量恢复")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复机构", notes = "批量恢复机构", httpMethod = "POST")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovOrganization govOrganization = govOrganizationService.selectById(id);
			govOrganization.setDelFlag(CommonConstant.STATUS_NORMAL);
			govOrganizationService.updateById(govOrganization);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "机构名查重", notes = "机构名查重", httpMethod = "POST")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govOrganizationService.repeat(title));
	}
}
