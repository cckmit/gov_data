package com.cloud.dips.theme.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cloud.dips.tag.api.vo.UserTagVO;

import lombok.Data;

/**
 * 
 *
 * @author johan
 * @date 2018-12-10
 */
@Data
public class WebUserContactDTO {

	
	/**
	 * 层级
	 */
	private  String[]  level = new String[0];
	
	/**
	 * 规模
	 */
	private  String[]  scale = new String[0];
	/**
	 * 部门
	 */
	private String[] department = new String[0];
	/**
	 * 角色
	 */
	private  String[]  target = new String[0];
	/**
	 * 行业
	 */
	private  String[]  industry = new String[0];

	/**
	 * 地区编码
	 */
	private  String [] region = new String[0];

	/**
	 *所订阅主题
	 */
	private  Integer[] theme = new Integer[0];
	/**
	 *所订阅主题key值
	 */
	private  String[] keys = new String[0];
	
	/**
	 *标签信息
	 */
	private  List<UserTagVO> tagStatus = new ArrayList<UserTagVO>();
	/**
	 * 地区名称
	 */
	private  String[] regionName = new String[0];
	/**
	 * 字段订阅或标签订阅标识位
	 */
	private Integer type;
	/**
	 * 是否通过邮箱推送(0-否1-是）
	 */
	private Integer isMail;
	
}
