package com.cloud.dips.raus.controller;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.raus.aop.UserLogRecord;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.entity.UserApplication;
import com.cloud.dips.raus.service.UserApplicationService;


/**
 * 
 *
 * @author johan
 * @date 2019-02-17 08:45:31
 */
@RestController
@RequestMapping("/userApplication")
public class UserApplicationController {
    @Autowired
    private UserApplicationService userApplicationService;
    

    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
    	
      return  userApplicationService.selectApplicationPage(new Query<>(params));
    }


    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
    	UserApplication preposeUser = userApplicationService.selectById(id);
			return new R<>(preposeUser);
    }

    /**
     * 保存
     * @param preposeUser
     * @return R
     */
    @PostMapping
    public R save(@RequestBody UserApplication userApplication){
			return new R<>(userApplicationService.insert(userApplication));
    }

    /**
     * 修改
     * @param preposeUser
     * @return R
     */
    @PutMapping
    public R update(@RequestBody UserApplication userApplication){
    	userApplicationService.updateById(userApplication);
      return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
      return new R<>(userApplicationService.deleteById(id));
    }
    
    /**
     * 用户申请共享数据库的主题和字段
     */
    @UserLogRecord(description="用户申请共享数据库的主题和字段",operationType="申请")
    @PostMapping("/applyApiInformation")
    public String applyApiInformation(@RequestBody UserApplicationDTO userApplicationDTO) {
    	 String applyApiInformation = userApplicationService.applyApiInformation(userApplicationDTO);
    	return applyApiInformation;
    }

}
