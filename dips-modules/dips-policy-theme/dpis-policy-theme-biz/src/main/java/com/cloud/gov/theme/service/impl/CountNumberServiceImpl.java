package com.cloud.gov.theme.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.theme.api.constant.ThemeConstant;
import com.cloud.dips.theme.api.dto.WebUserPushDTO;
import com.cloud.dips.theme.api.entity.WebUserCollection;
import com.cloud.dips.theme.api.entity.WebUserFootprint;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.gov.theme.controller.WebUserPushController;
import com.cloud.gov.theme.mapper.WebUserCollectionMapper;
import com.cloud.gov.theme.mapper.WebUserFootprintMapper;
import com.cloud.gov.theme.mapper.WebUserPushMapper;
import com.cloud.gov.theme.service.CountNumberService;
/**
 * 
 * @author johan
 * 2019年1月3日
 *CountNumberServiceImpl.java
 */
@Service("CountNumberService")
public class CountNumberServiceImpl implements CountNumberService {

	
    @Autowired
    private WebUserFootprintMapper webUserFootprintMapper;
    @Autowired
    private WebUserCollectionMapper webUserCollectionMapper;
    @Autowired
    private WebUserPushMapper webUserPushMapper;
    /**
     * 统计收藏，足迹，订阅信息数量
     */
    @Override
    public List<Map<String, Integer>> queryCount() {
    	Integer userId = SecurityUtils.getUser().getId();
    	
    	WebUserFootprint webUserFootprint = new WebUserFootprint();
    	webUserFootprint.setWebUserId(userId);
    	EntityWrapper<WebUserFootprint> footprintWrapper = new EntityWrapper<>(webUserFootprint);
    	int footprintCount = webUserFootprintMapper.selectCount(footprintWrapper);
    	
    	WebUserCollection webUserCollection = new WebUserCollection();
    	webUserCollection.setWebUserId(userId);
    	EntityWrapper<WebUserCollection> collectionWrapper = new EntityWrapper<>(webUserCollection);
    	int collectionCount = webUserCollectionMapper.selectCount(collectionWrapper);
    	
    	Map<String, Integer> map = new HashMap<String, Integer>(16);
    	map.put("wealth", 0);
    	map.put("favorites", collectionCount);
    	map.put("history", footprintCount);
    	map.put("subscription", WebUserPushController.total);
    	map.put("message", 0);
    	WebUserPushController.total = ThemeConstant.IS_NOT_DO;
    	List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
    	list.add(map);
    	return list;
    }
}
