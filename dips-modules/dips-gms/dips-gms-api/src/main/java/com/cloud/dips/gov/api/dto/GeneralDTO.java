package com.cloud.dips.gov.api.dto;

import java.util.List;

import com.cloud.dips.gov.api.entity.GovPolicyGeneral;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Wang Jiadong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralDTO extends GovPolicyGeneral{
	/**
	 * 发文单位（关联机构）
	 */
	private List<Integer> dispatchList;
	/**
	 * 联合发文单位（关联机构）
	 */
	private List<Integer> unionList;
	/**
	 * 政策依据（关联政策）
	 */
	private List<Integer> policyList;
	/**
	 * 适用区域（关联地域）
	 */
	private List<Integer> regionList;
	/**
	 * 标签（关联标签）
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
