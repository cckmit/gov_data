package com.cloud.dips.gov.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cloud.dips.gov.api.vo.GeneralVO;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author panjiahan
 *@date 2018年7月30日
 *
 */
public class WordUtil {

	private static Configuration configuration  = null;
	
	private static Map<String,Template> allTemplate = null;
	
	static {
		configuration = new Configuration(Configuration.VERSION_2_3_28);//指定freemaker版本信息，根据jar包来选择
		configuration.setDefaultEncoding("UTF-8");//指定文字格式
		configuration.setClassForTemplateLoading(WordUtil.class, "/templates/");//指定读取模板的路径
		
		allTemplate = new HashMap<String,Template>();
		
		try {
			//这里采用key-value的形式保存模板，后台传对应的key值即可获得对应的模板
			allTemplate.put("general", configuration.getTemplate("general.ftl"));
//			allTemplate.put("explain", configuration.getTemplate("explain.ftl"));
//			allTemplate.put("declare", configuration.getTemplate("declare.ftl"));
//			allTemplate.put("information", configuration.getTemplate("information.ftl"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private WordUtil() {
		
	}
	
	public static File createDoc(Map<?,?> dataMap,String type,String title) {
		String name = new String(title+".doc");//指定导出的word名字
        File file = new File(name);//新建word文件
        Template t = allTemplate.get(type);//根据传入来的key值获取相应模板
        try{
            //这个地方不能使用FileWriter因为需要指定编码类型否则声场的word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
//            t.process(dataMap,new PrintWriter("C:\\Users\\24707\\Desktop\\"+name));//测试用于输出到桌面
            t.process(dataMap,w);
            w.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return file;
	}
	
	public static void downloadWord(HttpServletResponse response,File file, Map<String, Object> map) {
	    BufferedInputStream bis=null;
	    OutputStream os=null;
	       try{
	    	   response.reset();
	    	   response.setContentType("application/msword");
	    	   response.setHeader("Content-Disposition", 
	    			   "attachment;filename="+URLEncoder.encode(file.getName(),"UTF-8"));
	    	   bis=new BufferedInputStream(new FileInputStream(file));
	   		byte[] b=new byte[bis.available()+1000];
	   		int i=0;
	           	os = response.getOutputStream();   //直接下载导出
	           	while((i=bis.read(b))!=-1) {
	           		os.write(b, 0, i);
	           	}
	               os.flush();
	               os.close();
	           } catch (IOException e) {  
	               e.printStackTrace();  
	           }finally {
	        	   try {
					if (bis != null) {
						  bis.close();
					   }
					if (os!=null) {
					      os.close();
					   } 
				    if (file != null) {
						  file.delete();
					   }
				} catch (IOException e) {
					e.printStackTrace();
				}
	       }
	}
}
