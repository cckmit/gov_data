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
import com.cloud.dips.raus.api.dto.PreposeUserDTO;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.service.PreposeUserService;


/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
@RestController
@RequestMapping("/preposeuser")
public class PreposeUserController {
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
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
			PreposeUser preposeUser = preposeUserService.selectById(id);
			return new R<>(preposeUser);
    }

    /**
     * 保存
     * @param preposeUser
     * @return R
     */
    @PostMapping
    public R save(@RequestBody PreposeUser preposeUser){
			return new R<>(preposeUserService.insert(preposeUser));
    }

    /**
     * 修改
     * @param preposeUser
     * @return R
     */
    @PutMapping
    public R update(@RequestBody PreposeUser preposeUser){
			preposeUserService.updateById(preposeUser);
      return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
      return new R<>(preposeUserService.deleteById(id));
    }
    
    /**
     * 创建用户数据库api的token
     * @return
     */
    @UserLogRecord(description="管理员创建用户的API令牌",operationType="保存")
    @PostMapping("/createUserToken")
    public R createUserToken(@RequestBody PreposeUserDTO preposeUserDTO) {
    	String tokenName = preposeUserService.createToken(preposeUserDTO);
    	return new R<>(tokenName);
    }
}
