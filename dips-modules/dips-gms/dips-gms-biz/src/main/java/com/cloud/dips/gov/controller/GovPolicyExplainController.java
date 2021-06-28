package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.gov.api.dto.ExplainDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExplain;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.service.GovPolicyExplainService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 政策解读
 *
 * @author C.Z.H
 * @date 2018-09-11 11:27:05
 */
@RestController
@RequestMapping("/policy/explain")
@AllArgsConstructor
public class GovPolicyExplainController {

	private final GovPolicyExplainService govPolicyExplainService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@SysLog(value = "分页綜合查询政策解读搜索",role = EnumRole.WEB_TYE)
	@ApiOperation(value = "分页查询解读", notes = "分页查询解读")
	public Page<ExplainVO> page(@RequestParam Map<String, Object> params) {
		return govPolicyExplainService.selectAllPage(new Query<>(params));
	}

	/**
	 * 控制台列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/pageConsole")
	@ApiOperation(value = "控制台分页查询解读", notes = "控制台分页查询解读")
	public Page<ExplainVO> pageConsole(@RequestParam Map<String, Object> params) {
		return govPolicyExplainService.selectConsolePage(new Query<>(params));
	}
	
	/**
	 * 给小程序调用政策解读分页
	 * @param params
	 * @return
	 */
	@GetMapping("/pageWechat")
	@ApiOperation(value = "小程序分页查询解读", notes = "分页查询解读")
	public Page<ExplainVO> pageWeChat(@RequestParam Map<String, Object> params) {
		return govPolicyExplainService.selectWechatPage(new Query<>(params));
	}

	/**
	 * 列表
	 *
	 * @param tagId
	 * @return
	 */
	@GetMapping("/tag/{tagId}")
	@ApiOperation(value = "根据标签分页查询解读", notes = "根据标签分页查询解读")
	public Page pageTag(@PathVariable("tagId") Integer tagId,@RequestParam Map<String, Object> params) {
		params.put("tagId", tagId);
		return govPolicyExplainService.selectPageByTagId(new Query<>(params));
	}

	@GetMapping("{id}")
	@SysLog(value="根据id查询解读",role = EnumRole.WEB_TYE)
	@ApiOperation(value = "根据id查询解读", notes = "根据id查询解读")
	public ExplainVO getId(@PathVariable String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
			govPolicyExplainService.updateViews(ids);
			ExplainVO selectAllVo = govPolicyExplainService.selectAllVo(ids);
			if (null == selectAllVo) {
				return null;
			}
			return selectAllVo;
		}
		
