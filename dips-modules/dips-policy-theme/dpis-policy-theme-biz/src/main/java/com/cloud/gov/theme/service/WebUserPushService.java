package com.cloud.gov.theme.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.dto.SortingDataDTO;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.vo.WebUserPushVO;

/**
 * 用户d订阅主题推送表
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
public interface WebUserPushService extends IService<WebUserPush> {
	
	public SortingDataDTO findThemeByUser(String theme,String mark,Integer userId);
	
	public void savePush(Page<SortDataVO> pageTotal,Integer userId);
	
	Page<WebUserPushVO>queryPushByTheme(Query<WebUserPushVO> query);
	
	/**
	 * 订阅的列表
	 */
	public Page<SortDataVO> queryPush(SortingDataDTO sortingDataDTO,Query query,String mark);
	
}

