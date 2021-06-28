package com.cloud.dips.theme.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.theme.api.dto.WebUserContactDTO;
import com.cloud.dips.theme.api.feign.factory.RemoteContactServiceFallbackFactory;
import com.cloud.dips.theme.api.vo.WebusercontactVo;

/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.THEME_SERVICE, fallbackFactory = RemoteContactServiceFallbackFactory.class)
public interface RemoteContactService {

	/**
	 * 查询用户其它基本资料
	 * @return
	 */
	@GetMapping("/webusercontact/getcontact")
	public R<WebUserContactDTO> getcontact(@RequestParam("webId")String webId);
	
	/**
	 * 修改用户的其它基本资料
	 */
	@PutMapping("/webusercontact/updateOtherInformation")
	public void updateOtherInformation(@RequestParam("jsonString")String jsonString);
}
