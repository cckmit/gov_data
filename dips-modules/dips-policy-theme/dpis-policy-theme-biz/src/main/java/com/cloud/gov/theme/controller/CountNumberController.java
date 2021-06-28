package com.cloud.gov.theme.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.common.core.util.R;
import com.cloud.gov.theme.service.CountNumberService;

import io.swagger.annotations.ApiOperation;


/**
 *	 统计收藏，足迹，推送信息
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
@RestController
@RequestMapping("/countNumber")
public class CountNumberController {
    @Autowired
    private CountNumberService countNumberService;

    /**
         *  列表
    * @param params
    * @return
    */
    @GetMapping("/total")
    @ApiOperation(value = "统计足迹、收藏、订阅信息数量", notes = "统计足迹、收藏、订阅信息数量")
    public List<Map<String, Integer>> queryCount() {
    	List<Map<String, Integer>> count = countNumberService.queryCount();
      return   count;
    }

}
