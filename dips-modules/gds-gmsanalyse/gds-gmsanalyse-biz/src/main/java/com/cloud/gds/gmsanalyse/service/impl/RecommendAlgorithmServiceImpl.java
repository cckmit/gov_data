package com.cloud.gds.gmsanalyse.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.gds.gmsanalyse.bo.PolicyRecommendBo;
import com.cloud.gds.gmsanalyse.service.RecommendAlgorithmService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 政策推荐
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-06
 */
@Service
public class RecommendAlgorithmServiceImpl implements RecommendAlgorithmService {


	@Override
	public String generalRecommend(PolicyRecommendBo recommendBo) {
		String result = null;
		String[] arguments = null;
		//配置python算法程序文件路径，python环境驱动并填入json文件所在路径
		arguments = new String[]{"/anaconda3/bin/python", "/usr/local/data-cleaning/analysis_similarity/deepAnalyse.py",new JSONObject().toJSONString(recommendBo) };

		try {
			String line = null;
			//调用python 推荐政策情况
			Process process = Runtime.getRuntime().exec(arguments);
			//将得到的数据进行io处理
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = in.readLine()) != null) {
				//算法得到的json数据
				result = line;
			}
			//关闭流
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
