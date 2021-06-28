package com.cloud.dips.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.user.api.dto.UserSuggestionsDTO;
import com.cloud.dips.user.api.entity.UserSuggestions;
import com.cloud.dips.user.service.UserSuggestionsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author yinzan
 * @ClassName: UserSuggestionsController
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/2下午4:49
 */
@RestController
@RequestMapping("/suggestions")
public class UserSuggestionsController {

	@Autowired
	private UserSuggestionsService userSuggestionsService;

	/**
	 * 新增用户建议
	 * @param dto
	 * @return
	 */
	@PostMapping("/savesugg")
	@ApiOperation(value = "新增用户建议",notes = "保存用户id",httpMethod = "POST")
	 public R<Boolean> saveSuggestion(UserSuggestionsDTO dto){

		UserSuggestions userSuggestions =new UserSuggestions();
		BeanUtils.copyProperties(dto,userSuggestions);
		userSuggestions.setUserId(SecurityUtils.getUser().getId());
		userSuggestions.setUsername(SecurityUtils.getUser().getUsername());
		userSuggestions.setCreate_time(LocalDateTime.now());

		return  new R<>(userSuggestionsService.insert(userSuggestions));
	 }

     @GetMapping("/info")
	 @ApiOperation(value = "查询用户所有建议",notes = "根据用户id",httpMethod = "GET")
	 public Page info(@RequestParam Map<String, Object> params){
		 Integer id = SecurityUtils.getUser().getId();
		 params.put("userId",id);
		 return  userSuggestionsService.selectPageByTagId(new Query(params));
	 }


}
