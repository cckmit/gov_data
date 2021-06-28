package com.cloud.dips.gov.api.dto;

import java.util.List;

import com.cloud.dips.gov.api.entity.GovInformation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author C.Z.H
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InformationDTO extends GovInformation{

	/**
	 * 标签
	 */
	private List<String> tagList;


	/**
	 * 地域
	 */
	private String region;
	/**
	 * 地域编码
	 */
	private List<String> regionArr;
}
