package com.cloud.gov.theme.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.vo.WebUserPushVO;
import com.cloud.gov.theme.mapper.WebUserContactMapper;
import com.cloud.gov.theme.mapper.WebUserPushMapper;
import com.cloud.gov.theme.service.WebUserPushService;

/**
 * 
 * @author johan
 * 2019年1月3日
 *WebUserPushServiceImpl.java
 */
@Service("webUserPushService")
public class WebUserPushServiceImpl extends ServiceImpl<WebUserPushMapper, WebUserPush> implements WebUserPushService {

	
	@Autowired
	private WebUserContactMapper webUserContactMapper;
	@Autowired
	private WebUserPushMapper webUserPushMapper;
	
	/**
	 * 查找该用户是否包含订阅主题
	 */
	@Override
	public SortingDataDTO findThemeByUser(String theme,String mark,Integer userId) {
		
		WebUserContact webUserContact = new WebUserContact();
		webUserContact.setWebUserId(userId);
		WebUserContact userContact = webUserContactMapper.selectOne(webUserContact);
		if (null == userContact) {
			return null;
		}
		boolean isNull = false;
		if (userContact.getType().intValue() == 0) {
			isNull =StringUtils.isBlank(userContact.getTarget()) && StringUtils.isBlank(userContact.getLevel()) 
					&& StringUtils.isBlank(userContact.getScale()) && StringUtils.isBlank(userContact.getDictValueKey()) 
					&& StringUtils.isBlank(userContact.getRegion()) && StringUtils.isBlank(userContact.getIndustry()) 
				    && StringUtils.isBlank(userContact.getDepartment());
		}
		if (userContact.getType().intValue() == 1) {
			isNull = StringUtils.isBlank(userContact.getTagStatus());
		}
		if (isNull) {
			return null;
		}
		SortingDataDTO sortingDataDTO = new SortingDataDTO();
	
		sortingDataDTO.setTargetString(userContact.getTarget());
		sortingDataDTO.setLevelString(userContact.getLevel());
		sortingDataDTO.setScaleString(userContact.getScale());
		sortingDataDTO.setIndustry(userContact.getIndustry());
		sortingDataDTO.setDeparment(userContact.getDepartment());
		sortingDataDTO.setType(userContact.getType());
		sortingDataDTO.setRegion(userContact.getRegion());
		if (userContact.getRegion().contains("0")) {
			sortingDataDTO.setRegion("0");
		}
		if (userContact.getType().intValue() == 0) {
			if (StringUtils.isBlank(theme)) {
				sortingDataDTO.setTheme(userContact.getDictValueKey());
			}
			if (StringUtils.isNotBlank(theme)) {
				sortingDataDTO.setTheme(theme);
			}	
		}
		if (userContact.getType().intValue() == 1) {
			if (StringUtils.isBlank(theme)) {
				sortingDataDTO.setTagStatus(userContact.getTagStatus());
			}
			if (StringUtils.isNotBlank(theme)) {
				sortingDataDTO.setTagStatus(theme);
			}	
		}
		if (StringUtils.isNotBlank(mark)) {
			sortingDataDTO.setMark(mark);
		}
		return sortingDataDTO;
	}

	/**
	 * 保存订阅列表政策id和类型
	 * 异步调用
	 */
	@Override
	public void savePush(Page<SortDataVO> pageTotal,Integer userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("web_user_id", userId);
		List<WebUserPush> selectByMap = webUserPushMapper.selectByMap(map);
		List<SortDataVO> records = pageTotal.getRecords();
		
		if(selectByMap.size() > 0) {
			List<WebUserPush> pushList = new ArrayList<WebUserPush>();
			 for (SortDataVO sortDataVO : records) {
				 WebUserPush newPush = new WebUserPush();
				 newPush.setWebUserId(userId);
				 newPush.setMark(sortDataVO.getMark());
				 newPush.setPolicyId(sortDataVO.getId());
				 newPush.setTitle(sortDataVO.getTitle());
				 pushList.add(newPush);
				}
			
			 webUserPushMapper.insertUniquePush(pushList);
			 return;
		}
		for (SortDataVO sortDataVO : records) {
			WebUserPush webUserPush = new WebUserPush();
			webUserPush.setWebUserId(userId);
			webUserPush.setPolicyId(sortDataVO.getId());
			webUserPush.setMark(sortDataVO.getMark());
			webUserPush.setTitle(sortDataVO.getTitle());
			webUserPush.insert();
		}
	}
	

	@Override
	public Page<WebUserPushVO> queryPushByTheme(Query<WebUserPushVO> query) {
		Object webUserId = query.getCondition().get("webUserId");
		Object theme = query.getCondition().get("theme");
		query.setRecords( webUserPushMapper.queryPushByTheme(query,webUserId, theme));
		return query;
	}

	@Override
	public Page<SortDataVO> queryPush(SortingDataDTO sortingDataDTO,Query query,String mark) {
		// 获取当前年月和前三个月
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				String currentMonth = format.format(c.getTime());
				c.add(Calendar.MONTH, -3);
				String beforeMonth = format.format(c.getTime());
				// 根据标签订阅
				if (sortingDataDTO.getType().intValue() == 1) {
					List<Integer> policyId = webUserPushMapper.selectRelationId(sortingDataDTO.getTagStatus());
					List<SortDataVO> tagPolicy = webUserPushMapper.selectPolicyById(query,policyId,beforeMonth,currentMonth,mark);
					query.setRecords(tagPolicy);
					return query;
				}
				// 根据字段订阅
				List<SortDataVO> policyDeclare = webUserPushMapper.queryThemeForTwo(query,  sortingDataDTO.getRegion(), sortingDataDTO.getTargetString(),
						sortingDataDTO.getScaleString(),sortingDataDTO.getTheme(),beforeMonth,currentMonth,mark);
				query.setRecords(policyDeclare);
		return query;
	}
}
