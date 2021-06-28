package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyExplain;
import com.cloud.dips.gov.service.GovPolicyExplainService;
import com.cloud.dips.gov.service.ScrapyGovPolicyExplainService;
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
@RequestMapping("/scrapy/explain")
@AllArgsConstructor
public class ScrapyGovExplainController {

	private final ScrapyGovPolicyExplainService scrapyGovPolicyExplainService;
	private final GovPolicyExplainService govPolicyExplainService;

	/**
	 * 复制入库
	 */
	@SysLog("解读复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "解读复制入库")
	public R copy(@PathVariable Integer id) {
		ScrapyGovPolicyExplain scrapyGovPolicyExplain = scrapyGovPolicyExplainService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govPolicyExplainService.repeat(scrapyGovPolicyExplain.getTitle()) || "2".equals(scrapyGovPolicyExplain.getDelFlag())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(scrapyGovPolicyExplain.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(scrapyGovPolicyExplain.getDelFlag())) {
			scrapyGovPolicyExplainService.copy(scrapyGovPolicyExplain);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("解读批量复制入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "解读批量复制入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyExplain scrapyGovPolicyExplain = scrapyGovPolicyExplainService.selectById(id);
			if (!govPolicyExplainService.repeat(scrapyGovPolicyExplain.getTitle())) {
				scrapyGovPolicyExplain.setDelFlag("3");
				scrapyGovPolicyExplainService.updateById(scrapyGovPolicyExplain);
				countFalse= countFalse+1;
				sb.append(scrapyGovPolicyExplain.getTitle()+"&");
				continue;
			}
			if ("2".equals(scrapyGovPolicyExplain.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(scrapyGovPolicyExplain.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(scrapyGovPolicyExplain.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(scrapyGovPolicyExplain.getDelFlag())) {
				scrapyGovPolicyExplainService.copy(scrapyGovPolicyExplain);
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
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询采集解读")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyExplainService.selectAllPage(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集解读")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovPolicyExplain scrapyGovPolicyExplain = scrapyGovPolicyExplainService.selectById(id);
		return new R<>(scrapyGovPolicyExplain);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovPolicyExplain
	 * @return R
	 */
	@SysLog("新增采集解读")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集解读")
	public R save(@RequestBody ScrapyGovPolicyExplain scrapyGovPolicyExplain) {
		return new R<>(scrapyGovPolicyExplainService.insert(scrapyGovPolicyExplain));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovPolicyExplain
	 * @return R
	 */
	@SysLog("更新采集解读")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集解读")
	public R update(@RequestBody ScrapyGovPolicyExplain scrapyGovPolicyExplain) {
		scrapyGovPolicyExplainService.updateById(scrapyGovPolicyExplain);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集解读")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集解读")
	public R delete(@PathVariable Integer id) {
		ScrapyGovPolicyExplain scrapyGovPolicyExplain = scrapyGovPolicyExplainService.selectById(id);
		scrapyGovPolicyExplain.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovPolicyExplainService.updateById(scrapyGovPolicyExplain));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量逻辑删除采集解读")
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集解读")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovPolicyExplain scrapyGovPolicyExplain = scrapyGovPolicyExplainService.selectById(id);
			if (!StrUtil.equals(scrapyGovPolicyExplain.getDelFlag(), "2")) {
				scrapyGovPolicyExplain.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovPolicyExplainService.updateById(scrapyGovPolicyExplain);
			}
		}
		return new R<>(Boolean.TRUE);
	}

	@SysLog("excel导入采集解读")
	@PostMapping("/import")
	@ApiOperation(value = "excel导入采集解读")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		int rowsNum;
		try {
			// 创建文件输入流
			InputStream is = file.getInputStream();
			// 读取excel内容
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovPolicyExplain explain = new ScrapyGovPolicyExplain();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					explain.setTitle(sheet.getCell(0, i).getContents());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (isString2Date(sheet.getCell(1, i).getContents())) {
						explain.setPublishTime(new java.sql.Date(sdf.parse(sheet.getCell(1, i).getContents()).getTime()));
					}
					explain.setSource(sheet.getCell(2, i).getContents());
					explain.setLevel(sheet.getCell(3, i).getContents());
					explain.setMain(sheet.getCell(4, i).getContents());
					explain.setSummary(sheet.getCell(5, i).getContents());
					explain.setText(sheet.getCell(6, i).getContents());
					explain.setUrl(sheet.getCell(7, i).getContents());
					explain.setIndustry(sheet.getCell(8, i).getContents());
					explain.setTag(sheet.getCell(9, i).getContents());
					explain.setTheme(sheet.getCell(10, i).getContents());
					scrapyGovPolicyExplainService.insert(explain);
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
	@ApiOperation(value = "采集解读excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("政策解读采集");
			HSSFRow row = sheetElement.createRow(0);
			sheetElement.setDefaultColumnWidth(20);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("标题(必填)");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("解读时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("来源");
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("层级 单选" + getString("POLICY_LEVEL"));
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("主体 单选" + getString("POLICY_MAIN"));
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("摘要");
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("正文");
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue("原文链接");
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue("政策解读行业 多选" + getString("POLICY_INDUSTRY"));
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue("标签");
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue("政策解读主题 多选" + getString("POLICY_THEME"));
			
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
			String excelName = "scrapyExplain";
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

	private String getString(String number) {
		List<String> list = scrapyGovPolicyExplainService.listDictByNumber(number);
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
	 * 获取被放入回收站的政策解读
	 *
	 * @return 被放入回收站的政策解读列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集解读")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyExplainService.getRecyclePage(new Query<>(params));
	}

	/**
	 * 彻底删除政策解读
	 *
	 * @param id 政策解读ID
	 */
	@SysLog("彻底删除采集解读")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除采集解读")
	public R recycleDelete(@PathVariable("id") Integer id) {
		return new R<>(scrapyGovPolicyExplainService.deleteRecycle(id));
	}

	/**
	 * 将政策解读从回收站还原
	 *
	 * @param id 政策解读ID
	 */
	@SysLog("还原采集解读")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原采集解读")
	public R recycleRecover(@PathVariable("id") Integer id) {
		return new R<>(scrapyGovPolicyExplainService.recycleRecover(id));
	}

	/**
	 * 批量彻底删除政策解读
	 *
	 * @param ids 政策解读ID集合
	 */
	@SysLog("批量彻底删除采集解读")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除采集解读")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		return new R<>(scrapyGovPolicyExplainService.batchDeleteRecycle(ids));
	}

	/**
	 * 批量将政策解读从回收站还原
	 *
	 * @param ids 政策解读ID集合
	 */
	@SysLog("批量还原采集解读")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原采集解读")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		return new R<>(scrapyGovPolicyExplainService.batchRecycleRecover(ids));
	}

}
