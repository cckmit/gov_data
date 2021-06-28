package com.cloud.dips.raus.util;



import javax.servlet.http.HttpServletRequest;


import org.json.JSONObject;


/**
 * 
 * @author johan
 * @Date 2018年12月4日  
 * @Company 佛山司马钱技术有限公司
 * @description 获取用户的代理ip和真实ip
 */
public class GetIpUtil {

	/**
	 * 获取代理ip
	 * @param request
	 * @return
	 */
	public String getUserIp(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    boolean realIp = (null == ip || ip.length()==0 || "unknown".equalsIgnoreCase(ip)); 
	    if (realIp) {
	    	ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (realIp) {
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (realIp) {
	    	ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (realIp) {
	    	ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (realIp) {
	    	ip = request.getHeader("Cdn-Src-Ip");
	    }
	    if (realIp) {
	    	ip = request.getRemoteAddr();
	    }
		return ip;
		
	}
	
	  
		/**
		  *  获取真实iP和归属地
		 * @param request
		 * @return
		 */
		public JSONObject getRealIp(HttpServletRequest request) {
			String ip = getUserIp(request);
			ConnectIp connectIp = new ConnectIp();
			JSONObject json = connectIp.pconlineForIp(request,ip);
		return json;
			
		}
}
