package com.cloud.dips.gov.api.dto;


import java.util.List;

import com.cloud.dips.gov.api.entity.GovPolicySense;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author C.Z.H
 * 2019年1月3日
 *SenseDTO.java
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SenseDTO extends GovPolicySense{

	/**
	 * 标签
	 */
	private List<String> tagList;

}
