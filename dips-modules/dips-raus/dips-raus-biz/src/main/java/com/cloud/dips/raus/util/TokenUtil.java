package com.cloud.dips.raus.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.service.PreposeUserService;

/**
 * 随机生成32位token访问令牌
 * @author panjiahan
 *@date 2018年10月31日
 *
 */
public class TokenUtil {

	@Autowired
	private PreposeUserService preposeUserService;
	
	public String createToken(String userName,String effectTime) {
//		String userName = params.get("userName").toString();
//		String effectTime = params.get("effectTime").toString();
		StringBuffer  bulid = new StringBuffer ();
		// 新建一个32位数组
		String[] token = new String[32];
		// 设置随机生成的格式
		String[] a= {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
					 "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
				     "1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","/","-","+","="};
		String tokenName = null;
		// 设置开关
		boolean flag = true;
		do {
			// 清除存在的生成的token
			bulid.delete(0, token.length);
			// 随机生成32位token
			for (int i = 0; i < token.length; i++) {
				token[i] = a[(int) (Math.random()*a.length)];
				// 利用StringBuffer进行拼接
				bulid.append(token[i]);
			}
			// 转换为String类型
			tokenName = bulid.toString();
			// 查找数据库时候是否已经存在改令牌，如果存在，则重新生成
			Integer number = preposeUserService.findHasToken(tokenName);
			if(number != null && number <= 0) {
				// 没有相同则走出循环
				flag = false;
			}
		}while(flag);
		// 插入数据库
//		PreposeUser preposeUser = new PreposeUser();
//		preposeUser.setUserName(userName);
//		preposeUser.setToken(tokenName);
//		preposeUser.setEffectTime(effectTime);
//		preposeUserService.insertToken(preposeUser);
		return tokenName;
	}
}
