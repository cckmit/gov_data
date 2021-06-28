package com.cloud.dips.gov.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.entity.GovPolicyGeneral;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;
import com.cloud.dips.gov.api.vo.ComparisonGeneralVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.service.GovPolicyGeneralService;
import com.cloud.dips.gov.service.ScrapyGovPolicyGeneralService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 通用政策模型
 *
 * @author yy
 * @date 2018-09-11 11:19:21
 */
@RestController
@RequestMapping("/policy/general")
@AllArgsConstructor
public class GovPolicyGeneralController {

	private final GovPolicyGeneralService govPolicyGeneralService;

	@GetMapping("/page")
	@SysLog(value = "分页綜合查询通用政策搜索", role = EnumRole.WEB_TYE)
	@ApiOperation(value = "分页綜合查询表", notes = "通用政策模型", httpMethod = "GET")
	public Page page(@RequestParam Map<String, Object> params) {
		return govPolicyGeneralService.selectAllPage(new Query<>(params));
	}

	@GetMapping("/pageConsole")
	@ApiOperation(value = "控制台分页綜合查询表", notes = "控制台通用政策模型", httpMethod = "GET")
	public Page pageConsole(@RequestParam Map<String, Object> params) {
		return govPolicyGeneralService.selectConsolePage(new Query<>(params));
	}

	@GetMapping("/pageWechat")
	@ApiOperation(value = "小程序分页綜合查询表", notes = "通用政策模型", httpMethod = "GET")
	public Page pageWx(@RequestParam Map<String, Object> params) {
		return govPolicyGeneralService.selectWechatPage(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询通用政策")
	@SysLog(value = "根据id查询通用政策", role = EnumRole.WEB_TYE)
	public R<GeneralVO> info(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
			GeneralVO generalVO = govPolicyGeneralService.selectGeneralVOById(ids);
			if (null == generalVO) {
				return null;
			}
			return new R<>(generalVO);
		}
		return null;
	}

	@GetMapping("/infoForConsole/{id}")
	@ApiOperation(value = "根据id查询通用政策")
	public R<GeneralVO> infoForConsole(@PathVariable("id") String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			int ids = Integer.parseInt(id);
			GeneralVO generalVO = govPolicyGeneralService.selectGeneralVOByIdForConsole(ids);
			if (null == generalVO) {
				return null;
			}
			return new R<>(generalVO);
		}
		return null;
	}

	/**
	 * 根据标签id取政策类的分页
	 */
	@GetMapping("/tag/{tagId}")
	@ApiOperation(value = "根据标签查询通用政策")

	public Page pageTag(@PathVariable("tagId") Integer tagId, @RequestParam Map<String, Object> params) {
		params.put("tagId", tagId);
		return govPolicyGeneralService.selectPageByTagId(new Query<>(params));
	}

	/**
	 * 添加通用政策
	 *
	 * @param generalDto
	 * @return
	 */
	@SysLog("添加通用政策")
	@PostMapping("/create")
	@ApiOperation(value = "添加政策详情", notes = "添加政策详情", httpMethod = "POST")
	public R save(@RequestBody GeneralDTO generalDto) {
		return govPolicyGeneralService.insertPolicyGeneral(generalDto);
	}


	/**
	 * 添加通用政策并提交
	 *
	 * @param generalDto
	 * @return
	 */
	@SysLog("添加通用政策并提交")
	@PostMapping("/createAndCommit")
	@ApiOperation(value = "添加政策详情并提交", notes = "添加政策详情", httpMethod = "POST")
	public R<Boolean> saveAndCommit(@RequestBody GeneralDTO generalDto) {
		govPolicyGeneralService.insertPolicyGeneralAndCommit(generalDto);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 修改
	 *
	 * @param generalDTO
	 * @return R
	 */
	@SysLog("修改通用政策")
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "修改")
	public Boolean update(@RequestBody GeneralDTO generalDTO) {

		return govPolicyGeneralService.updateGeneral(generalDTO);
	}

	/**
	 * 修改
	 *
	 * @param generalDTO
	 * @return R
	 */
	@PostMapping("/updateAndCommit")
	@SysLog("修改并提交")
	@ApiOperation(value = "修改并提交", notes = "修改")
	public Boolean updateAndCommit(@RequestBody GeneralDTO generalDTO) {
		return govPolicyGeneralService.updateGeneralAndCommit(generalDTO);
	}

	/**
	 * 逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@DeleteMapping("/logicDelete")
	@SysLog("根据Id批量逻辑删除")
	@ApiOperation(value = "根据Id批量逻辑删除", notes = "根据Id删除")
	public R<Boolean> deleteLogic(@RequestBody Integer[] ids) {
		return new R<>(govPolicyGeneralService.logicDelete(ids));
	}

	/**
	 * 回收站通用政策列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站通用政策")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return govPolicyGeneralService.selectRecyclePage(new Query<>(params));
	}

	/**
	 * 回收站查询单个
	 */
	@GetMapping("/recycle/{id}")
	@ApiOperation(value = "根据id查询回收站通用政策")
	public R recycleInfo(@PathVariable("id") Integer id) {
		return new R<>(govPolicyGeneralService.selectGeneralVOById(id));
	}

