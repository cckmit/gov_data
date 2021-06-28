package com.cloud.dips.raus.api.dto;

import java.util.List;

import com.cloud.dips.raus.api.entity.GovWebLog;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author johan
 * @Date 2018年12月6日
 * @Company 佛山司马钱技术有限公司
 * @description 数据传输层
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRecrodDto extends GovWebLog{

	/**
	  *  用户日志列表
	 */
	private List<GovWebLog> govWebLogList;
}
