package com.cloud.dips.raus.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 日志表
 *
 * @author BigPan
 * @date 2018-12-04 16:48:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_web_log")
public class GovWebLog extends Model<GovWebLog> {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/**
	  * 用户id
	 */
	@TableField("user_id")
	private Long userId;
	
	/**
	  * 用户名称
	 */
	@TableField("user_name")
	private String userName;
	
	/**
	  * 用户操作
	 */
	@TableField("operation_type")
	private String operationType;
	
	/**
	  * 用户操作的详细信息
	 */
	@TableField("operation_detail")
	private String operationDetail;
	
	
	/**
	  * 真实IP地址
	 */
	@TableField("real_ip")
	private String realIp;
	
	/**
	  * 代理ip地址
	 */
	@TableField("agent_ip")
	private String agentIp;
	
	/**
	 * ip所属地
	 */
	@TableField("Territory_ip")
	private String territoryIp;
	
	/**
	  * 执行时间
	 */
	@TableField("time")
	private String time;
	
	/**
	  * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;
	
	/**
	  * 更新时间
	 */
	@TableField(value = "modified_time", update = "now()")
	private LocalDateTime modifiedTime;
	
	/**
	  * 删除标记
	 */
	@TableField("is_deleted")
	private String deleted;
	
	/**
	  * 异常信息
	 */
	@TableField("exception")
	private String exception;
	
	/**
	  * 状态
	 */
	@TableField("status")
	private String status;
	
	/**
	  * 方法的路径
	 */
	@TableField("method")
	private String method;
	
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
  
  
  
}
