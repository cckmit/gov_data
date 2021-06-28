package com.cloud.gov.theme.controller;


import java.util.HashMap;
import java.util.Map;

import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.theme.api.entity.WebUserFootprint;
import com.cloud.dips.theme.api.vo.WebUserFootprintVO;
import com.cloud.gov.theme.service.WebUserFootprintService;

import io.swagger.annotations.ApiOperation;


/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
@RestController
@RequestMapping("/webuserfootprint")
public class WebUserFootprintController {
    @Autowired
    private WebUserFootprintService webUserFootprintService;


    /**
    *  查询web端用户足迹列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    @ApiOperation(value = "查询web端用户足迹列表", notes = "查询web端用户足迹列表")
    public Page<WebUserFootprintVO> page(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
    	Integer userId = SecurityUtils.getUser().getId();
    	WebUserFootprint webUserFootprint = new WebUserFootprint();
    	webUserFootprint.setWebUserId(userId);
    	EntityWrapper<WebUserFootprint> entityWrapper = new EntityWrapper<>(webUserFootprint, "id,policy_id,policy_type,title,is_readed");
    	
      return  webUserFootprintService.selectPage(new Page(page,limit,"modified_time",false), entityWrapper);
    }


    /**
     * 根据足迹政策id和标志位mark查找对应的政策信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
	@SysLog(value = "根据足迹政策id和标志位mark查找对应的政策信息",role = EnumRole.WEB_TYE)
    public R info(@PathVariable("id") Long id){
			WebUserFootprint webUserFootprint = webUserFootprintService.selectById(id);
			return new R<>(webUserFootprint);
    }

    

    /**
     * 修改
     * @param webUserFootprint
     * @return R
     */
    @PutMapping
    public R update(@RequestBody WebUserFootprint webUserFootprint){
			webUserFootprintService.updateById(webUserFootprint);
      return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/deleteFootprint/{id}/{mark}")
    @ApiOperation(value = "删除用户足迹", notes = "删除用户足迹")
    public R delete(@PathVariable Long id,@PathVariable String mark){
    	Map<String, Object> columnMap = new HashMap<String, Object>();
    	Integer userId = SecurityUtils.getUser().getId();
    	columnMap.put("web_user_id", userId);
    	columnMap.put("policy_id", id);
    	columnMap.put("policy_type", mark);
      return new R<>(webUserFootprintService.deleteByMap(columnMap));
    }

}
