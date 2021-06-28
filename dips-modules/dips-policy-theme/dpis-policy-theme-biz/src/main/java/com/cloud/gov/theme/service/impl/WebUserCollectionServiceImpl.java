package com.cloud.gov.theme.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.theme.api.entity.WebUserCollection;
import com.cloud.gov.theme.mapper.WebUserCollectionMapper;
import com.cloud.gov.theme.service.WebUserCollectionService;

/**
 * 
 * @author johan
 * 2019年1月3日
 *WebUserCollectionServiceImpl.java
 */
@Service("webUserCollectionService")
public class WebUserCollectionServiceImpl extends ServiceImpl<WebUserCollectionMapper, WebUserCollection> implements WebUserCollectionService {

	@Autowired
	private WebUserCollectionMapper webUserCollectionMapper;
	
	/**
	 * 查询是否收藏
	 */
	@Override
	public List<WebUserCollection> queryIsCollection() {
		Integer userId = SecurityUtils.getUser().getId();
    	WebUserCollection webUserCollection = new WebUserCollection();
    	webUserCollection.setWebUserId(userId);
    	EntityWrapper<WebUserCollection> entityWrapper = new EntityWrapper<>(webUserCollection,"web_user_id,policy_id,policy_type");
    	entityWrapper.orderBy("create_time", false);
		List<WebUserCollection> selectList = webUserCollectionMapper.selectList(entityWrapper);
		return selectList;
	}
	
	/**
	 * 设置查询收藏需要的参数
	 */
	 @Override
	 public WebUserCollection setCollectionValue(String collectionId, String mark,String title) {
	    	Integer policyId = Integer.valueOf(collectionId);
	    	Integer userId = SecurityUtils.getUser().getId();
	    	WebUserCollection webUserCollection = new WebUserCollection();
	    	webUserCollection.setWebUserId(userId);
	    	webUserCollection.setPolicyId(policyId);
	    	webUserCollection.setPolicyType(mark);
	    	if (StringUtils.isNotBlank(title)) {
	    	webUserCollection.setTitle(title);
	    	}
	    	return webUserCollection;
	    }

	@Override
	public String queryCollectionTitle(Integer collectionId,String mark) {
		return webUserCollectionMapper.queryCollectionTitle(collectionId, mark);
	}
	 
	 
}
