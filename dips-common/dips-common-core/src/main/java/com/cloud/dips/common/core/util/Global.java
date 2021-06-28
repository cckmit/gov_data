/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cloud.dips.common.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;

/**
 * 全局配置类
 * 
 * @author ThinkGem
 * @version 2014-06-25
 * 阿斯达
 */
public class Global {

    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */

    public static final String SESSION_SECURITY_CODE = "sessionSecCode";

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";

    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";

    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";

    public static final String FALSE = "false";

    /**
     * 删除标记（0：正常；1：删除；）
     */
    public static final int DEL_FLAG_NORMAL = 0;

    public static final int DEL_FLAG_DELETE = 1;

    /**
     * 审核标记（0：未审核；1：审核；）
     */
   /* public static final int AUDIT_FLAG_NO = 0;
    
    public static final int AUDIT_FLAG_YES = 1;*/
    
    
    
    /**
     * 状态(0：未发布，1：已发布未审核，2：已发布已审核，3：已发布审核不通过)
     */
    public static final int RELEASE_NO = 0;
    
    public static final int AUDIT_FLAG_NO = 1;
    
    public static final int AUDIT_FLAG_YES = 2;
    
    public static final int AUDIT_FLAG_NO1 = 3;
    
    /**
     * 受理状态(0:未受理    1:已受理)
     */
    
    public static final int NO_ACCEPT = 0;
    
    public static final int IS_ACCEPT = 1;
    
    /**
     * 部门角色ID
     */
    public static final int FIRST_DEPART_W = 131;
    
    public static final int FIRST_DEPART_E = 132;
    
    public static final int SECOND_DEPART_W = 6;
    
    public static final int SECOND_DEPART_E = 129;
    
    public static final int BOSS_DEPART = 1; //最终审核角色ID，待定
    
    public static final int Admin = 2;//管理员角色
    
    public static final int district  = 133;//区部门角色
    
    public static final int sys = 3;//系统查看员
    

    
    /**
     * 增/删/改
     */
    public static final String INSERT_SUCCESS = "保存成功！";

    public static final String INSERT_ERROR = "保存失败！";
    
    public static final String UPDATE_SUCCESS = "更新成功！";

    public static final String UPDATE_ERROR = "更新失败！";
    
    public static final String DELETE_SUCCESS = "删除成功！";

    public static final String DELETE_ERROR = "删除失败！";
    
    public static final String HANDLE_SUCCESS = "数据操作成功！";
    
    public static final String HANDLE_ERROR = "数据操作失败！";
    
    public static final String IMPORT_ERROR = "上传失败！";
    public static final String IMPORT_SUCCESS = "上传成功！";
    
    
    public static final String REDIS_KEY = "qxdata_REDIS_"; 
    
    public static final Integer REDIS_TIME = 60*30;

    
    /**
     * 数据库连接信息    
     */
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public static final String ORACLE_URL = "jdbc:oracle:thin:@%s:%s:%s";

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    public static final String MYSQL_URL = "jdbc:mysql://%s:%s/%s?useInformationSchema=true&useUnicode=true&characterEncoding=utf-8";
    
    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "/userfiles/";

    public static final String SESSION_USER = "sessionUser";  
    
    /**
     * 无业务事项
     */
    public static final String NO_BUSINESS_NAME = new String("无业务事项");
    
    /**
     * 获取当前对象实例
     */
    public static Global getInstance() {
        return global;
    }



    /**
     * 页面获取常量
     * 
     * @see {fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }


}
