package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.ScrapyGovOrganization;
import com.cloud.dips.gov.service.GovOrganizationService;
import com.cloud.dips.gov.service.ScrapyGovOrganizationService;
import io.swagger.annotations.ApiOperation;
import jxl.Sheet;
import jxl.Workbook;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 机构模型
 *
 * @author BlackR
 * @date 2018-10-24 14:50:37
 */
@RestController
@RequestMapping("/scrapy/organization")
@AllArgsConstructor
public class ScrapyGovOrganizationController {

	private final ScrapyGovOrganizationService scrapyGovOrganizationService;
	private final GovOrganizationService govOrganizationService;

	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页查询采集机构")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovOrganizationService.selectOrganizationPage(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集机构")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovOrganization scrapyGovOrganization = scrapyGovOrganizationService.selectById(id);
		return new R<>(scrapyGovOrganization);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovOrganization
	 * @return R
	 */
	@SysLog("新增采集机构")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集机构")
	public R save(@RequestBody ScrapyGovOrganization scrapyGovOrganization) {
		return new R<>(scrapyGovOrganizationService.insert(scrapyGovOrganization));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovOrganization
	 * @return R
	 */
	@SysLog("更新采集机构")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集机构")
	public R update(@RequestBody ScrapyGovOrganization scrapyGovOrganization) {
		scrapyGovOrganizationService.updateById(scrapyGovOrganization);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集机构")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集机构")
	public R delete(@PathVariable Integer id) {
		ScrapyGovOrganization scrapyGovOrganization = scrapyGovOrganizationService.selectById(id);
		scrapyGovOrganization.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovOrganizationService.updateById(scrapyGovOrganization));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量逻辑删除采集机构")
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集机构")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovOrganization scrapyGovOrganization = scrapyGovOrganizationService.selectById(id);
			if (!StrUtil.equals(scrapyGovOrganization.getDelFlag(), "2")) {
				scrapyGovOrganization.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovOrganizationService.updateById(scrapyGovOrganization);
			}
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 复制入库
	 */
	@SysLog("采集机构复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "采集机构复制入库")
	public R copy(@PathVariable Integer id) {
		ScrapyGovOrganization scrapyGovOrganization = scrapyGovOrganizationService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govOrganizationService.repeat(scrapyGovOrganization.getName())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(scrapyGovOrganization.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(scrapyGovOrganization.getDelFlag())) {
			scrapyGovOrganizationService.copy(scrapyGovOrganization);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("采集机构批量复制入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "采集机构批量复制入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovOrganization scrapyGovOrganization = scrapyGovOrganizationService.selectById(id);
			if (!govOrganizationService.repeat(scrapyGovOrganization.getName())) {
				scrapyGovOrganization.setDelFlag("3");
				scrapyGovOrganizationService.updateById(scrapyGovOrganization);
				countFalse=countFalse++;
				sb.append(scrapyGovOrganization.getName()+"&");
			}
			if (CommonConstant.STATUS_NORMAL.equals(scrapyGovOrganization.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(scrapyGovOrganization.getDelFlag())) {
				scrapyGovOrganizationService.copy(scrapyGovOrganization);
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
	@SysLog("excel导入采集机构")
	@PostMapping("/import")
	@ApiOperation(value = "excel导入采集机构")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		int rowsNum;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovOrganization scrapyGovOrganization = new ScrapyGovOrganization();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					scrapyGovOrganization.setName(sheet.getCell(0, i).getContents());
					scrapyGovOrganization.setLevel(sheet.getCell(1, i).getContents());
					scrapyGovOrganization.setClassification(sheet.getCell(2, i).getContents());
					scrapyGovOrganization.setTag(sheet.getCell(3, i).getContents());
					scrapyGovOrganization.setUrl(sheet.getCell(4, i).getContents());
					scrapyGovOrganization.setIntroduce(sheet.getCell(5, i).getContents());
					scrapyGovOrganization.setAddress(sheet.getCell(6, i).getContents());
					scrapyGovOrganization.setCreatorId(SecurityUtils.getUser().getId());
					scrapyGovOrganizationService.insert(scrapyGovOrganization);
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
	@ApiOperation(value = "采集机构excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("机构采集");
			HSSFRow row = sheetElement.createRow(0);
			sheetElement.setDefaultColumnWidth(20);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("机构名称");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("机构层级 单选" + getString("POLICY_LEVEL"));
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("机构分类 单选" + getString("ORGANIZATION_CLASSIFICATION"));
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("标签");
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("机构网址");
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("机构介绍");
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("机构地址");

			rs.reset();
			// 自动识别
			rs.setContentType("multipart/form-data");
			String excelName = "scrapyOrganization";
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
		List<String> list = scrapyGovOrganizationService.listDictByNumber(number);
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
	 * 获取被放入回收站的机构模型
	 *
	 * @return 被放入回收站的机构模型列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集机构")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovOrganizationService.getRecyclePage(new Query<>(params));
	}

	/**
	 * 彻底删除机构模型
	 *
	 * @param id 机构模型ID
	 */
	@SysLog("彻底删除采集机构")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除采集机构")
	public R recycleDelete(@PathVariable("id") Integer id) {
		scrapyGovOrganizationService.deleteRecycle(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 将机构模型从回收站还原
	 *
	 * @param id 机构模型ID
	 */
	@SysLog("还原采集机构")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原采集机构")
	public R recycleRecover(@PathVariable("id") Integer id) {
		scrapyGovOrganizationService.recycleRecover(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量彻底删除机构模型
	 *
	 * @param ids 机构模型ID集合
	 */
	@SysLog("批量彻底删除采集机构")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除采集机构")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		scrapyGovOrganizationService.batchDeleteRecycle(ids);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量将机构模型从回收站还原
	 *
	 * @param ids 机构模型ID集合
	 */
	@SysLog("批量还原采集机构")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原采集机构")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		scrapyGovOrganizationService.batchRecycleRecover(ids);
		return new R<>(Boolean.TRUE);
	}

}