		return null;	
	}
	
	@GetMapping("/infoForConsole/{id}")
	@ApiOperation(value = "根据id查询解读", notes = "根据id查询解读")
	public ExplainVO getIdForConsole(@PathVariable String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
			govPolicyExplainService.updateViews(ids);
			ExplainVO selectAllVo = govPolicyExplainService.selectAllVoForConsole(ids);
			if (null == selectAllVo) {
				return null;
			}
			return selectAllVo;
		}
		
		return null;	
	}

	/**
	 * 新增
	 *
	 * @param explainDTO
	 * @return R
	 */
	@SysLog("新增解读")
	@PostMapping("/create")
	@ApiOperation(value = "新增解读", notes = "新增解读")
	public R insertExplain(@RequestBody ExplainDTO explainDTO) {
		return govPolicyExplainService.insertExplain(explainDTO);
	}
	
	/**
	 * 新增并提交
	 *
	 * @param explainDTO
	 * @return R
	 */
	@SysLog("新增解读并提交")
	@PostMapping("/createAndCommit")
	@ApiOperation(value = "新增解读并提交", notes = "新增解读并提交")
	public R<Boolean> insertExplainAndCommit(@RequestBody ExplainDTO explainDTO) {
		return new R<>(govPolicyExplainService.insertExplainAndCommit(explainDTO));
	}

	/**
	 * 修改
	 *
	 * @param explainDTO
	 * @return R
	 */
	@SysLog("更新解读")
	@PostMapping("/update")
	@ApiOperation(value = "更新解读", notes = "更新解读")
	public R<Boolean> update(@RequestBody ExplainDTO explainDTO) {
		return new R<>(govPolicyExplainService.updateExplain(explainDTO));
	}
	
	/**
	 * 修改
	 *
	 * @param explainDTO
	 * @return R
	 */
	@PostMapping("/updateAndCommit")
	@ApiOperation(value = "更新解读并提交", notes = "更新解读")
	public R<Boolean> updateAndCommit(@RequestBody ExplainDTO explainDTO) {
		return new R<>(govPolicyExplainService.updateExplainAndCommit(explainDTO));
	}

	/**
	 * 删除
	 *
	 * @param explainIds
	 * @return R
	 */
	@SysLog("逻辑删除解读")
	@DeleteMapping
	@ApiOperation(value = "逻辑删除解读", notes = "逻辑删除解读")
	public R<Boolean> delete(@RequestBody Integer[] explainIds) {
		return new R<>(govPolicyExplainService.deleteExplain(explainIds));
	}

	/**
	 * 回收站政策解读列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询解读", notes = "分页查询解读")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govPolicyExplainService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询解读", notes = "根据id查询解读")
	public R recycleInfo(@PathVariable("id") Integer id) {
		return new R<>(govPolicyExplainService.selectRecycleById(id));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除解读")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除解读", notes = "彻底删除解读")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govPolicyExplainService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复解读")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复解读", notes = "恢复解读")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovPolicyExplain govPolicyExplain = govPolicyExplainService.selectById(id);
		govPolicyExplain.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govPolicyExplainService.updateById(govPolicyExplain));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除解读")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除解读", notes = "批量彻底删除解读")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govPolicyExplainService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复解读")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复解读", notes = "批量恢复解读")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyExplain govPolicyExplain = govPolicyExplainService.selectById(id);
			govPolicyExplain.setDelFlag(CommonConstant.STATUS_NORMAL);
			govPolicyExplainService.updateById(govPolicyExplain);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "解读查重", notes = "解读查重")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govPolicyExplainService.repeat(title));
	}
	
	/**
	 * 政策退回
	 * 
	 * @param id,retreatContent
	 */
	@PostMapping("/retreatPolicy")
	public R retreatPolicyExplain(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyExplainService.retreatPolicy(new Query<>(params)));
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
		return govPolicyExplainService.selectSelfPage(new Query<>(params));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/examinePage")
	@ApiOperation(value = "分页查询审核政策")
	public Page examinePage(@RequestParam Map<String, Object> params) {
		return govPolicyExplainService.selectExaminePage(new Query<>(params));
	}
	
	@SysLog("政策提交")
	@PostMapping("/commit")
	@ApiOperation(value = "提交政策到审核")
	public R commit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyExplainService.commit(ids));
	}
	
	@SysLog("政策撤回提交")
	@PostMapping("/reCommit")
	@ApiOperation(value = "撤回提交")
	public R reCommit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyExplainService.reCommit(ids));
	}
	
	@SysLog("放弃加工：移出个人政策")
	@PostMapping("giveUpProcess")
	@ApiOperation(value = "放弃加工政策")
	public R giveUpProcess(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyExplainService.giveUpProcess(ids));
	}
	
	@SysLog("政策受理")
	@GetMapping("/beAccept/{id}")
	@ApiOperation(value = "受理政策")
	public R accept(@PathVariable("id") Integer id) {
		return new R<>(govPolicyExplainService.accept(id));
	}
	
	@SysLog("政策审核")
	@PostMapping("/examine")
	@ApiOperation(value = "政策审核")
	public R examine(@RequestBody List<Integer> id) {
		return new R<>(govPolicyExplainService.doExamine(id));
	}
	
	@SysLog("政策审核不通过")
	@PostMapping("/disExamine")
	@ApiOperation(value = "审核退回")
	public R disExamine(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyExplainService.disExamine(new Query<>(params)));
	}

	@SysLog("相关政策解读查询、条件为政策原文，申报 通用政策")
	@GetMapping("/policy")
	@ApiOperation(value = "相关政策解读查询、条件为政策原文，通用政策", notes = "相关政策解读查询、条件为政策原文，通用政策")
	public R selectGenerList(@RequestParam(value = "id") Integer id) {
		return new R<>(govPolicyExplainService.selectGenerList(id));
	}

	@SysLog("相关解读，根据政策原文匹配通用、申报的政策")
	@GetMapping("/policytext")
	@ApiOperation(value = "相关解读，根据政策原文匹配通用、申报的政策")
	public R findPolicyText(@RequestParam(value = "ids") String ids) {
		return new R<>(govPolicyExplainService.findPolicyText(ids));
	}

	@SysLog("其它解读")
	@GetMapping("/interpretation")
	@ApiOperation("其它解读")
	public R findInterPreTaboo(@RequestParam(value = "ids") String ids,@RequestParam(value = "id") Integer id) {
		return new R<>(govPolicyExplainService.findInterPreTaboo(ids,id));
	}
}
