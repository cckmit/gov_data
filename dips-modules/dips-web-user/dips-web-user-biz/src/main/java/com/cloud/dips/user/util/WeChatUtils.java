package com.cloud.dips.user.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.cloud.dips.user.constant.WxConfigConstant;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yinzan
 * @ClassName: WeChatUtils
 * @ProjectName dips
 * @Description: TODO
 * @date 2019/3/20下午2:56
 */
@Slf4j
public class WeChatUtils {


	//获取accessToken
	public static JSONObject getAccessToken() {
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("grant_type", "client_credential");
		paramMap.put("appid", WxConfigConstant.WX_APPID);
		paramMap.put("secret", WxConfigConstant.WX_APPSECRET);
		String result = HttpUtil.get(accessTokenUrl, paramMap);

		JSONObject jsonObject = JSON.parseObject(result);
		log.info("请求微信获取token=====>"+jsonObject);

		return jsonObject;
	}

	//获取ticket
	public static JSONObject getJsApiTicket(String accessToken) {
		String apiTicketUrl= "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("access_token",accessToken);

		paramMap.put("type", "jsapi");
		String result = HttpUtil.get(apiTicketUrl,paramMap);
		JSONObject jsonObject = JSON.parseObject(result);
		log.info("请求微信获取jsapi=====>"+jsonObject);
		return jsonObject;
	}

	//生成微信权限验证的参数
	public static Map<String, Object> makeWXTicket(Object jsApiTicket, String url) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String nonceStr = createNonceStr();
		String timestamp = createTimestamp();
		String string1;
		String signature = "";

		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsApiTicket +
			"&noncestr=" + nonceStr +
			"&timestamp=" + timestamp +
			"&url=" + url;
		log.info("String1=====>" + string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			log.info("signature=====>" + signature);
		} catch (NoSuchAlgorithmException e) {
			log.error("WeChatController.makeWXTicket=====Start");
			log.error(e.getMessage(), e);
			log.error("WeChatController.makeWXTicket=====End");
		} catch (UnsupportedEncodingException e) {
			log.error("WeChatController.makeWXTicket=====Start");
			log.error(e.getMessage(), e);
			log.error("WeChatController.makeWXTicket=====End");
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsApiTicket);
		ret.put("nonceStr", nonceStr);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", WxConfigConstant.WX_APPID);

		return ret;
	}

	/**
	 * //字节数组转换为十六进制字符串
	 *
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * //生成随机字符串
	 *
	 * @return
	 */

	private static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	/**
	 * //生成时间戳
	 *
	 * @return
	 */
	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
