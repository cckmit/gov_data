package com.cloud.dips.common.log.enmu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author yinzan
 * @ClassName: EnumRole
 * @ProjectName dips
 * @Description: TODO
 * @date 2019/3/28上午10:05
 */
@Getter
@AllArgsConstructor
public enum  EnumRole
{
	/**
	 * 后端账号
	 */
	USER_TYPE(0L),
	/**
	 * 前端账号
	 */
	WEB_TYE(1L);


	private  Long type;

}
