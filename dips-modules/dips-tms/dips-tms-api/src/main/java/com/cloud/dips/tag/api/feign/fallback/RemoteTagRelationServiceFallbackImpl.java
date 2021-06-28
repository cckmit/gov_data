package com.cloud.dips.tag.api.feign.fallback;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.cloud.dips.tag.api.vo.CommonVO;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.api.vo.UserTagVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZB
 */
@Slf4j
@Component
public class RemoteTagRelationServiceFallbackImpl implements RemoteTagRelationService {
	@Setter
	private Throwable cause;

	@Override
	public R<Boolean> saveTagRelation(@RequestParam(value = "params",required = false) Map<String, Object> params) {
		log.error("feign 插入失败", cause);
		return null;
	}

	@Override
	public R<Boolean> deleteTagRelation(Integer relationId,String node) {
		log.error("feign 删除失败", cause);
		return null;
	}

	@Override
	public R<Map<String, List<CommonVO>>> getTags(Map<String, Object> params) {
		log.error("feign 获取失败", cause);
		return null;
	}

	@Override
	public List<UserTagVO> selectTagsByIds(String ids) {
		log.error("feign 获取web端名称失败", cause);
		return null;
	}
}
