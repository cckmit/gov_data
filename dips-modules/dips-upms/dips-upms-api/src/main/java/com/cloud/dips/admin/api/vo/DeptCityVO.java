package com.cloud.dips.admin.api.vo;

import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dingbin
 *
 */
@Data
public class DeptCityVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 部门ID
	 */
	private Integer id;
	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	private String name;
	/**
	 * 所属城市ID
	 */
	private Integer cityId;
	/**
	 * 所属城市名称
	 */
	private String cityName;
	/**
	 * 所属城市ID
	 */
	private Integer cityParentId;
}
