package com.cloud.gov.theme.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.feign.RemoteSortingDataService;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.theme.api.constant.ThemeConstant;
import com.cloud.dips.theme.api.dto.WebUserPushDTO;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.gov.theme.service.WebUserPushService;

import io.swagger.annotations.ApiOperation;


/**
 * 用户d订阅主题推送表
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
@RestController
@RequestMapping("/webuserpush")
public class WebUserPushController {
    @Autowired
    private WebUserPushService webUserPushService;
    
    @Autowired
    private RemoteSortingDataService remoteSortingDataService;
   
    public static Integer total;
    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    @ApiOperation(value = "订阅信息列表", notes = "订阅信息列表")
    public Page<SortDataVO> page(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,
    		@RequestParam(required =false, value= "theme") String theme,@RequestParam(required =false, value= "mark") String mark) {
    	Integer userId = SecurityUtils.getUser().getId();
    	SortingDataDTO sortingDataDTO = webUserPushService.findThemeByUser(theme,mark,userId);
    	if (null ==  sortingDataDTO) {
    		Page<SortDataVO> nullPage = new Page<SortDataVO>();
    		return nullPage;
    	}
    	String jsonString = JSON.toJSONString(sortingDataDTO);
    	Page<SortDataVO> pageTotal = remoteSortingDataService.queryPush(jsonString,page,limit,sortingDataDTO.getMark());
    	long total = pageTotal.getTotal();
    	this.total = (int)total;
    	return pageTotal;
    }


    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
			WebUserPush webUserPush = webUserPushService.selectById(id);
			return new R<>(webUserPush);
    }

    /**
     * 保存
     * @param webUserPush
     * @return R
     */
    @PostMapping

    public R save(@RequestBody WebUserPush webUserPush){
			return new R<>(webUserPushService.insert(webUserPush));
    }

    /**
     * 修改推送信息的阅读状态
     * @param webUserPush
     * @return R
     */
    @PutMapping("/updatePush")
    @ApiOperation(value = "修改推送信息的阅读状态", notes = "修改推送信息的阅读状态")
    public R update(@RequestBody WebUserPush webUserPush){
//    		webUserPush.setStatus(ThemeConstant.IS_DO);
//			webUserPushService.updateById(webUserPush);
      return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/deletePush/{id}")
    @ApiOperation(value = "删除推送信息", notes = "删除推送信息")
    public R delete(@PathVariable Long id){
      return new R<>(webUserPushService.deleteById(id));
    }

}
