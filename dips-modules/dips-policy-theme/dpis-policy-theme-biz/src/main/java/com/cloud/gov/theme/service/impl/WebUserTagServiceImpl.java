package com.cloud.gov.theme.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.entity.WebUserTag;
import com.cloud.gov.theme.mapper.WebUserContactMapper;
import com.cloud.gov.theme.mapper.WebUserPushMapper;
import com.cloud.gov.theme.mapper.WebUserTagMapper;
import com.cloud.gov.theme.service.WebUserPushService;
import com.cloud.gov.theme.service.WebUserTagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author yinzan
 * @description //TODO
 * @date 下午3:45 2018/12/17
 * @param
 * @return
　*/
@Service
@AllArgsConstructor
public class WebUserTagServiceImpl extends ServiceImpl<WebUserTagMapper, WebUserTag> implements WebUserTagService {

	

	
}
