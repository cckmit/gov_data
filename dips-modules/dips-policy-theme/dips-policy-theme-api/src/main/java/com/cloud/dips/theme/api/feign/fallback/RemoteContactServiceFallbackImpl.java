package com.cloud.dips.theme.api.feign.fallback;

import org.springframework.stereotype.Component;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.theme.api.dto.WebUserContactDTO;
import com.cloud.dips.theme.api.feign.RemoteContactService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j
@Component
public class RemoteContactServiceFallbackImpl implements RemoteContactService {
	@Setter
	private Throwable cause;

	@Override
	public R<WebUserContactDTO> getcontact(String webId) {
		log.error("feign 查询用户其余信息失败:{参数出错}", cause);
		return null;
	}

	@Override
	public void updateOtherInformation(String jsonString) {
		log.error("feign 修改用户其余信息失败:{参数出错}", cause);
		
	}

	
}
