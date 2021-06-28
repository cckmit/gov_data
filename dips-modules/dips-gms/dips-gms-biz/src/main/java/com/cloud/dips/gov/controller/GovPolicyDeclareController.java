package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.gov.api.dto.DeclareDTO;
import com.cloud.dips.gov.api.entity.GovPolicyDeclare;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.service.GovPolicyDeclareService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 申报政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:23:50
 */
@RestController
@RequestMapping("/policy/declare")
@AllArgsConstructor
public class GovPolicyDeclareController {

	private final GovPolicyDeclareService govPolicyDeclareService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询申报", notes = "分页查询申报")
	@SysLog(value = "分页查询申报政策搜索",role = EnumRole.WEB_TYE)
	public Page page(@RequestParam Map<String, Object> params) {
		return govPolicyDeclareService.selectAllPage(new Query<>(params));
	}
    /**
	 * 小程序端调用
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/pageWechat")
	@ApiOperation(value = "分页查询申报", notes = "分页查询申报")
	public Page pageWechat(@RequestParam Map<String, Object> params) {
		return govPolicyDeclareService.selectAllWxPage(new Query<>(params));
	}

	
	/**
	 * 控制台列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/pageConsole")
	@ApiOperation(value = "控制台分页查询申报", notes = "控制台分页查询申报")
	public Page pageConsole(@RequestParam Map<String, Object> params) {
		return govPolicyDeclareService.selectConsolePage(new Query<>(params));
	}

	
	/**
	 * 列表
	 *
	 * @param tagId
	 * @return
	 */
	@GetMapping("/tag/{tagId}")
	@ApiOperation(value = "根据标签分页查询申报", notes = "根据标签分页查询申报")
	@SysLog(value="根据标签分页查询申报",role = EnumRole.WEB_TYE)
	public Page pageTag(@PathVariable("tagId") Integer tagId,@RequestParam Map<String, Object> params) {
		params.put("tagId", tagId);
		return govPolicyDeclareService.selectPageByTagId(new Query<>(params));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询申报", notes = "根据id查询申报")
	@SysLog(value="根据id查询申报",role = EnumRole.WEB_TYE)
	public R info(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			
			int ids = Integer.parseInt(id);
			DeclareVO declareVO = govPolicyDeclareService.selectDeclareVOById(ids);
			if (null == declareVO) {
				return null;
			}
			return new R<>(declareVO);
		}
		return new R<>(Boolean.FALSE);
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/infoForConsole/{id}")
	@ApiOperation(value = "根据id查询申报", notes = "根据id查询申报")
	public R infoForConsole(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			
			int ids = Integer.parseInt(id);
			DeclareVO declareVO = govPolicyDeclareService.selectDeclareVOByIdForConsole(ids);
			if (null == declareVO) {
				return null;
			}
			return new R<>(declareVO);
		}
		return new R<>(Boolean.FALSE);
	}
	
	/**
	 * 保存
	 *
	 * @param declareDTO
	 * @return R
	 */
	@SysLog("添加申报类政策")
	@PostMapping("/create")
	@ApiOperation(value = "新增申报", notes = "新增申报")
	public R save(@RequestBody DeclareDTO declareDTO) {
		return govPolicyDeclareService.insertDeclare(declareDTO);
	}
	
