package com.cloud.dips.user.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yinzan
 * @ClassName: UserSuggestionsVO
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/3上午9:47
 */
@Data
public class UserSuggestionsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 问题板块
	 */
	private Integer blockId;
	/**
	 * 问题性质
	 */
	private Integer natureId;


	/**
	 * 内容
	 */
	private String content;
	/**
	 * 管理员回复者
	 */
	private String sysUsername;

	/**
	 * 创建时间
	 */
	private LocalDateTime create_time;
	/**
	 * 回复时间
	 */
	private LocalDateTime update_time;
	/**
	 * 0为未回复、1已回复
	 */
	private Integer state;

	/**
	 * 管理员回复内容
	 */
	private String sysContent;
}
