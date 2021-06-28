package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.gov.api.dto.InformationDTO;
import com.cloud.dips.gov.api.entity.GovInformation;
import com.cloud.dips.gov.api.vo.InformationVO;
import com.cloud.dips.gov.service.GovInformationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 政策资讯模型
 *
 * @author CUI.CAN
 * @date 2018-09-11 10:27:23
 */
@RestController
@RequestMapping("/policy/information")
@AllArgsConstructor
public class GovInformationController {

	private final GovInformationService govInformationService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "获取政策资讯列表", notes = "获取表中状态为正常的资讯列表")
	@SysLog(value = "分页查询政策资讯搜索",role = EnumRole.WEB_TYE)
	public Page page(@RequestParam Map<String, Object> params) {
		return govInformationService.selectInformationPage(new Query<>(params));
	}
	
	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/pageConsole")
	@ApiOperation(value = "控制台获取政策资讯列表", notes = "控制台获取表中状态为正常的资讯列表")
	public Page pageConsole(@RequestParam Map<String, Object> params) {
		return govInformationService.selectConsolePage(new Query<>(params));
	}

	@GetMapping("/pageWechat")
	@ApiOperation(value = "微信小程序获取政策资讯列表", notes = "获取表中状态为正常的资讯列表")
	public Page pageWeChat(@RequestParam Map<String, Object> params) {
		return govInformationService.selectInformationWechatPage(new Query<>(params));
	}

	/**
	 * 根据标签id查询列表
	 *
	 * @param tagId
	 * @return
	 */
	@GetMapping("/tag/{tagId}")
	@ApiOperation(value = "获取资讯列表", notes = "根据标签ID获取资讯列表: params{资讯ID: tagId}")
	public Page pageTag(@PathVariable("tagId") Integer tagId,@RequestParam Map<String, Object> params) {
		params.put("tagId", tagId);
		return govInformationService.selectPageByTagId(new Query<>(params));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "获取资讯信息", notes = "根据ID获取资讯信息: params{资讯ID: id}")
	@SysLog(value="获取资讯信息",role = EnumRole.WEB_TYE)
	public R info(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
		InformationVO govInformation = govInformationService.selectInformationById(ids);
		if (null == govInformation) {
			return null;
		}
		return new R<>(govInformation);
		}
		return new R<>(Boolean.FALSE,"地址不允许含有非法字符，请查看其它页面！");
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/infoForConsole/{id}")
	@ApiOperation(value = "获取资讯信息", notes = "根据ID获取资讯信息: params{资讯ID: id}")
	public R infoForConsole(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
		InformationVO govInformation = govInformationService.selectInformationByIdForConsole(ids);
		if (null == govInformation) {
			return null;
		}
		return new R<>(govInformation);
		}
		return new R<>(Boolean.FALSE,"地址不允许含有非法字符，请查看其它页面！");
	}
	
	/**
	 * 保存
	 *
	 * @param informationDTO
	 * @return R
	 */
	@SysLog("添加资讯")
	@PostMapping("/create")
	@ApiOperation(value = "添加资讯", notes = "给表中添加资讯信息")
	public R save(@RequestBody InformationDTO informationDTO) {
		return govInformationService.insertInformation(informationDTO);
	}
	
	/**
	 * 保存
	 *
	 * @param informationDTO
	 * @return R
	 */
	@SysLog("添加资讯并提交")
	@PostMapping("/createAndCommit")
	@ApiOperation(value = "添加资讯并提交", notes = "给表中添加资讯信息")
	public R saveAndCommit(@RequestBody InformationDTO informationDTO) {
		return new R<>(govInformationService.insertInformationAndCommit(informationDTO));
	}

	/**
	 * 修改
	 *
	 * @param informationDTO
	 * @return R
	 */
	@SysLog("修改资讯")
	@PostMapping("/update")
	@ApiOperation(value = "修改资讯", notes = "修改表中资讯信息")
	public R update(@RequestBody InformationDTO informationDTO) {
		return new R<>(govInformationService.updateGovInformationById(informationDTO));
	}
	
	/**
	 * 修改
	 *
	 * @param informationDTO
	 * @return R
	 */
	@SysLog("修改资讯并提交")
	@PostMapping("/updateAndCommit")
	@ApiOperation(value = "修改资讯并提交", notes = "修改表中资讯信息")
	public R updateAndCommit(@RequestBody InformationDTO informationDTO) {
		return new R<>(govInformationService.updateGovInformationByIdAndCommit(informationDTO));
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("删除资讯")
	@DeleteMapping("/delete")
	@ApiOperation(value = "批量删除资讯", notes = "根据id列表批量删除表中资讯信息")
	public R delete(@RequestBody List<Integer> ids) {
		return new R<>(govInformationService.deleteByIds(ids));
	}

	/**
	 * 回收站资讯列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站政策资讯", notes = "分页查询回收站政策资讯")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govInformationService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询回收站政策资讯", notes = "根据id查询回收站政策资讯")
	public R recycleInfo(@PathVariable("id") Integer id) {
		return new R<>(govInformationService.selectRecycleById(id));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除政策资讯")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除政策资讯", notes = "彻底删除政策资讯")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govInformationService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复政策资讯")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复政策资讯", notes = "恢复政策资讯")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovInformation govInformation = govInformationService.selectById(id);
		govInformation.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govInformationService.updateById(govInformation));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量删除政策资讯")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量删除政策资讯", notes = "批量删除政策资讯")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govInformationService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复政策资讯")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复政策资讯", notes = "批量恢复政策资讯")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovInformation govInformation = govInformationService.selectById(id);
			govInformation.setDelFlag(CommonConstant.STATUS_NORMAL);
			govInformationService.updateById(govInformation);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "政策资讯查重", notes = "政策资讯查重")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govInformationService.repeat(title));
	}

	
	  
	  
	/**
	 * 政策退回
	 * 
	 * @param id,retreatContent
	 */
	@PostMapping("/retreatPolicy")
	public R retreatPolicyDeclare(@RequestParam Map<String, Object> params) {
		return new R<>(govInformationService.retreatPolicy(new Query<>(params)));
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
		return govInformationService.selectSelfPage(new Query<>(params));
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
		return govInformationService.selectExaminePage(new Query<>(params));
	}
	
	@SysLog("政策提交")
	@PostMapping("/commit")
	@ApiOperation(value = "提交政策到审核")
	public R commit(@RequestBody List<Integer> ids) {
		return new R<>(govInformationService.commit(ids));
	}
	
	@SysLog("政策撤回提交")
	@PostMapping("/reCommit")
	@ApiOperation(value = "撤回提交")
	public R reCommit(@RequestBody List<Integer> ids) {
		return new R<>(govInformationService.reCommit(ids));
	}
	
	@SysLog("放弃加工：移出个人政策")
	@PostMapping("giveUpProcess")
	@ApiOperation(value = "放弃加工政策")
	public R giveUpProcess(@RequestBody List<Integer> ids) {
		return new R<>(govInformationService.giveUpProcess(ids));
	}
	
	@SysLog("政策受理")
	@GetMapping("/beAccept/{id}")
	@ApiOperation(value = "受理政策")
	public R accept(@PathVariable("id") Integer id) {
		return new R<>(govInformationService.accept(id));
	}
	
	@SysLog("政策审核")
	@PostMapping("/examine")
	@ApiOperation(value = "政策审核")
	public R examine(@RequestBody List<Integer> id) {
		return new R<>(govInformationService.doExamine(id));
	}
	
	@SysLog("政策审核不通过")
	@PostMapping("/disExamine")
	@ApiOperation(value = "审核退回")
	public R disExamine(@RequestParam Map<String, Object> params) {
		return new R<>(govInformationService.disExamine(new Query<>(params)));
	}


	/**
	 * 相关申报政策 根据标签匹配
	 * @param ids
	 * @return
	 */
	@SysLog("相关政策资讯 根据标签匹配")
	@ApiOperation(value ="相关政策资讯，根据标签匹配" )
	@GetMapping("/matching")
	public  R selectMatching(@RequestParam(value = "ids")String ids,@RequestParam(value = "id") Long id){
		return new R<>(govInformationService.selectMatching(ids,id));
	}

	/**
	 * 根据政策资讯 标签 匹配申报、通用政策
	 * @param ids
	 * @return
	 */
	@SysLog("根据政策资讯 标签 匹配申报、通用政策")
	@ApiOperation(value ="根据政策资讯 标签 匹配申报、通用政策" )
	@GetMapping("/commonMatching")
	public R selectCommonMatching(@RequestParam(value = "ids") String ids){
		return  new R<>(govInformationService.selectCommonMatching(ids));
	}
	
}
