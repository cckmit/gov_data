package com.cloud.dips.raus.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.entity.UserApplication;
import com.cloud.dips.raus.api.vo.UserApplicationVO;
import com.cloud.dips.raus.mapper.PreposeUserMapper;
import com.cloud.dips.raus.mapper.UserApplicationMapper;
import com.cloud.dips.raus.service.UserApplicationService;


@Service("userApplicationService")
public class UserApplicationServiceImpl extends ServiceImpl<UserApplicationMapper, UserApplication> implements UserApplicationService {
	@Autowired
	private UserApplicationMapper userApplicationMapper;
	@Autowired
	private PreposeUserMapper preposeUserMapper;
	@Override
	public String applyApiInformation(UserApplicationDTO userApplicationDTO) {
		if (userApplicationDTO.getId() != null) {
			UserApplication userApplication = new UserApplication();
			userApplication.setId((long)userApplicationDTO.getId());
			userApplication.setHandleName(userApplicationDTO.getHandleName());
			userApplication.setHandleResult(userApplicationDTO.getHandleResult());
			userApplication.setReason(userApplicationDTO.getReason());
			userApplication.setModifiedTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
			userApplication.setMoney(userApplicationDTO.getMoney());
			userApplicationMapper.updateById(userApplication);
			if (userApplicationDTO.getHandleResult() == 1) {
				PreposeUser preposeUser = new PreposeUser();
				preposeUser.setPermitColumn(userApplicationDTO.getApplyColumn());
				preposeUser.setPermitColumnCounts(userApplicationDTO.getApplyColumn().split(",").length);
				preposeUser.setTheme(userApplicationDTO.getApplyTheme());
				preposeUser.setPermitThemeCounts(userApplicationDTO.getApplyTheme().split(",").length);
				preposeUser.setInvalidTime(userApplicationDTO.getInvalidTime());
				preposeUser.setUserName(userApplicationDTO.getUserName());
				preposeUser.setApplicationId(userApplicationDTO.getId());
				preposeUserMapper.insert(preposeUser);
			}
			return "success";
		}
		String userName = SecurityUtils.getUser().getUsername();
    	Integer userId = SecurityUtils.getUser().getId();
    	userApplicationDTO.setUserName(userName);
    	userApplicationDTO.setUserId(userId);
    	String orderId = userApplicationMapper.selectMaxOrderId();
    	// 订单编码规则：佛山4406+日期时间+自增编码号
//    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
//    	String dateFormat = format.format(new Date());
    	UserApplication userApplication = new UserApplication();
    	BeanUtils.copyProperties(userApplicationDTO, userApplication);
    	userApplication.setCreateTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    	userApplication.setModifiedTime(LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    	// 数据库不存在任何订单
    	if (null == orderId) {
//    		String firstOrderId = "4406"+dateFormat+"000001";
    		String firstOrderId = "000001";
    		userApplication.setOrderId(firstOrderId);
    		userApplicationMapper.insert(userApplication);
    		return firstOrderId;
    	}
    	// 数据库存在订单
//    	String substring = orderId.substring(16, orderId.length());
    	int intValue = Integer.parseInt(orderId);
    	intValue++;
//    	String userOrderId = "4406"+dateFormat+String.format("%06d", intValue);
    	String userOrderId = String.format("%06d", intValue);
    	userApplication.setOrderId(userOrderId);
		userApplicationMapper.insert(userApplication);
		return userOrderId;
	}
	@Override
	public Page<UserApplicationVO> selectApplicationPage(Query query) {
		Object connectName = query.getCondition().get("connectName");
		Object createTime = query.getCondition().get("createTime");
		Object handleResult = query.getCondition().get("handleResult");
		String type = SecurityUtils.getUser().getType();
		Integer userId = SecurityUtils.getUser().getId();
	    List<UserApplicationVO> applicationPage = userApplicationMapper.selectApplicationPage(query, connectName, createTime, handleResult, type,userId);
	    query.setRecords(applicationPage);
		return query;
	}

	
}