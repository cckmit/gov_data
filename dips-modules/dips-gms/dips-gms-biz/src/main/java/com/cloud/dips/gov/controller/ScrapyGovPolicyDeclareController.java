package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyDeclare;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;
import com.cloud.dips.gov.service.GovPolicyDeclareService;
import com.cloud.dips.gov.service.ScrapyGovPolicyDeclareService;
import io.swagger.annotations.ApiOperation;
import jxl.Sheet;
import jxl.Workbook;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 申报政策模型
 *
 * @author BigPan
 * @date 2018-10-24 11:25:41
 */
@RestController
@RequestMapping("/scrapy/declare")
@AllArgsConstructor
public class ScrapyGovPolicyDeclareController {

	private final ScrapyGovPolicyDeclareService scrapyGovPolicyDeclareService;
	private final GovPolicyDeclareService govPolicyDeclareService;

	/**
	 * 复制入库
	 */
	@SysLog("申报复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "申报复制入库")
	public R copy(@PathVariable Integer id) {
		ScrapyGovPolicyDeclare scrapyGovPolicyDeclare = scrapyGovPolicyDeclareService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govPolicyDeclareService.repeat(scrapyGovPolicyDeclare.getTitle()) || "2".equals(scrapyGovPolicyDeclare.getDelFlag())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(scrapyGovPolicyDeclare.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(scrapyGovPolicyDeclare.getDelFlag())) {
			scrapyGovPolicyDeclareService.copy(scrapyGovPolicyDeclare);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("申报批量复制入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "申报批量复制入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyDeclare sDeclare = scrapyGovPolicyDeclareService.selectById(id);
			if (!govPolicyDeclareService.repeat(sDeclare.getTitle())) {
				sDeclare.setDelFlag("3");
				scrapyGovPolicyDeclareService.updateById(sDeclare);
				countFalse= countFalse+1;
				sb.append(sDeclare.getTitle()+"&");
				continue;
			}
			if ("2".equals(sDeclare.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sDeclare.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sDeclare.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sDeclare.getDelFlag())) {
				scrapyGovPolicyDeclareService.copy(sDeclare);
				countSuccess=countSuccess+1;
			}
		}
		if(countFalse > 0) {
			return new R<>(Boolean.TRUE,"检测到有"+countFalse+"条重复政策，本次成功导入"+countSuccess+"条政策");
		}else if(countFalse == ids.size()){
			return new R<>(Boolean.FALSE,"检测到有"+countFalse+"条重复政策，本次成功导入"+countSuccess+"条政策");
		}else {
			return new R<>(Boolean.TRUE,"导入成功，共"+countSuccess+"条");
		}
	}

	/**
	 * excel导入
	 */
	@SysLog("excel导入申报")
	@PostMapping("/import")
	@ApiOperation(value = "excel导入申报")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		Integer rowsNum;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovPolicyDeclare declare = new ScrapyGovPolicyDeclare();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					declare.setTitle(sheet.getCell(0, i).getContents());
					declare.setSummary(sheet.getCell(1, i).getContents());
					declare.setSource(sheet.getCell(2, i).getContents());
					declare.setReference(sheet.getCell(3, i).getContents());
					declare.setIssue(sheet.getCell(4, i).getContents());
					declare.setStyle(sheet.getCell(5, i).getContents());
					declare.setLevel(sheet.getCell(6, i).getContents());
					declare.setCondition(sheet.getCell(7, i).getContents());
					declare.setStandard(sheet.getCell(8, i).getContents());
					declare.setProcess(sheet.getCell(9, i).getContents());
					declare.setMethod(sheet.getCell(10, i).getContents());
					declare.setRequirement(sheet.getCell(11, i).getContents());
					declare.setStatus(sheet.getCell(12, i).getContents());
					declare.setSpecial(sheet.getCell(13, i).getContents());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (isString2Date(sheet.getCell(14, i).getContents())) {
						declare.setWriteTime(new java.sql.Date(sdf.parse(sheet.getCell(14, i).getContents()).getTime()));
					}
					if (isString2Date(sheet.getCell(15, i).getContents())) {
						declare.setPublishTime(new java.sql.Date(sdf.parse(sheet.getCell(15, i).getContents()).getTime()));
					}
					if (isString2Date(sheet.getCell(16, i).getContents())) {
						declare.setEffectTime(new java.sql.Date(sdf.parse(sheet.getCell(16, i).getContents()).getTime()));
					}
					if (isString2Date(sheet.getCell(17, i).getContents())) {
						declare.setInvalidTime(new java.sql.Date(sdf.parse(sheet.getCell(17, i).getContents()).getTime()));
					}
					declare.setText(sheet.getCell(18, i).getContents());
					declare.setUrl(sheet.getCell(19, i).getContents());
					declare.setTarget(sheet.getCell(20, i).getContents());
					declare.setMode(sheet.getCell(21, i).getContents());
					declare.setFormality(sheet.getCell(22, i).getContents());
					declare.setSupport(sheet.getCell(23, i).getContents());
					declare.setTheme(sheet.getCell(24, i).getContents());
					declare.setFund(sheet.getCell(25, i).getContents());
					declare.setScale(sheet.getCell(26, i).getContents());
					declare.setIndustry(sheet.getCell(27, i).getContents());
					declare.setTag(sheet.getCell(28, i).getContents());
					declare.setCreatorId(SecurityUtils.getUser().getId());
					scrapyGovPolicyDeclareService.insert(declare);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * excel模版下载
	 */
	@GetMapping("/download")
	@ApiOperation(value = "申报excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("申报政策采集");
			HSSFRow row = sheetElement.createRow(0);
			sheetElement.setDefaultColumnWidth(20);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("标题(必填)");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("摘要");
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("来源");
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("索引号");
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("发文号");
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("文体 单选" + getString("POLICY_STYLE"));
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("层级 单选" + getString("POLICY_LEVEL"));
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue("申报条件");
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue("扶持标准");
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue("办理流程");
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue("申报方式");
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue("材料递交要求");
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue("申报状态 单选" + getString("DECLARE_STATUS"));
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue("专项类型 单选" + getString("DECLARE_SPECIAL"));
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue("成文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue("发文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell16 = row.createCell(16);
			cell16.setCellValue("生效时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell17 = row.createCell(17);
			cell17.setCellValue("失效时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell18 = row.createCell(18);
			cell18.setCellValue("正文");
			HSSFCell cell19 = row.createCell(19);
			cell19.setCellValue("原文链接");
			HSSFCell cell20 = row.createCell(20);
			cell20.setCellValue("申报对象 多选" + getString("DECLARE_TARGET"));
			HSSFCell cell21 = row.createCell(21);
			cell21.setCellValue("扶持方式 多选" + getString("DECLARE_MODE"));
			HSSFCell cell22 = row.createCell(22);
			cell22.setCellValue("扶持形式 多选" + getString("DECLARE_FORMALITY"));
			HSSFCell cell23 = row.createCell(23);
			cell23.setCellValue("支持方式 多选" + getString("DECLARE_SUPPORT"));
			HSSFCell cell24 = row.createCell(24);
			cell24.setCellValue("主题 多选" + getString("POLICY_THEME"));
			HSSFCell cell25 = row.createCell(25);
			cell25.setCellValue("扶持资金规模 多选" + getString("DECLARE_FUND"));
			HSSFCell cell26 = row.createCell(26);
			cell26.setCellValue("适用规模 多选" + getString("POLICY_SCALE"));
			HSSFCell cell27 = row.createCell(27);
			cell27.setCellValue("行业 多选" + getString("POLICY_INDUSTRY"));
			HSSFCell cell28 = row.createCell(28);
			cell28.setCellValue("标签");
			
			/**设置前200行日期单元格格式为yyyy-MM-dd格式*/
			for(int i=1;i<200;i++) {
				HSSFRow everyRow = sheetElement.createRow(i);
				HSSFCellStyle textStyle = workbook.createCellStyle();
				HSSFDataFormat format = workbook.createDataFormat();
				textStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
				everyRow.createCell(14).setCellStyle(textStyle);
				everyRow.createCell(15).setCellStyle(textStyle);
				everyRow.createCell(16).setCellStyle(textStyle);
				everyRow.createCell(17).setCellStyle(textStyle);
			}

			rs.reset();
			// 自动识别
			rs.setContentType("multipart/form-data");
			String excelName = "scrapyDeclare";
			// windows系统使用以下转换中文，linux系统未知
			rs.setHeader("Content-Disposition", "attachment;filename=" + excelName + ".xls");
			// 文件流输出到rs里
			workbook.write(rs.getOutputStream());
			rs.getOutputStream().flush();
			rs.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getString(String number) {
		List<String> list = scrapyGovPolicyDeclareService.listDictByNumber(number);
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i)).append(")");
			} else {
				sb.append(list.get(i)).append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询采集申报")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyDeclareService.selectAllPage(new Query<>(params));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集申报")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovPolicyDeclare scrapyGovPolicyDeclare = scrapyGovPolicyDeclareService.selectById(id);
		return new R<>(scrapyGovPolicyDeclare);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovPolicyDeclare
	 * @return R
	 */
	@SysLog("新增采集申报")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集申报")
	public R save(@RequestBody ScrapyGovPolicyDeclare scrapyGovPolicyDeclare) {
		return new R<>(scrapyGovPolicyDeclareService.insert(scrapyGovPolicyDeclare));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovPolicyDeclare
	 * @return R
	 */
	@SysLog("更新采集申报")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集申报")
	public R update(@RequestBody ScrapyGovPolicyDeclare scrapyGovPolicyDeclare) {
		scrapyGovPolicyDeclareService.updateById(scrapyGovPolicyDeclare);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集申报")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集申报")
	public R delete(@PathVariable Integer id) {
		ScrapyGovPolicyDeclare scrapyGovPolicyDeclare = scrapyGovPolicyDeclareService.selectById(id);
		scrapyGovPolicyDeclare.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovPolicyDeclareService.updateById(scrapyGovPolicyDeclare));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量逻辑删除采集申报")
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集申报")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovPolicyDeclare scrapyGovPolicyDeclare = scrapyGovPolicyDeclareService.selectById(id);
			if (!StrUtil.equals(scrapyGovPolicyDeclare.getDelFlag(), "2")) {
				scrapyGovPolicyDeclare.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovPolicyDeclareService.updateById(scrapyGovPolicyDeclare);
			}
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 获取被放入回收站的申报政策
	 *
	 * @return 被放入回收站的申报政策列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集申报")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyDeclareService.getRecyclePage(new Query<>(params));
	}

	/**
	 * 彻底删除申报政策
	 *
	 * @param id 申报政策ID
	 */
	@SysLog("彻底删除采集申报")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除采集申报")
	public R recycleDelete(@PathVariable("id") Integer id) {
		scrapyGovPolicyDeclareService.deleteById(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 将申报政策从回收站还原
	 *
	 * @param id 申报政策ID
	 */
	@SysLog("还原采集申报")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原采集申报")
	public R recycleRecover(@PathVariable("id") Integer id) {
		scrapyGovPolicyDeclareService.recoverRecycle(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量彻底删除申报政策
	 *
	 * @param ids 申报政策ID集合
	 */
	@SysLog("批量彻底删除采集申报")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除采集申报")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicyDeclareService.deleteById(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量将申报政策从回收站还原
	 *
	 * @param ids 申报政策ID集合
	 */
	@SysLog("批量还原采集申报")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原采集申报")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicyDeclareService.recoverRecycle(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 验证String能否转化为Date
	 *
	 * @param str
	 * @return Boolean
	 */
	private Boolean isString2Date(String str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			new java.sql.Date(sdf.parse(str).getTime());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SysLog("添加到个人政策")
	@PostMapping("/bringToSelfProcess")
	@ApiOperation(value = "添加到个人政策")
	public R bringToSelfProcess(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovPolicyDeclare scrapyGovPolicyDeclare = scrapyGovPolicyDeclareService.selectById(id);
			scrapyGovPolicyDeclare.setExamineStatus(0);
			scrapyGovPolicyDeclare.setProcessorId(SecurityUtils.getUser().getId());
			scrapyGovPolicyDeclareService.updateById(scrapyGovPolicyDeclare);
			}
		return new R<>(Boolean.TRUE);
	}
}
