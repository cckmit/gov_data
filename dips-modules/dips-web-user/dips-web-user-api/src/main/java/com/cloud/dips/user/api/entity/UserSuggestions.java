package com.cloud.dips.user.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yinzan
 * @ClassName: UserSuggestions
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/2下午4:30
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_user_suggestions")
public class UserSuggestions extends Model<UserSuggestions> {
	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 创建者
	 */
	@TableField("user_id")
	private  Integer userId;

	/**
	 * 问题板块
	 */
	@TableField("block_id")
	private  Integer blockId;
	/**
	 * 问题性质
	 */
	@TableField("nature_id")
	private Integer natureId;
	/**
	 * 内容
	 */
	private  String  content;
	/**
	 * 管理员回复者
	 */
	@TableField("sys_username")
	private  String sysUsername;
	/**
	 * 回复者id
	 */
	@TableField(value = "responder_id")
	private  Integer responderId;
	/**
	 * 管理员回复内容
	 */
	@TableField("sys_content")
	private  String sysContent;
	/**
	 * 创建时间
	 */
	private LocalDateTime  create_time;
	/**
	 * 回复时间
	 */
	private LocalDateTime update_time;
	/**
	 * 0为未回复、1已回复
	 */
	private  Integer state;
	/**
	 * 提交用户名称
	 */
	private String username;





	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
