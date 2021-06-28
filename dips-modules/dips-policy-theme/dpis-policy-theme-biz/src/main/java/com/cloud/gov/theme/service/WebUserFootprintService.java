package com.cloud.gov.theme.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.theme.api.entity.WebUserFootprint;

/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
public interface WebUserFootprintService extends IService<WebUserFootprint> {
	
	public WebUserFootprint setFootprintValue(String collectionId, String mark, String title);
	
	public void saveFootprint(String collectionId, String mark,String title);
}

