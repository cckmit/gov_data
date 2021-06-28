package com.cloud.gov.theme.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.gov.theme.service.SendEmailToClineService;
import com.cloud.gov.theme.service.WebUserPushService;

/**
  * 定时发送邮件给用户
 * @author johan
 * 2019年3月29日
 *SendMailToClineController.java
 */
@RestController("/sendMail")
public class SendMailToClineController {

	@Autowired
	private SendEmailToClineService sendEmailToClineService;
	@Autowired
	private WebUserPushService webUserPushService;
	
	public void sendEmailToCline(){
		sendEmailToClineService.sendSimpleEmail();
	}
	
	@Scheduled(cron="0 0/1 * * * ?")
	@GetMapping(value="/findUserThemeList")
	public void findUserThemeList() {
		List<Integer> pushUserId = sendEmailToClineService.selectPushUser();
		for (Integer userId : pushUserId) {
			SortingDataDTO sortingDataDTO = webUserPushService.findThemeByUser(null,null,userId);
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put("page", 1);
			map.put("limit", 10);
			Page<SortDataVO> pageTotal = webUserPushService.queryPush(sortingDataDTO,new Query<>(map),null);
			webUserPushService.savePush(pageTotal, userId);
		}
		sendEmailToCline();
		
	}
}
