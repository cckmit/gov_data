package com.cloud.dips.theme.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户足迹表
 *
 * @author BigPan
 * @date 2018-12-12 09:42:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_footprint")
public class WebUserFootprint extends Model<WebUserFootprint> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/**
	 * web用户ID
	 */
	@TableField("web_user_id")
	private Integer webUserId;
	
	/**
	 * 政策对象ID
	 */
	@TableField("policy_id")
	private Integer policyId;
	
	/**
	 * 政策类型
	 */
	@TableField("policy_type")
	private String policyType;
	
	/**
	 * 0-正常，1-删除
	 */
	@TableField("is_deleted")
	private String deleted;
	
	/**
	 * 收藏时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;
	
	/**
	 * 修改时间
	 */
	@TableField("modified_time")
	private LocalDateTime modifiedTime;

	/**
	 * 政策标题
	 */
	@TableField("title")
	private String title;

	/**
	 * 是否已读
	 */
	@TableField("is_readed")
	private Integer isReaded;
	
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
