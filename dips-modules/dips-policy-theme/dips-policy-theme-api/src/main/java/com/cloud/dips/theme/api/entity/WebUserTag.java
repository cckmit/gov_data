package com.cloud.dips.theme.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户个个标签表
 *
 * @author BigPan
 * @date 2018-12-11 10:56:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("web_user_tag")
public class WebUserTag extends Model<WebUserTag> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/**
	 * web用户ID
	 */
	@TableField("user_id")
	private Integer userId;
	
	/**
	 * 政策对象ID
	 */
	@TableField("tag_id")
	private Integer tagId;
	

	
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
	 * 标签的key
	 */
	@TableField("tag_key")
	private  String tagKey;


  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
