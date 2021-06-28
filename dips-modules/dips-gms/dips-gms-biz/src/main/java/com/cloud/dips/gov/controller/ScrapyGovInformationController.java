package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.ScrapyGovInformation;
import com.cloud.dips.gov.service.GovInformationService;
import com.cloud.dips.gov.service.ScrapyGovInformationService;
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
import java.sql.Date;;

/**
 * 政策资讯模型
 *
 * @author CUI.CAN
 * @date 2018-10-24 15:27:13
 */
@RestController
@RequestMapping("/scrapy/information")
@AllArgsConstructor
public class ScrapyGovInformationController {

	private final ScrapyGovInformationService scrapyGovInformationService;
	private final GovInformationService govInformationService;

	/**
	 * 复制入库
	 */
	@SysLog("资讯复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "资讯复制入库")
	public R copy(@PathVariable Integer id) {
		ScrapyGovInformation sInformation = scrapyGovInformationService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govInformationService.repeat(sInformation.getTitle()) || "2".equals(sInformation.getDelFlag())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(sInformation.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sInformation.getDelFlag())) {
			scrapyGovInformationService.copy(sInformation);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("资讯批量复制入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "资讯批量复制入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovInformation sInformation = scrapyGovInformationService.selectById(id);
			if (!govInformationService.repeat(sInformation.getTitle())) {
				sInformation.setDelFlag("3");
				scrapyGovInformationService.updateById(sInformation);
				countFalse= countFalse+1;
				sb.append(sInformation.getTitle()+"&");
			}
			if (CommonConstant.STATUS_NORMAL.equals(sInformation.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sInformation.getDelFlag())) {
				scrapyGovInformationService.copy(sInformation);
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
	 * 列表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询采集资讯")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovInformationService.selectAllPage(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集资讯")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovInformation scrapyGovInformation = scrapyGovInformationService.selectById(id);
		return new R<>(scrapyGovInformation);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovInformation
	 * @return R
	 */
	@SysLog("新增采集资讯")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集资讯")
	public R save(@RequestBody ScrapyGovInformation scrapyGovInformation) {
		return new R<>(scrapyGovInformationService.insert(scrapyGovInformation));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovInformation
	 * @return R
	 */
	@SysLog("更新采集资讯")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集资讯")
	public R update(@RequestBody ScrapyGovInformation scrapyGovInformation) {
		scrapyGovInformationService.updateById(scrapyGovInformation);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集资讯")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集资讯")
	public R delete(@PathVariable Integer id) {
		ScrapyGovInformation scrapyGovInformation = scrapyGovInformationService.selectById(id);
		scrapyGovInformation.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovInformationService.updateById(scrapyGovInformation));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量逻辑删除采集资讯")
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集资讯")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovInformation scrapyGovInformation = scrapyGovInformationService.selectById(id);
			if (!StrUtil.equals(scrapyGovInformation.getDelFlag(), "2")) {
				scrapyGovInformation.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovInformationService.updateById(scrapyGovInformation);
			}
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * excel导入
	 */
	@SysLog("excel导入采集资讯")
	@PostMapping("/import")
	@ApiOperation(value = "excel导入采集资讯")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		int rowsNum;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovInformation information = new ScrapyGovInformation();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					information.setTitle(sheet.getCell(0, i).getContents());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (isString2Date(sheet.getCell(1, i).getContents())) {
						information.setPublishTime(new java.sql.Date(sdf.parse(sheet.getCell(1, i).getContents()).getTime()));
					}
					information.setSummary(sheet.getCell(2, i).getContents());
					information.setSource(sheet.getCell(3, i).getContents());
					information.setText(sheet.getCell(4, i).getContents());
					information.setAuthor(sheet.getCell(5, i).getContents());
					information.setUrl(sheet.getCell(6, i).getContents());
					information.setTag(sheet.getCell(7, i).getContents());
					information.setCreatorId(SecurityUtils.getUser().getId());
					scrapyGovInformationService.insert(information);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new R<>(Boolean.TRUE);
	}

	@GetMapping("/download")
	@ApiOperation(value = "采集资讯excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("政策资讯采集");
			HSSFRow row = sheetElement.createRow(0);
			sheetElement.setDefaultColumnWidth(20);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("标题(必填)");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("成文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("摘要");
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("来源");
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("正文");
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("作者");
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("原文链接");
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue("标签");
			
			
			/**设置前200行日期单元格格式为yyyy-MM-dd格式*/
			for(int i=1;i<200;i++) {
				HSSFRow everyRow = sheetElement.createRow(i);
				HSSFCellStyle textStyle = workbook.createCellStyle();
				HSSFDataFormat format = workbook.createDataFormat();
				textStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
				everyRow.createCell(1).setCellStyle(textStyle);
			}
			
			rs.reset();
			// 自动识别
			rs.setContentType("multipart/form-data");
			String excelName = "scrapyInformation";
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
	 * 获取被放入回收站的政策资讯模型
	 *
	 * @return 被放入回收站的政策资讯模型列表
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集资讯")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovInformationService.getRecyclePage(new Query<>(params));
	}

	/**
	 * 彻底删除政策资讯模型
	 *
	 * @param id 政策资讯模型ID
	 */
	@SysLog("彻底删除采集资讯")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除采集资讯")
	public R recycleDelete(@PathVariable("id") Integer id) {
		scrapyGovInformationService.deleteRecycle(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 将政策资讯模型从回收站还原
	 *
	 * @param id 政策资讯模型ID
	 */
	@SysLog("还原采集资讯")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原采集资讯")
	public R recycleRecover(@PathVariable("id") Integer id) {
		scrapyGovInformationService.recycleRecover(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量彻底删除政策资讯模型
	 *
	 * @param ids 政策资讯模型ID集合
	 */
	@SysLog("批量彻底删除采集资讯")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除采集资讯")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		scrapyGovInformationService.batchDeleteRecycle(ids);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量将政策资讯模型从回收站还原
	 *
	 * @param ids 政策资讯模型ID集合
	 */
	@SysLog("批量还原采集资讯")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原采集资讯")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		scrapyGovInformationService.batchRecycleRecover(ids);
		return new R<>(Boolean.TRUE);
	}

}
