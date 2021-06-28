package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.entity.GovBlog;
import com.cloud.dips.gov.service.GovBlogService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 友情链接
 *
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */
@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class GovBlogController {
	private final GovBlogService govBlogService;

	@SysLog("新增友情链接")
	@PostMapping("/create")
	@ApiOperation(value = "新增友情链接", notes = "新增友情链接", httpMethod = "POST")
	public R<Boolean> save(@RequestBody GovBlog blog) {
		blog.setCreateTime(new Date());
		return new R<>(govBlogService.insert(blog));
	}

	@SysLog("逻辑删除友情链接")
	@DeleteMapping("/delete")
	@ApiOperation(value = "逻辑删除友情链接", notes = "逻辑删除友情链接", httpMethod = "DELETE")
	public R<Boolean> delete(@RequestBody Integer[] id) {
		GovBlog blog = new GovBlog();
		blog.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(govBlogService.update(blog, new EntityWrapper<GovBlog>().in("id", id)));
	}

	@SysLog("更新友情链接")
	@PostMapping("/update")
	@ApiOperation(value = "更新友情链接", notes = "更新友情链接", httpMethod = "POST")
	public R<Boolean> update(@RequestBody GovBlog blog) {
		return new R<>(govBlogService.updateById(blog));
	}

	@GetMapping("/page")
	@ApiOperation(value = "分页查询友情链接", notes = "分页查询友情链接", httpMethod = "GET")
	public Page findBlogOrSort(@RequestParam Map<String, Object> params) {
		params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
		String title = " ";
		if (StrUtil.isNotBlank((String) params.get("title"))) {
			title = ((String) params.get("title")).trim();
		}
		params.remove("title");
		Collection<String> columns=new ArrayList<String>();
		columns.add("id");
		if (StrUtil.isNotBlank((String) params.get("priority"))) {
			return govBlogService.selectPage(new Query<>(params),
				new EntityWrapper<GovBlog>().groupBy("sort").orderDesc(columns));
		} else {
			return govBlogService.selectPage(new Query<>(params),
				new EntityWrapper<GovBlog>().like("title", title).orderDesc(columns));
		}
	}

	/**
	 * 回收站广告列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站友情链接", notes = "分页查询回收站友情链接", httpMethod = "GET")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_DEL);
		String title = "";
		if (StrUtil.isNotBlank((String) params.get("title"))) {
			title = ((String) params.get("title")).trim();
		}
		return govBlogService.selectPage(new Query<>(params),
			new EntityWrapper<GovBlog>().and().like("title", title));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询回收站友情链接", notes = "根据id查询回收站友情链接", httpMethod = "GET")
	public R recycleInfo(@PathVariable("id") Integer id) {
		GovBlog govBlog = govBlogService.selectById(id);
		return new R<>(govBlog);
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除友情链接")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除友情链接", notes = "彻底删除友情链接", httpMethod = "DELETE")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govBlogService.deleteById(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复友情链接")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复友情链接", notes = "恢复友情链接", httpMethod = "POST")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovBlog govBlog = govBlogService.selectById(id);
		govBlog.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govBlogService.updateById(govBlog));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除友情链接")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除友情链接", notes = "批量彻底删除友情链接", httpMethod = "DELETE")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		return new R<>(govBlogService.deleteBatchIds(ids));
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复友情链接")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复友情链接", notes = "批量恢复友情链接", httpMethod = "POST")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovBlog govBlog = govBlogService.selectById(id);
			govBlog.setDelFlag(CommonConstant.STATUS_NORMAL);
			govBlogService.updateById(govBlog);
		}
		return new R<>(Boolean.TRUE);
	}
}
