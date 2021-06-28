package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicySense;
import com.cloud.dips.gov.service.GovPolicySenseService;
import com.cloud.dips.gov.service.ScrapyGovPolicySenseService;
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
 * @author C.Z.H
 */
@RestController
@RequestMapping("/scrapy/sense")
@AllArgsConstructor
public class ScrapyGovSenseController {

	private final ScrapyGovPolicySenseService scrapyGovPolicySenseService;
	private final GovPolicySenseService govPolicySenseService;

	/**
	 * 复制保存
	 */
	@SysLog("常识复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "常识复制入库")
	public R copySave(@PathVariable Integer id) {
		ScrapyGovPolicySense sSense = scrapyGovPolicySenseService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govPolicySenseService.repeat(sSense.getTitle())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(sSense.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sSense.getDelFlag())) {
			scrapyGovPolicySenseService.copySave(sSense);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("常识批量复制入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "常识批量复制入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicySense sSense = scrapyGovPolicySenseService.selectById(id);
			if (!govPolicySenseService.repeat(sSense.getTitle())) {
				sSense.setDelFlag("3");
				scrapyGovPolicySenseService.updateById(sSense);
				countFalse=countFalse++;
				sb.append(sSense.getTitle()+"&");
			}
			if (CommonConstant.STATUS_NORMAL.equals(sSense.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sSense.getDelFlag())) {
				scrapyGovPolicySenseService.copySave(sSense);
				countSuccess=countSuccess++;
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
	@SysLog("常识excel导入")
	@PostMapping("/import")
	@ApiOperation(value = "常识excel导入")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		int rowsNum;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovPolicySense sense = new ScrapyGovPolicySense();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					sense.setTitle(sheet.getCell(0, i).getContents());
					sense.setKeywords(sheet.getCell(1, i).getContents());
					sense.setIntroduce(sheet.getCell(2, i).getContents());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (isString2Date(sheet.getCell(3, i).getContents())) {
						sense.setPublishTime(new java.sql.Date(sdf.parse(sheet.getCell(3, i).getContents()).getTime()));
					}
					sense.setSource(sheet.getCell(4, i).getContents());
					sense.setSummary(sheet.getCell(5, i).getContents());
					sense.setText(sheet.getCell(6, i).getContents());
					sense.setTag(sheet.getCell(7, i).getContents());
					sense.setCreatorId(SecurityUtils.getUser().getId());
					scrapyGovPolicySenseService.insert(sense);
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
	@ApiOperation(value = "常识excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("政策常识采集");
			HSSFRow row = sheetElement.createRow(0);
			sheetElement.setDefaultColumnWidth(20);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("标题(必填)");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("关键词");
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("描述");
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("发文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("来源");
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("简介");
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("正文");
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue("标签");

			
			/**设置前200行日期单元格格式为yyyy-MM-dd格式*/
			for(int i=1;i<200;i++) {
				HSSFRow everyRow = sheetElement.createRow(i);
				HSSFCellStyle textStyle = workbook.createCellStyle();
				HSSFDataFormat format = workbook.createDataFormat();
				textStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
				everyRow.createCell(3).setCellStyle(textStyle);
			}
			
			
			rs.reset();
			// 自动识别
			rs.setContentType("multipart/form-data");
			String excelName = "scrapySense";
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


	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询采集常识")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicySenseService.selectAllScrapyGovPolicySense(new Query<>(params));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集常识")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovPolicySense scrapyGovPolicySense = scrapyGovPolicySenseService.selectById(id);
		return new R<>(scrapyGovPolicySense);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovPolicySense
	 * @return R
	 */
	@SysLog("新增采集常识")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集常识")
	public R save(@RequestBody ScrapyGovPolicySense scrapyGovPolicySense) {
		return new R<>(scrapyGovPolicySenseService.insert(scrapyGovPolicySense));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovPolicySense
	 * @return R
	 */
	@SysLog("更新采集常识")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集常识")
	public R update(@RequestBody ScrapyGovPolicySense scrapyGovPolicySense) {
		scrapyGovPolicySenseService.updateById(scrapyGovPolicySense);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 删除【逻辑删除】
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集常识")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集常识")
	public R delete(@PathVariable Integer id) {
		ScrapyGovPolicySense scrapyGovPolicySense = scrapyGovPolicySenseService.selectById(id);
		scrapyGovPolicySense.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovPolicySenseService.updateById(scrapyGovPolicySense));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集常识")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovPolicySense scrapyGovPolicySense = scrapyGovPolicySenseService.selectById(id);
			if (!StrUtil.equals(scrapyGovPolicySense.getDelFlag(), "2")) {
				scrapyGovPolicySense.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovPolicySenseService.updateById(scrapyGovPolicySense);
			}
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 获取被放入回收站的政策常识
	 *
	 * @return 被放入回收站的政策常识列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集常识")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicySenseService.getRecyclePage(new Query<>(params));
	}

	/**
	 * 彻底删除政策常识
	 *
	 * @param id 政策常识ID
	 */
	@SysLog("彻底删除政策常识")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除政策常识")
	public R recycleDelete(@PathVariable("id") Integer id) {
		scrapyGovPolicySenseService.deleteById(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 将政策常识从回收站还原
	 *
	 * @param id 政策常识ID
	 */
	@SysLog("还原政策常识")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原政策常识")
	public R recycleRecover(@PathVariable("id") Integer id) {
		scrapyGovPolicySenseService.recoverRecycle(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量彻底删除政策常识
	 *
	 * @param ids 政策常识ID集合
	 */
	@SysLog("批量彻底删除政策常识")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除政策常识")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicySenseService.deleteById(id);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量将政策常识从回收站还原
	 *
	 * @param ids 政策常识ID集合
	 */
	@SysLog("批量还原政策常识")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原政策常识")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		for (Integer id : ids) {
			scrapyGovPolicySenseService.recoverRecycle(id);
		}
		return new R<>(Boolean.TRUE);
	}

}
