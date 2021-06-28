package com.cloud.dips.raus.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author johan
 * @Date 2018年12月7日  
 * @Company 佛山司马钱技术有限公司
 * @description 对称加密算法AES
 */
public class EncryptionForAES {
	
	/**
	 * 对称算法加密
	 * @param content
	 * @return
	 */
	public static String aesEncode(String content){
        try {
        	// 加密规则
        	String encodeRules = "qweasdzxc1234569";
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            // 3.产生原始对称密钥
            SecretKey originalKey=keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte [] raw=originalKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byteEncode=content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte [] byteAES=cipher.doFinal(byteEncode);
            // 10.将加密后的数据转换为字符串
            String aesEncode=new String(Base64.encodeBase64String(byteAES));
            // 11.将字符串返回
            return aesEncode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        //如果有错就返加nulll
        return null;         
    }
	
	
   /**
    * 对称算法解密
    * @param content
    * @return
    */
    public static String asdnCode(String content){
        try {
        	// 解密规则
        	String encodeRules = "qweasdzxc1234569";
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            // 3.产生原始对称密钥
            SecretKey originalKey=keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte [] raw=originalKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
            byte [] byteContent= Base64.decodeBase64(content);
            /*
             * 解密
             */
            byte [] byteDecode=cipher.doFinal(byteContent);
            String aesDecode=new String(byteDecode,"utf-8");
            return aesDecode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        //如果有错就返加nulll
        return null;         
    }
    
}