	/**
	 * 保存
	 *
	 * @param declareDTO
	 * @return R
	 */
	@SysLog("添加申报类政策并提交")
	@PostMapping("/createAndCommit")
	@ApiOperation(value = "新增申报并提交", notes = "新增申报")
	public R saveAndCommit(@RequestBody DeclareDTO declareDTO) {
		govPolicyDeclareService.insertDeclareAndCommit(declareDTO);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 修改
	 *
	 * @param declareDTO
	 * @return R
	 */
	@PostMapping("/update")
	@ApiOperation(value = "更新申报", notes = "更新申报")
	public R update(@RequestBody DeclareDTO declareDTO) {
		govPolicyDeclareService.updateDeclare(declareDTO);
		return new R<>(Boolean.TRUE);
	}
	
	/**
	 * 修改
	 *
	 * @param declareDTO
	 * @return R
	 */
	@SysLog("修改申报类政策并提交")
	@PostMapping("/updateAndCommit")
	@ApiOperation(value = "更新申报并提交", notes = "更新申报")
	public R updateAndCommit(@RequestBody DeclareDTO declareDTO) {
		govPolicyDeclareService.updateDeclareAndCommit(declareDTO);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量删除申报类政策")
	@DeleteMapping
	@ApiOperation(value = "批量删除申报", notes = "批量删除申报")
	public R<Boolean> delete(@RequestBody List<Integer> ids) {
		for (Integer declareId : ids) {
			govPolicyDeclareService.deleteDeclare(declareId);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站申报政策列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站申报", notes = "分页查询回收站申报")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govPolicyDeclareService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询申报", notes = "根据id查询申报")
	public R recycleInfo(@PathVariable("id") Integer id) {
		return new R<>(govPolicyDeclareService.selectRecycleById(id));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除申报")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除申报", notes = "彻底删除申报")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govPolicyDeclareService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复申报")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复申报", notes = "恢复申报")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovPolicyDeclare govPolicyDeclare = govPolicyDeclareService.selectById(id);
		govPolicyDeclare.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govPolicyDeclareService.updateById(govPolicyDeclare));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除申报")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除申报", notes = "批量彻底删除申报")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govPolicyDeclareService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复申报")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复申报", notes = "批量恢复申报")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyDeclare govPolicyDeclare = govPolicyDeclareService.selectById(id);
			govPolicyDeclare.setDelFlag(CommonConstant.STATUS_NORMAL);
			govPolicyDeclareService.updateById(govPolicyDeclare);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "申报查重", notes = "申报查重")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govPolicyDeclareService.repeat(title));
	}

	/**
	 * 政策退回
	 * 
	 * @param id,retreatContent
	 */
	@PostMapping("/retreatPolicy")
	public R retreatPolicyDeclare(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyDeclareService.retreatPolicy(new Query<>(params)));
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
		return govPolicyDeclareService.selectSelfPage(new Query<>(params));
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
		return govPolicyDeclareService.selectExaminePage(new Query<>(params));
	}
	
	@SysLog("政策提交")
	@PostMapping("/commit")
	@ApiOperation(value = "提交政策到审核")
	public R commit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyDeclareService.commit(ids));
	}
	
	@SysLog("政策撤回提交")
	@PostMapping("/reCommit")
	@ApiOperation(value = "撤回提交")
	public R reCommit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyDeclareService.reCommit(ids));
	}
	
	@SysLog("放弃加工：移出个人政策")
	@PostMapping("giveUpProcess")
	@ApiOperation(value = "放弃加工政策")
	public R giveUpProcess(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyDeclareService.giveUpProcess(ids));
	}
	
	@SysLog("政策受理")
	@GetMapping("/beAccept/{id}")
	@ApiOperation(value = "受理政策")
	public R accept(@PathVariable("id") Integer id) {
		return new R<>(govPolicyDeclareService.accept(id));
	}
	
	@SysLog("政策审核")
	@PostMapping("/examine")
	@ApiOperation(value = "政策审核")
	public R examine(@RequestBody List<Integer> id) {
		return new R<>(govPolicyDeclareService.doExamine(id));
	}
	
	@SysLog("政策审核不通过")
	@PostMapping("/disExamine")
	@ApiOperation(value = "审核退回")
	public R disExamine(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyDeclareService.disExamine(new Query<>(params)));
	}

	@SysLog("根据政策依据查找申报政策")
	@GetMapping("/basis")
	@ApiOperation(value = "根据政策依据查找申报政策")
	public R selctRelation(@RequestParam(value = "ids") String ids,@RequestParam(value = "id") Integer id){
		return  new R<>(govPolicyDeclareService.selctRelation(ids,id));
	}


	/**
	 * 相关申报政策 根据标签匹配
	 * @param ids
	 * @return
	 */
	@SysLog("相关申报政策 根据标签匹配")
	@ApiOperation(value ="相关申报政策，根据标签匹配" )
	@GetMapping("/matching")
	public  R selectMatching(@RequestParam(value = "ids")String ids){
		return new R<>(govPolicyDeclareService.selectMatching(ids));
	}
	
	/**
	 * 申报政策比对
	 */
	@GetMapping("/comparisonByDeclare")
	@ApiOperation(value = "申报政策比对", notes = "申报政策比对", httpMethod = "GET")
	@SysLog(value="申报政策比对",role = EnumRole.WEB_TYE)
	public List<ComparisonVO> selectComparisonByDeclare(@RequestParam("ids[]") Integer[] ids) {
		
		return govPolicyDeclareService.selectComparisonByDeclare(ids);
	} 
}
