package com.cloud.dips.user.api.constant;

/**
 * @author Wilson
 * @date 2017/10/29
 */
public interface UserConstant {
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

	

}