	/**
	 * 回收站彻底删除
	 */
	@SysLog("彻底删除通用政策")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除通用政策")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(govPolicyGeneralService.recycleDelete(id));
	}

	/**
	 * 回收站恢复
	 */
	@SysLog("恢复通用政策")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "恢复通用政策")
	public R recycleRecover(@PathVariable("id") Integer id) {
		GovPolicyGeneral govPolicyGeneral = govPolicyGeneralService.selectById(id);
		govPolicyGeneral.setDelFlag(CommonConstant.STATUS_NORMAL);
		return new R<>(govPolicyGeneralService.updateById(govPolicyGeneral));
	}

	/**
	 * 回收站彻底批量删除
	 */
	@SysLog("批量彻底删除通用政策")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除通用政策")
	public R recycleBatchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			govPolicyGeneralService.recycleDelete(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 回收站批量恢复
	 */
	@SysLog("批量恢复通用政策")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量恢复通用政策")
	public R recycleBatchRecover(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicyGeneral govPolicyGeneral = govPolicyGeneralService.selectById(id);
			govPolicyGeneral.setDelFlag(CommonConstant.STATUS_NORMAL);
			govPolicyGeneralService.updateById(govPolicyGeneral);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查重
	 */
	@PostMapping("/repeat")
	@ApiOperation(value = "通用政策查重")
	public R repeat(@RequestBody Map<String, String> params) {
		String title = params.get("title");
		return new R<>(govPolicyGeneralService.repeat(title));
	}

	/**
	 * 通用政策撤回
	 *
	 * @param id,retreatContent
	 */
	@PostMapping("/retreatPolicy")
	@ApiOperation(value = "撤回政策")
	public R retreatPolicyGeneral(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyGeneralService.retreatPolicy(new Query<>(params)));
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
		return govPolicyGeneralService.selectSelfPage(new Query<>(params));
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
		return govPolicyGeneralService.selectExaminePage(new Query<>(params));
	}

	@SysLog("政策提交")
	@PostMapping("/commit")
	@ApiOperation(value = "提交政策到审核")
	public R commit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyGeneralService.commit(ids));
	}

	@SysLog("政策撤回提交")
	@PostMapping("/reCommit")
	@ApiOperation(value = "撤回提交")
	public R reCommit(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyGeneralService.reCommit(ids));
	}

	@SysLog("放弃加工：移出个人政策")
	@PostMapping("giveUpProcess")
	@ApiOperation(value = "放弃加工政策")
	public R giveUpProcess(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyGeneralService.giveUpProcess(ids));
	}

	@SysLog("政策受理")
	@GetMapping("/beAccept/{id}")
	@ApiOperation(value = "受理政策")
	public R accept(@PathVariable("id") Integer id) {
		return new R<>(govPolicyGeneralService.accept(id));
	}

	@SysLog("政策审核")
	@PostMapping("/examine")
	@ApiOperation(value = "政策审核")
	public R examine(@RequestBody List<Integer> ids) {
		return new R<>(govPolicyGeneralService.doExamine(ids));
	}

	@SysLog("政策审核不通过")
	@PostMapping("/disExamine")
	@ApiOperation(value = "审核退回")
	public R disExamine(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyGeneralService.disExamine(new Query<>(params)));
	}

	@PostMapping("/getGeneralAndDeclareList")
	@ApiOperation(value = "获取通用政策与政策申报的集合")
	public Page getGeneralAndDeclareList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.setRecords(govPolicyGeneralService.getGeneralAndDeclareList(new Query<>(params)));
		return query;
	}

	@SysLog("相关通用政策查询 根据标签匹配度")
	@GetMapping("/selectrelevant")
	@ApiOperation(value = "相关通用政策查询")
	public R selectRelevant(@RequestParam(value = "ids") String ids, Integer id) {
		List<GeneralVO> generalVOS = govPolicyGeneralService.selectRelevant(ids, id);

		return new R<>(generalVOS);


	}

	@SysLog("根据政策依据查找通用政策")
	@GetMapping("/basis")
	@ApiOperation(value = "根据政策依据查找通用政策")
	public R selctRelation(@RequestParam(value = "ids") String ids, @RequestParam("id") Integer id) {
		return new R<>(govPolicyGeneralService.selctRelation(ids, id));
	}


	/**
	 * 通用政策比对
	 */
	@GetMapping("/comparisonByGeneral")
	@ApiOperation(value = "通用政策比对", notes = "通用政策比对", httpMethod = "GET")
	@SysLog(value = "通用政策比对", role = EnumRole.WEB_TYE)
	public List<ComparisonGeneralVO> selectComparisonByGeneral(@RequestParam("ids[]") Integer[] ids) {

		return govPolicyGeneralService.selectComparisonByGeneral(ids);
	}

	/**
	 * 全文下载
	 */
	@PostMapping("/fullDownloadGeneral")
	@ApiOperation(value = "全文下载通用政策", notes = "全文下载通用政策", httpMethod = "POST")
	@SysLog(value = "全文下载通用政策", role = EnumRole.WEB_TYE)
	public void fullDownloadGeneral(HttpServletResponse response, Integer id) {
		govPolicyGeneralService.fullDownloadGeneral(response, id);

	}

	/**
	 * 根据条件获取id数组
	 *
	 * @param params
	 * @return
	 * @name yaonuan
	 */
	@PostMapping("/ids")
	public List<Long> gainList(@RequestBody Map<String, Object> params) {
		return govPolicyGeneralService.gainList(params);

	}

	/**
	 * 根据id集查询政策标题、正文信息
	 * @param ids ids集
	 * @return 政策信息
	 * @name yaonuan
	 */
	@PostMapping("/selectByIds")
	public List<GovPolicyGeneral> selectByIds(@RequestBody List<Long> ids) {
		return govPolicyGeneralService.queryByInfos(ids);
	}
}
