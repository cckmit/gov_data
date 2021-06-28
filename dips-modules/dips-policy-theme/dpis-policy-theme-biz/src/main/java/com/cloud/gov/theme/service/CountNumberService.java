package com.cloud.gov.theme.service;

import java.util.List;
import java.util.Map;


/**
  *  统计收藏，足迹，推送信息接口
 * @author johan
 * 2018年12月13日
 *CountNumberService.java
 */
public interface CountNumberService {

	/**
	 * 统计收藏，足迹，推送信息接口
	 * 
	 */
	public List<Map<String, Integer>> queryCount();
}
