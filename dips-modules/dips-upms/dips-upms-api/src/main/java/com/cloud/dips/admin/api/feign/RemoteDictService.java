package com.cloud.dips.admin.api.feign;

import com.cloud.dips.admin.api.feign.factory.RemoteDictServiceFallbackFactory;
import com.cloud.dips.admin.api.vo.DictVO;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteDictServiceFallbackFactory.class)
public interface RemoteDictService {


	/**
	 * 通过类型查询字典值域列表
	 *
	 * @param number 类型
	 * @return R
	 */
	@GetMapping("/dict/list/{number}")
	List<DictValueVO> list(@PathVariable("number") String number);

	/**
	 * 查询字典全部值域列表
	 *
	 * @return R
	 */
	@GetMapping("/dict/all_list")
	List<DictVO> allList();

	/**
	 * 通过一组类型查询字典值域列表集合
	 *
	 * @param numberList 字典编码集合
	 * @return 字典值域列表集合
	 */
	@GetMapping("/dict/map")
	Map<String, List<DictValueVO>> getDictMap(@RequestParam("numberList[]") String[] numberList);
	
	/**
	  * 根据主题id找到对应的主题
	 * @param themeId
	 * @return
	 */
	@GetMapping("/dict/listDictValue/themeId")
	public List<DictValueVO> getlistDictValue(@RequestParam("themeId") Integer themeId); 
	
	/**
	 * 字典值一级分类集合
	 * @param id
	 * @return
	 */
	@RequestMapping("/dictValue/dictValueParents/{dId}")
	public List<DictValueVO> dictValueParents(@PathVariable("dId") Integer id);
}
