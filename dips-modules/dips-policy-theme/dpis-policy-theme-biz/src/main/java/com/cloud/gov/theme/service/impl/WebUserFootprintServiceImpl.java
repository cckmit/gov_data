package com.cloud.gov.theme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.theme.api.entity.WebUserFootprint;
import com.cloud.gov.theme.mapper.WebUserFootprintMapper;
import com.cloud.gov.theme.service.WebUserFootprintService;


/**
 * 
 * @author johan
 * 2019年1月3日
 *WebUserFootprintServiceImpl.java
 */
@Service("webUserFootprintService")
public class WebUserFootprintServiceImpl extends ServiceImpl<WebUserFootprintMapper, WebUserFootprint> implements WebUserFootprintService {

	@Autowired
	private WebUserFootprintMapper webUserFootprintMapper;
	
	/**
	 * 设置查询足迹需要的参数
	 */
	@Override
	public WebUserFootprint setFootprintValue(String collectionId, String mark, String title) {
    	Integer policyId = Integer.valueOf(collectionId);
    	Integer userId = SecurityUtils.getUser().getId();
    	WebUserFootprint webUserFootprint = new WebUserFootprint();
    	webUserFootprint.setWebUserId(userId);
    	webUserFootprint.setPolicyId(policyId);
    	webUserFootprint.setPolicyType(mark);
    	webUserFootprint.setTitle(title);
    	return webUserFootprint;
    }
	
	/**
	 * 保存足迹的逻辑实现
	 */
	@Override
	public void saveFootprint(String collectionId, String mark,String title){
	    WebUserFootprint footprintValue = setFootprintValue(collectionId, mark, title);
	    EntityWrapper<WebUserFootprint> entityWrapper = new EntityWrapper<>(footprintValue);
	    int count = webUserFootprintMapper.selectCount(entityWrapper);
	    if(count == 0) {	
	    	webUserFootprintMapper.insert(footprintValue);
	    	}
	    	
	    webUserFootprintMapper.updateForSet("modified_time = Now(),is_readed = 1", entityWrapper);
	    }
}
