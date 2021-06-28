package com.cloud.gov.theme.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.feign.*;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.theme.api.entity.WebUserCollection;
import com.cloud.dips.theme.api.entity.WebUserTag;
import com.cloud.dips.theme.api.vo.WebUserTagVO;
import com.cloud.gov.theme.service.WebUserCollectionService;
import com.cloud.gov.theme.service.WebUserFootprintService;
import com.cloud.gov.theme.service.WebUserTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 *
 * @author johan
* @Date 2018年12月10日
* @Company 佛山司马钱技术有限公司
* @description 用户收藏表
 */
@RestController
@RequestMapping("/webusertag")
public class WebUserTagController {

	@Autowired
	private WebUserTagService webUserTagService;


	@PostMapping("inserTag")
	@ApiOperation(value = "用户添加个人标签", notes = "保存字典的值", httpMethod = "POST" )
	@SysLog(value = "用户添加个人标签",role = EnumRole.WEB_TYE)
	public R<Boolean>  inserTag(@RequestBody WebUserTag webUserTag){
         webUserTag.setUserId(SecurityUtils.getUser().getId());
          webUserTagService.insert(webUserTag);
          return  new R<>(Boolean.TRUE);
	}

//	@GetMapping("/getTag")
//	@ApiOperation(value = "查询自己个人标签",httpMethod = "GET")
//	public R<WebUserTagVO> selectById(){
//        WebUserTag webUserTag=new WebUserTag();
//        webUserTag.setUserId(SecurityUtils.getUser().getId());
//		webUserTag = webUserTagService.selectById(webUserTag);
//	}



}
