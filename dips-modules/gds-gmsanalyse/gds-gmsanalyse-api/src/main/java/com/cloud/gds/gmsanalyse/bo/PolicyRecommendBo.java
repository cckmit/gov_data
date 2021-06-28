package com.cloud.gds.gmsanalyse.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 政策推荐算法bo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
@Data
public class PolicyRecommendBo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 是否属于通用政策
	 */
	private boolean belongToGeneral;
	/**
	 * 推荐数量
	 */
	private Integer num;
}
