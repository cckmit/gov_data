package com.cloud.dips.gov.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;
import com.cloud.dips.gov.service.*;
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
@RequestMapping("/scrapy/general")
@AllArgsConstructor
public class ScrapyGovGeneralController {

	private final ScrapyGovPolicyGeneralService scrapyGovPolicyGeneralService;
	private final GovPolicyGeneralService govPolicyGeneralService;
	private final GovPolicySenseService govPolicySenseService;
	private final GovPolicyExplainService govPolicyExplainService;
	private final GovPolicyDeclareService govPolicyDeclareService;
	private final GovOrganizationService govOrganizationService;
	private final GovInformationService govInformationService;

	/**
	 * 复制保存
	 */
	@SysLog("通用复制入库")
	@PostMapping("/copy/{id}")
	@ApiOperation(value = "通用复制入库")
	public R copySave(@PathVariable Integer id) {
		ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
		// 如果标题存在就直接返回false
		if (!govPolicyGeneralService.repeat(sGeneral.getTitle()) || "2".equals(sGeneral.getDelFlag())) {
			return new R<>(Boolean.FALSE);
		}
		if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
			scrapyGovPolicyGeneralService.copySave(sGeneral);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量复制入库
	 */
	@SysLog("通用复制批量入库")
	@PostMapping("/copyAll")
	@ApiOperation(value = "通用复制批量入库")
	public R copyAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			// 如果标题存在就直接返回false
			if (!govPolicyGeneralService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copySave(sGeneral);
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
	@ApiOperation(value = "分页查询采集通用")
	public Page page(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyGeneralService.selectAllPage(new Query<>(params));
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查询采集通用")
	public R info(@PathVariable("id") Integer id) {
		ScrapyGovPolicyGeneral scrapyGovPolicyGeneral = scrapyGovPolicyGeneralService.selectById(id);
		return new R<>(scrapyGovPolicyGeneral);
	}

	/**
	 * 保存
	 *
	 * @param scrapyGovPolicyGeneral
	 * @return R
	 */
	@SysLog("新增采集通用")
	@PostMapping("/create")
	@ApiOperation(value = "新增采集通用")
	public R save(@RequestBody ScrapyGovPolicyGeneral scrapyGovPolicyGeneral) {
		return new R<>(scrapyGovPolicyGeneralService.insert(scrapyGovPolicyGeneral));
	}

	/**
	 * 修改
	 *
	 * @param scrapyGovPolicyGeneral
	 * @return R
	 */
	@SysLog("更新采集通用")
	@PostMapping("/update")
	@ApiOperation(value = "更新采集通用")
	public R update(@RequestBody ScrapyGovPolicyGeneral scrapyGovPolicyGeneral) {
		scrapyGovPolicyGeneralService.updateById(scrapyGovPolicyGeneral);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("逻辑删除采集通用")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "逻辑删除采集通用")
	public R delete(@PathVariable Integer id) {
		ScrapyGovPolicyGeneral scrapyGovPolicyGeneral = scrapyGovPolicyGeneralService.selectById(id);
		scrapyGovPolicyGeneral.setDelFlag(CommonConstant.STATUS_DEL);
		return new R<>(scrapyGovPolicyGeneralService.updateById(scrapyGovPolicyGeneral));
	}

	/**
	 * 批量逻辑删除
	 *
	 * @param ids
	 * @return R
	 */
	@SysLog("批量逻辑删除采集通用")
	@DeleteMapping("/batch")
	@ApiOperation(value = "批量逻辑删除采集通用")
	public R batchDelete(@RequestBody List<Integer> ids) {
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral scrapyGovPolicyGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!StrUtil.equals(scrapyGovPolicyGeneral.getDelFlag(), "2")) {
				scrapyGovPolicyGeneral.setDelFlag(CommonConstant.STATUS_DEL);
				scrapyGovPolicyGeneralService.updateById(scrapyGovPolicyGeneral);
			}
		}
		return new R<>(Boolean.TRUE);
	}
	
	/**
	 * excel导入
	 */
	@SysLog("excel导入采集通用")
	@PostMapping("/import")
	@ApiOperation(value = "excel导入采集通用")
	public R importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		int rowsNum;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			rowsNum = sheet.getRows();

			for (int i = 1; i < rowsNum; i++) {
				ScrapyGovPolicyGeneral general = new ScrapyGovPolicyGeneral();
				if (!"".equals(sheet.getCell(0, i).getContents())) {
					general.setTitle(sheet.getCell(0, i).getContents());
					general.setSummary(sheet.getCell(1, i).getContents());
					general.setSource(sheet.getCell(2, i).getContents());
					general.setReference(sheet.getCell(3, i).getContents());
					general.setIssue(sheet.getCell(4, i).getContents());
					general.setStyle(sheet.getCell(5, i).getContents());
					general.setLevel(sheet.getCell(6, i).getContents());
					general.setTimeliness(sheet.getCell(7, i).getContents());
					general.setStage(sheet.getCell(8, i).getContents());
					general.setFormality(sheet.getCell(9, i).getContents());
//					general.setEffective(sheet.getCell(10, i).getContents());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					if (isString2Date(sheet.getCell(11, i).getContents())) {
//						general.setWriteTime(new java.sql.Date(sdf.parse(sheet.getCell(11, i).getContents()).getTime()));
//					}
					if (isString2Date(sheet.getCell(12, i).getContents())) {
						general.setPublishTime(new java.sql.Date(sdf.parse(sheet.getCell(12, i).getContents()).getTime()));
					}
					if (isString2Date(sheet.getCell(13, i).getContents())) {
						general.setEffectTime(new java.sql.Date(sdf.parse(sheet.getCell(13, i).getContents()).getTime()));
					}
					if (isString2Date(sheet.getCell(14, i).getContents())) {
						general.setInvalidTime(new java.sql.Date(sdf.parse(sheet.getCell(14, i).getContents()).getTime()));
					}
					general.setText(sheet.getCell(15, i).getContents());
					general.setTag(sheet.getCell(16, i).getContents());
					general.setTarget(sheet.getCell(17, i).getContents());
					general.setTheme(sheet.getCell(18, i).getContents());
					general.setFund(sheet.getCell(19, i).getContents());
					general.setIndustry(sheet.getCell(20, i).getContents());
					general.setUrl(sheet.getCell(21, i).getContents());
					general.setDelFlag("0");
					general.setCreatorId(SecurityUtils.getUser().getId());
					scrapyGovPolicyGeneralService.insert(general);
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
	@ApiOperation(value = "采集通用excel模版下载")
	public void model(HttpServletResponse rs) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheetElement = workbook.createSheet("通用政策采集");
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
			cell7.setCellValue("时效性 单选" + getString("IS_USEFUL"));
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue("政策阶段状态 单选" + getString("POLICY_STAGE"));
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue("发文形式 单选" + getString("POLICY_SEND"));
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue("有效年限");
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue("成文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue("发文时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue("生效时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue("失效时间(文本格式：yyyy-MM-dd)");
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue("正文");
			HSSFCell cell16 = row.createCell(16);
			cell16.setCellValue("标签");
			HSSFCell cell17 = row.createCell(17);
			cell17.setCellValue("通用对象 多选" + getString("DECLARE_TARGET"));
			HSSFCell cell18 = row.createCell(18);
			cell18.setCellValue("主题 多选" + getString("POLICY_THEME"));
			HSSFCell cell19 = row.createCell(19);
			cell19.setCellValue("适用规模 多选" + getString("POLICY_SCALE"));
			HSSFCell cell20 = row.createCell(20);
			cell20.setCellValue("行业 多选" + getString("POLICY_INDUSTRY"));
			HSSFCell cell21 = row.createCell(21);
			cell21.setCellValue("原文链接");
			
			/**设置前200行日期单元格格式为yyyy-MM-dd格式*/
			for(int i=1;i<200;i++) {
				HSSFRow everyRow = sheetElement.createRow(i);
				HSSFCellStyle textStyle = workbook.createCellStyle();
				HSSFDataFormat format = workbook.createDataFormat();
				textStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
				everyRow.createCell(11).setCellStyle(textStyle);
				everyRow.createCell(12).setCellStyle(textStyle);
				everyRow.createCell(13).setCellStyle(textStyle);
				everyRow.createCell(14).setCellStyle(textStyle);
			}

			
			rs.reset();
			// 自动识别
			rs.setContentType("multipart/form-data");
			String excelName = "scrapyGeneral";
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
		List<String> list = scrapyGovPolicyGeneralService.listDictByNumber(number);
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
	 * 批量复制入申报库
	 */
	@SysLog("采集通用批量复制入申报库")
	@PostMapping("/declare/copyAll")
	@ApiOperation(value = "采集通用批量复制入申报库")
	public R copyDeclareAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!govPolicyDeclareService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copyDeclare(sGeneral);
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
	 * 批量复制入解读库
	 */
	@SysLog("采集通用批量复制入解读库")
	@PostMapping("/explain/copyAll")
	@ApiOperation(value = "采集通用批量复制入解读库")
	public R copyExplainAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!govPolicyExplainService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse=countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copyExplain(sGeneral);
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
	 * 批量复制入资讯库
	 */
	@SysLog("采集通用批量复制入资讯库")
	@PostMapping("/information/copyAll")
	@ApiOperation(value = "采集通用批量复制入资讯库")
	public R copyInformationAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!govInformationService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse=countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copyInformation(sGeneral);
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
	 * 批量复制入机构库
	 */
	@SysLog("采集通用批量复制入机构库")
	@PostMapping("/organization/copyAll")
	@ApiOperation(value = "采集通用批量复制入机构库")
	public R copyOrganizationAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!govOrganizationService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if (CommonConstant.STATUS_NORMAL.equals(sGeneral.getDelFlag()) || CommonConstant.STATUS_IMPORT_FALSE.equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copyOrganization(sGeneral);
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
	 * 批量复制入常识库
	 */
	@SysLog("采集通用批量复制入常识库")
	@PostMapping("/sense/copyAll")
	@ApiOperation(value = "采集通用批量复制入常识库")
	public R copySenseAll(@RequestBody List<Integer> ids) {
		Integer countSuccess=0;
		Integer countFalse=0;
		StringBuffer sb=new StringBuffer();
		for (Integer id : ids) {
			ScrapyGovPolicyGeneral sGeneral = scrapyGovPolicyGeneralService.selectById(id);
			if (!govPolicySenseService.repeat(sGeneral.getTitle())) {
				sGeneral.setDelFlag("3");
				scrapyGovPolicyGeneralService.updateById(sGeneral);
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("2".equals(sGeneral.getDelFlag())) {
				countFalse= countFalse+1;
				sb.append(sGeneral.getTitle()+"&");
				continue;
			}
			if ("0".equals(sGeneral.getDelFlag())) {
				scrapyGovPolicyGeneralService.copySense(sGeneral);
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
	 * 获取被放入回收站的通用政策模型
	 *
	 * @return 被放入回收站的通用政策模型列表
	 */
	@GetMapping("/recycle/page")
	@ApiOperation(value = "分页查询回收站采集通用")
	public Page recyclePage(@RequestParam Map<String, Object> params) {
		return scrapyGovPolicyGeneralService.getRecyclePage(new Query<>(params));

	}

	/**
	 * 彻底删除通用政策模型
	 *
	 * @param id 通用政策模型ID
	 */
	@SysLog("彻底删除采集通用")
	@DeleteMapping("/recycle/{id}")
	@ApiOperation(value = "彻底删除采集通用")
	public R recycleDelete(@PathVariable("id") Integer id) {
		scrapyGovPolicyGeneralService.deleteRecycle(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 将通用政策模型从回收站还原
	 *
	 * @param id 通用政策模型ID
	 */
	@SysLog("还原采集通用")
	@PostMapping("/recycle/{id}")
	@ApiOperation(value = "还原采集通用")
	public R recycleRecover(@PathVariable("id") Integer id) {
		scrapyGovPolicyGeneralService.recycleRecover(id);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量彻底删除通用政策模型
	 *
	 * @param ids 通用政策模型ID集合
	 */
	@SysLog("彻底批量删除采集通用")
	@DeleteMapping("/recycle/batch")
	@ApiOperation(value = "批量彻底删除采集通用")
	public R recycleBatchDelete(@RequestBody Integer[] ids) {
		scrapyGovPolicyGeneralService.batchDeleteRecycle(ids);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量将通用政策模型从回收站还原
	 *
	 * @param ids 通用政策模型ID集合
	 */
	@SysLog("批量还原采集通用")
	@PostMapping("/recycle/batch")
	@ApiOperation(value = "批量还原采集通用")
	public R recycleBatchRecover(@RequestBody Integer[] ids) {
		scrapyGovPolicyGeneralService.batchRecycleRecover(ids);
		return new R<>(Boolean.TRUE);
	}
}
