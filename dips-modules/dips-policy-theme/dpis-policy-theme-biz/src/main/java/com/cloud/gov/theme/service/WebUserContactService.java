package com.cloud.gov.theme.service;


import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.vo.WebusercontactVo;

/**
 * 
 *
 * @author BigPan
 * @date 2018-12-07 10:33:33
 */
public interface WebUserContactService extends IService<WebUserContact> {
	
	/**
	 * 设置用户根据标签或是根据字段订阅
	 * @param webusercontactVo
	 * @param webUserContact
	 * @param userId
	 */
	public void setUserContact(WebusercontactVo webusercontactVo, WebUserContact webUserContact,Integer userId);
}

