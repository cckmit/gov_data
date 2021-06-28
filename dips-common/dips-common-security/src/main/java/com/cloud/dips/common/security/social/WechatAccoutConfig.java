package com.cloud.dips.common.security.social;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yinzan
 * @ClassName: WechatAccoutConfig
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2018/12/19下午3:48
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccoutConfig {

	private String maAppId;

	private String maAppSecret;
}
