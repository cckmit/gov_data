package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cloud.dips.admin.api.entity.SysDictValue;

import lombok.Data;

/**
 * @author ZB
 */
@Data
public class DictValueVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 字典键
	 */
	private String key;
	/**
	 * 字典值
	 */
	private String value;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 所属字典id
	 */
	private Integer dictId;
	/**
	 * 父类id
	 */
	private Integer parentId;
	/**
	 * 排序
	 */
	private Integer sort;

	private List<SysDictValue> dictValueList;
	
	/**
	 * 字段订阅或标签订阅标识位
	 */
	private Integer type;
}
