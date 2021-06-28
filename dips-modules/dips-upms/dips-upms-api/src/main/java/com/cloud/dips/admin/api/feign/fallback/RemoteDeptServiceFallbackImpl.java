package com.cloud.dips.admin.api.feign.fallback;

import com.cloud.dips.admin.api.feign.RemoteDeptService;
import com.cloud.dips.admin.api.vo.DeptCityVO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class RemoteDeptServiceFallbackImpl implements RemoteDeptService {
	@Setter
	private Throwable cause;

	@Override
	public List<DeptCityVO> list() {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}
}
