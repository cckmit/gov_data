package com.cloud.dips.raus.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.vo.GeneralApiVO;
import com.cloud.dips.raus.service.GovPolicyGeneralApiService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;


/**
 * 通用政策模型
 *
 * @author johan
 * @date 2019-02-19
 */
@RestController
@RequestMapping("/generalPolicy")
@AllArgsConstructor
public class GovPolicyGeneralApiController {

    private final GovPolicyGeneralApiService govPolicyGeneralApiService;

    @PostMapping("/api/page")
    @ApiOperation(value = "通用api分页查询", notes = "通用api分页查询", httpMethod = "POST")
    public Page<GeneralApiVO> page(@RequestParam Map<String,Object> params,String theme,String queryTime) {
//    	Map<String,Object> params = new HashMap<String,Object>();
//    	params.put("limit", limit);
//    	params.put("page", page);
//        return govPolicyGeneralApiService.selectGeneralApi(new Query<>(params),theme,queryTime);
    	return null;
    }
    
}
