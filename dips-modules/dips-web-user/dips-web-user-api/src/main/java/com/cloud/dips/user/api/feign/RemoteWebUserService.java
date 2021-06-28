package com.cloud.dips.user.api.feign;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author yinzan
 * @ClassName: RemoteWebUserService
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2018/12/6下午4:18
 */
@FeignClient(value = ServiceNameConstant.WEB_USER_SERVICE)
public interface RemoteWebUserService {
}
