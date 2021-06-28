package com.cloud.gov.theme.service.impl;


import java.time.LocalDateTime;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.vo.WebusercontactVo;
import com.cloud.gov.theme.mapper.WebUserContactMapper;
import com.cloud.gov.theme.service.WebUserContactService;

/**
 * 
 * @author johan
 * 2019年1月3日
 *WebUserContactServiceImpl.java
 */
@Service("webUserContactService")
public class WebUserContactServiceImpl extends ServiceImpl<WebUserContactMapper, WebUserContact> implements WebUserContactService {

	/**
	 * 设置用户根据标签或是根据字段订阅
	 * @param webusercontactVo
	 * @param webUserContact
	 * @param userId
	 */
	@Override
	public void setUserContact(WebusercontactVo webusercontactVo,WebUserContact webUserContact,Integer userId) {
			webUserContact.setWebUserId(userId);
			webUserContact.setModifiedTime(LocalDateTime.now());
			webUserContact.setTagStatus(webusercontactVo.getTagStatus());
			webUserContact.setDictValueId(webusercontactVo.getTheme());
			webUserContact.setDictValueKey(webusercontactVo.getKeys());
			webUserContact.setTarget(webusercontactVo.getTarget());
			webUserContact.setRegion(webusercontactVo.getRegion());
			webUserContact.setScale(webusercontactVo.getScale());
			webUserContact.setRegionName(webusercontactVo.getRegionName());
			webUserContact.setType(webusercontactVo.getType());
			webUserContact.setIsMail(webusercontactVo.getIsMail());
	}
	
}
