package com.cloud.gds.gmsanalyse.vo;

import lombok.Data;

import java.util.List;

/**
 * 柱状图
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-05
 */
@Data
public class AxisEcharts {
	/**
	 * x轴数据
	 */
	private List<String> xAxis;
	/**
	 * y轴数据
	 */
	private List<Integer> yAxis;

}
