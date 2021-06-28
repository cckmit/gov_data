package com.cloud.gov.theme.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.theme.api.entity.WebUserCollection;

/**
 * 用户收藏表
 *
 * @author BigPan
 * @date 2018-12-11 10:56:16
 */
public interface WebUserCollectionService extends IService<WebUserCollection> {
	
	public List<WebUserCollection> queryIsCollection();
	
	public WebUserCollection setCollectionValue(String collectionId, String mark,String title);
	
	String queryCollectionTitle(Integer collectionId,String mark);
}

