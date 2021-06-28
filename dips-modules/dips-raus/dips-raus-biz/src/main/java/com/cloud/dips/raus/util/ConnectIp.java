package com.cloud.dips.raus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * 
 * @author johan
 * @Date 2018年12月5日  
 * @Company 佛山司马钱技术有限公司
 * @description 获取用户代理ip,真实ip,归属地
 */
public class ConnectIp {

	private static final String ENCODE = "gb2312";
	private static final String CONNECT_PCONLINE = "http://whois.pconline.com.cn/?ip=";
	private static final String SUBSTRING_REAL_IP_START = "X-Real-IP=";
	private static final String SUBSTRING_REAL_IP_END = "<br/>X-Forwarded-For=";
	private static final String SUBSTRING_REAL_IP_LOCAL_START = "位置：";
	private static final String SUBSTRING_REAL_IP_LOCAL_END = " </p>";
	
	
		
	private URL url = null;
	private URLConnection connection = null;
	private InputStream inputStream = null;
	private InputStreamReader reader = null;
	private BufferedReader buffReader = null;
	private String buf = null;
	private StringBuilder allIpInformation = null;
	
	
	public JSONObject pconlineForIp(HttpServletRequest request,String ip) {
	    int local = 10;
	    connectIpMethod(CONNECT_PCONLINE,ip);
	    String string = allIpInformation.toString();
	    String substring = allIpInformation.substring(string.indexOf(SUBSTRING_REAL_IP_START)+local, string.indexOf(SUBSTRING_REAL_IP_END));
	
	    String realIp = substring.toString();
	    connectIpMethod(CONNECT_PCONLINE,realIp);
	    String realInformation = allIpInformation.toString();
	    String ipLocation = StringUtils.substringBetween(realInformation, SUBSTRING_REAL_IP_LOCAL_START, SUBSTRING_REAL_IP_LOCAL_END).trim();
	    
	    JSONObject json = new JSONObject();
	    json.put("agentIp", ip);
	    json.put("realIp", realIp);
	    json.put("ipLocation", ipLocation);
	    
	return json;
	
	}
	
	private void connectIpMethod(String webAddress,String ip) {
		try {
			url = new URL(webAddress+ip);
			connection = url.openConnection();
			inputStream = connection.getInputStream();
			reader = new InputStreamReader(inputStream,ENCODE);
			buffReader = new BufferedReader(reader);
			allIpInformation =  new StringBuilder ();
			while ((buf = buffReader.readLine()) != null) {
				allIpInformation.append(buf);
				}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
				reader.close();
				buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
