package com.cloud.dips.gov.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.dto.SenseDTO;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.vo.SenseVO;
import com.cloud.dips.gov.service.GovPolicySenseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 政策常识模型
 *
 * @author Z.Y.S
 * @date 2018-09-11 10:14:26
 */
@RestController
@RequestMapping("/policy/sense")
@AllArgsConstructor
public class GovPolicySenseController {

	private final GovPolicySenseService govPolicySenseService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询政策常识模型", notes = "政策常识模型")
	public Page<SenseVO> page(@RequestParam Map<String, Object> params) {
		return govPolicySenseService.selectAllPolicySense(new Query<>(params));
	}
	
	/**
	 * 控制台列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/pageConsole")
	@ApiOperation(value = "控制台分页查询政策常识模型", notes = "控制台政策常识模型")
	public Page<SenseVO> pageConsole(@RequestParam Map<String, Object> params) {
		return govPolicySenseService.selectConsolePolicySense(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询政策常识模型", notes = "查询政策常识模型")
	public GovPolicySense info(@PathVariable("id") Integer id) {
		return govPolicySenseService.selectById(id);
	}

	/**
	 * 保存
	 *
	 * @param senseDTO
	 * @return R
	 */
	@PostMapping("/create")
	@SysLog("添加政策常识模型")
	@ApiOperation(value = "添加政策常识模型", notes = "添加政策常识模型")
	public R<Boolean> save(@RequestBody SenseDTO senseDTO) {
		return new R<>(govPolicySenseService.insertPolicySense(senseDTO));
	}

	/**
	 * 修改
	 *
	 * @param senseDTO
	 * @return R
	 */
	@PostMapping("/update")
	@SysLog("修改政策常识模型")
	@ApiOperation(value = "修改政策常识模型", notes = "修改政策常识模型")
	public R<Boolean> update(@RequestBody SenseDTO senseDTO) {
		return new R<>(govPolicySenseService.updatePolicySenseById(senseDTO));
	}

	/**
	 * 根据ID删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/delete/{id}")
	@SysLog("删除政策常识模型")
	@ApiOperation(value = "删除政策常识模型", notes = "删除政策常识模型")
	public R<Boolean> deleteById(@PathVariable("id") Integer id) {
		return new R<>(govPolicySenseService.deletePolicySense(id));
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return R
	 */
	@DeleteMapping("/deleteAll")
	@SysLog("批量删除政策常识模型")
	@ApiOperation(value = "批量删除政策常识模型", notes = "批量删除政策常识模型")
	public R<Boolean> deletes(@RequestBody Integer[] ids) {
		return new R<>(govPolicySenseService.deletePolicySenseAll(ids));
	}

	/**
	 * 回收站政策常识列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站常识")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govPolicySenseService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除常识")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govPolicySenseService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复常识")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复常识")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovPolicySense govPolicySense = govPolicySenseService.selectById(id);
		govPolicySense.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govPolicySenseService.updateById(govPolicySense));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除常识")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除常识")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govPolicySenseService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复常识")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复常识")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicySense govPolicySense = govPolicySenseService.selectById(id);
			govPolicySense.setDelFlag(CommonConstant.STATUS_NORMAL);
			govPolicySenseService.updateById(govPolicySense);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "常识查重")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govPolicySenseService.repeat(title));
	}
	
	/**
	 * 政策退回
	 * 
	 * @param id,retreatContent
	 */
	@GetMapping("/retreatPolicy")
	public R retreatPolicyDeclare(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicySenseService.retreatPolicy(new Query<>(params)));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/selfPage")
	@ApiOperation(value = "分页查询个人加工政策")
	public Page selfPage(@RequestParam Map<String, Object> params) {
		return govPolicySenseService.selectSelfPage(new Query<>(params));
	}
	
	@SysLog("政策提交")
	@PostMapping("/commit")
	@ApiOperation(value = "提交政策到审核")
	public R commit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicySenseService.commit(ids));
	}
	
	@SysLog("政策撤回提交")
	@PostMapping("/reCommit")
	@ApiOperation(value = "撤回提交")
	public R reCommit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicySenseService.reCommit(ids));
	}
	
	@SysLog("放弃加工：移出个人政策")
	@PostMapping("giveUpProcess")
	@ApiOperation(value = "放弃加工政策")
	public R giveUpProcess(@RequestBody List<Integer> ids) {
		return new R<>(govPolicySenseService.giveUpProcess(ids));
	}
	
	@SysLog("政策受理")
	@GetMapping("/beAccept/{id}")
	@ApiOperation(value = "受理政策")
	public R accept(@PathVariable("id") Integer id) {
		return new R<>(govPolicySenseService.accept(id));
	}
	
	@SysLog("政策审核")
	@PostMapping("/examine")
	@ApiOperation(value = "政策审核")
	public R examine(@RequestBody List<Integer> id) {
		return new R<>(govPolicySenseService.doExamine(id));
	}
	
	@SysLog("政策审核不通过")
	@PostMapping("/disExamine")
	@ApiOperation(value = "审核退回")
	public R disExamine(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicySenseService.disExamine(new Query<>(params)));
	}
	
	
}
