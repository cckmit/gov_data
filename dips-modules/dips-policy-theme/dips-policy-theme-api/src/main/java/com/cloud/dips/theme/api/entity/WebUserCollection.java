package com.cloud.dips.theme.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户收藏表
 *
 * @author BigPan
 * @date 2018-12-11 10:56:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_collection")
public class WebUserCollection extends Model<WebUserCollection> {
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
	 * 政策类型-对应表【1 通用政策|2 申报政策|3 政策解读|4 政策资讯|5 政策常识】
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
	 * 标题
	 */
	@TableField("title")
	private String title;

	/**
	 * 收藏id
	 */
	@Transient
	private List<Integer> collectionId;
  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
