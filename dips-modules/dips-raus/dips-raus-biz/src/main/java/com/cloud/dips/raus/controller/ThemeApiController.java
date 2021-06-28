package com.cloud.dips.raus.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.aop.UserLogRecord;
import com.cloud.dips.raus.api.dto.ThemeApiDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.vo.GeneralApiVO;
import com.cloud.dips.raus.service.PreposeUserService;


/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@RestController
@RequestMapping("/dataSource")
public class ThemeApiController {
    @Autowired
    private PreposeUserService preposeUserService;
    

    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
      return  preposeUserService.selectPage(new Query<>(params), new EntityWrapper<PreposeUser>());
    }

    /**
     * 用户主题api调用
     */
    @UserLogRecord(description="用户调用主题api")
    @PostMapping("/theme/api")
    public Page<GeneralApiVO> themeApi(@RequestParam("userName") String userName,@RequestParam("token") String token,
    		@RequestParam("page") Integer page,@RequestParam("queryTime") String queryTime) {
    	ThemeApiDTO themeApiDTO = new ThemeApiDTO();
    	themeApiDTO.setUserName(userName);
    	themeApiDTO.setToken(token);
    	themeApiDTO.setPage(page);
    	themeApiDTO.setQueryTime(queryTime);
    	Page<GeneralApiVO> themeApi = preposeUserService.invokingThemeApi(themeApiDTO);
    	return themeApi;
      }
}
