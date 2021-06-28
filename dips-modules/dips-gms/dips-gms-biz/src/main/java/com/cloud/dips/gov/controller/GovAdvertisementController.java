package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.entity.GovAdvertisement;
import com.cloud.dips.gov.service.GovAdvertisementService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 广告管理
 *
 * @author BlackR
 * @date 2018-10-22 11:55:59
 */
@RestController
@RequestMapping("/advertisement")
@AllArgsConstructor
public class GovAdvertisementController {

	private final GovAdvertisementService govAdvertisementService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询广告", notes = "分页查询广告", httpMethod = "GET")
	public Page findAdvertisementOrSort(@RequestParam Map<String, Object> params) {
		params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
		String title = "";
		if (StrUtil.isNotBlank((String) params.get("title"))) {
			title = ((String) params.get("title")).trim();
		}
		params.remove("title");
		Collection<String> columns=new ArrayList<String>();
		columns.add("id");
		if (StrUtil.isNotBlank((String) params.get("priority"))) {
			return govAdvertisementService.selectPage(new Query<>(params),
				new EntityWrapper<GovAdvertisement>().groupBy("sort").orderDesc(columns));
		} else {
			return govAdvertisementService.selectPage(new Query<>(params),
				new EntityWrapper<GovAdvertisement>().and().like("title", title).orderDesc(columns));
		}
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询广告", notes = "根据id查询广告", httpMethod = "GET")
	public R info(@PathVariable("id") Integer id) {
		GovAdvertisement govAdvertisement = govAdvertisementService.selectById(id);
		return new R<>(govAdvertisement);
	}

	/**
	 * 保存
	 *
	 * @param govAdvertisement
	 * @return R
	 */
	@SysLog("新增广告")
	@PostMapping("/create")
	@ApiOperation(value = "新增广告", notes = "新增广告", httpMethod = "POST")
	public R save(@RequestBody GovAdvertisement govAdvertisement) {
		govAdvertisement.setCreateTime(new Date());
		return new R<>(govAdvertisementService.insert(govAdvertisement));
	}

	/**
	 * 修改
	 *
	 * @param govAdvertisement
	 * @return R
	 */
	@SysLog("更新广告")
	@PostMapping("/update")
	@ApiOperation(value = "更新广告", notes = "更新广告", httpMethod = "POST")
	public R<Boolean> update(@RequestBody GovAdvertisement govAdvertisement) {
		return new R<>(govAdvertisementService.insertOrUpdate(govAdvertisement));
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除广告")
	@DeleteMapping
	@ApiOperation(value = "逻辑删除广告", notes = "逻辑删除广告", httpMethod = "POST")
	public R delete(@RequestBody Integer[] id) {
		GovAdvertisement govAdvertisement = new GovAdvertisement();
		govAdvertisement.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(govAdvertisementService.update(govAdvertisement,
			new EntityWrapper<GovAdvertisement>().in("id", id)));
	}

	/**
	 * 回收站广告列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站广告", notes = "分页查询回收站广告", httpMethod = "GET")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_DEL);
		String title = "";
		if (StrUtil.isNotBlank((String) params.get("title"))) {
			title = ((String) params.get("title")).trim();
		}
		return govAdvertisementService.selectPage(new Query<>(params),
			new EntityWrapper<GovAdvertisement>().and().like("title", title));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询回收站广告", notes = "根据id查询回收站广告", httpMethod = "GET")
	public R recycleInfo(@PathVariable("id") Integer id) {
		GovAdvertisement govAdvertisement = govAdvertisementService.selectById(id);
		return new R<>(govAdvertisement);
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除广告")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除广告", notes = "彻底删除广告", httpMethod = "DELETE")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govAdvertisementService.deleteById(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复广告")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复广告", notes = "恢复广告", httpMethod = "POST")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovAdvertisement govAdvertisement = govAdvertisementService.selectById(id);
		govAdvertisement.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govAdvertisementService.updateById(govAdvertisement));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除广告")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除广告", notes = "批量彻底删除广告", httpMethod = "DELETE")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		return new R<>(govAdvertisementService.deleteBatchIds(ids));
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复广告")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复广告", notes = "批量恢复广告", httpMethod = "POST")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovAdvertisement govAdvertisement = govAdvertisementService.selectById(id);
			govAdvertisement.setDelFlag(CommonConstant.STATUS_NORMAL);
			govAdvertisementService.updateById(govAdvertisement);
		}
		return new R<>(Boolean.TRUE);
	}
}
