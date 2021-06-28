package com.cloud.gds.gmsanalyse.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-29
 */
@Data
public class AnalyseDetails {

	/**
	 * 详情
	 */
	private String analyseSummary;

	/**
	 * 饼状图,图表
	 */
	private List<Echarts> echartsList;

	/**
	 * 柱状图
	 */
	private AxisEcharts axisEcharts;


}
