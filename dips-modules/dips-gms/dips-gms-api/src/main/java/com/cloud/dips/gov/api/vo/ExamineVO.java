package com.cloud.dips.gov.api.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cloud.dips.gov.api.dto.FileCommonDTO;

import lombok.Data;

/**
 * @author Wave
 */
@Data
public class ExamineVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	@TableField("userName")
	private String userName;
	/**
	 * 通过次数
	 */
	@TableField("agreeCount")
	private String agreeCount;
	/**
	 * 退回次数
	 */
	@TableField("disagreeCount")
	private String disagreeCount;
	/**
	 * 提交次数
	 */
	@TableField("commitCount")
	private String commitCount;
	/**
	 * 积分
	 */
	private String mark;
	
}
