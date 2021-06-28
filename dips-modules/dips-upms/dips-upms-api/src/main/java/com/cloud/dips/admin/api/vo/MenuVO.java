package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
public class MenuVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	private Integer id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单权限标识
	 */
	private String permission;
	/**
	 * 父菜单ID
	 */
	private Integer parentId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 一个路径
	 */
	private String path;
	/**
	 * VUE页面
	 */
	private String component;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 0--正常 1--删除
	 */
	private String isDeleted;


	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/**
	 * menuId 相同则相同
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MenuVO) {
			Integer targetMenuId = ((MenuVO) obj).getId();
			return id.equals(targetMenuId);
		}
		return super.equals(obj);
	}
}
