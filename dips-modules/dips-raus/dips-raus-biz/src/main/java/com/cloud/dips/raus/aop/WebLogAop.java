package com.cloud.dips.raus.aop;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.raus.api.entity.GovWebLog;
import com.cloud.dips.raus.service.GovWebLogService;

	/**
	 * 
	 * @author johan
	 * @Date 2018年12月5日
	 * @Company 佛山司马钱技术有限公司
	 * @description 通过aop切面记录日志
	 */
	@Aspect
	@Component
    public class WebLogAop {
	
		private String description;
		private String operationType;
		private GovWebLog govWebLog = new GovWebLog();
	
		@Autowired
		private GovWebLogService govWebLogService;
	
	
	/**
	  * 定义切入点，切入点为UserLogRecord的注解
     */
    @Pointcut("@annotation(com.cloud.dips.raus.aop.UserLogRecord)")
    public void recordUserLog(){}

    /**
  	  * 在方法执行之后插入日志
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(returning="result", pointcut="recordUserLog()")
    public void afterReturn(JoinPoint joinPoint, Object result) throws Throwable {
    	
    	 govWebLog.setStatus("成功");
    	 govWebLog.setException("");
         insertUserLog(joinPoint);
    }

    /**
          * 方法抛出异常时执行
     * @param joinPoint
     * @param throwable
     * @throws Throwable
     */
    @AfterThrowing(pointcut="recordUserLog()", throwing="throwable")
    public void afterThrow(JoinPoint joinPoint, Throwable throwable) throws Throwable {
    	
    	govWebLog.setStatus("失败");
    	govWebLog.setException(throwable.toString());
    	insertUserLog(joinPoint);
    	
    }
    
    /**
          *  异常方法和正常插入的公共方法
     * @param joinPoint
     */
	private void insertUserLog(JoinPoint joinPoint) {
		/* 记录开始时间*/
		long startTime = System.currentTimeMillis();
		// 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();  
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取注解中的操作方式
        if(method!=null&&!"".equals(method)){
            // 获取自定义注解操作
        	UserLogRecord  userLogRecord = method.getAnnotation(UserLogRecord.class);
            // 获取用户操作方式
        	description = userLogRecord.description();
            // 获取用户操作内容
        	operationType = userLogRecord.operationType();
        }

        JSONObject allInformation = govWebLogService.getAllInformation(request);
//        request.getAttribute("userId");
//        request.getAttribute("userName");
        
        // 查找方法所在的路径
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        // 查找方法名
        String name = joinPoint.getSignature().getName();
        // 将路径和方法名合并
        StringBuilder builder = new StringBuilder(typeName);
        builder.append(".");
        builder.append(name);
        String methodPath = builder.toString();
        
//        String username = SecurityUtils.getUser().getUsername();
//        long userId = SecurityUtils.getUser().getId();
//        govWebLog.setUserId(userId);
//        govWebLog.setUserName(username);
        govWebLog.setUserId(1L);
        govWebLog.setMethod(methodPath);
        govWebLog.setAgentIp(allInformation.getString("agentIp"));
        govWebLog.setRealIp(allInformation.getString("realIp"));
        govWebLog.setTerritoryIp(allInformation.getString("ipLocation"));
        govWebLog.setOperationDetail(description);
        govWebLog.setOperationType(operationType);
        govWebLog.setCreateTime(LocalDateTime.now());
        govWebLog.setModifiedTime(LocalDateTime.now());  
        long endTime = System.currentTimeMillis();
        govWebLog.setTime((endTime-startTime)+"ms"); 
        govWebLogService.insert(govWebLog);
	}

}
