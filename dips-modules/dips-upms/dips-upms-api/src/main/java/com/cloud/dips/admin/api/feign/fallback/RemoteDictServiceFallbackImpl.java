package com.cloud.dips.admin.api.feign.fallback;

import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictVO;
import com.cloud.dips.admin.api.vo.DictValueVO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j
@Component
public class RemoteDictServiceFallbackImpl implements RemoteDictService {
	@Setter
	private Throwable cause;

	/**
	 * 通过类型查询字典值域列表
	 *
	 * @param number 类型
	 * @return R
	 */
	@Override
	public List<DictValueVO> list(String number) {
		log.error("feign 查询字典信息失败:{}", cause);
		return null;

	}

	@Override
	public List<DictVO> allList() {
		log.error("feign 查询字典信息失败:{}", cause);
		return null;
	}

	/**
	 * 通过一组类型查询字典值域列表集合
	 *
	 * @param numberList 字典编码集合
	 * @return 字典值域列表集合
	 */
	@Override
	public Map<String, List<DictValueVO>> getDictMap(String[] numberList) {
		log.error("feign 查询字典信息失败:{}", cause);
		return null;
	}
	
	@Override
	public List<DictValueVO> getlistDictValue(Integer themeId) {
		log.error("feign 查询主题信息失败:{}", cause);
		return null;
	}

	@Override
	public List<DictValueVO> dictValueParents(Integer id) {
		log.error("feign 查询一级主题信息失败:{传参出错}", cause);
		return null;
	}
}
