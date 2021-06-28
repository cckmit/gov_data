package com.cloud.dips.gov.api.dto;

import java.util.List;

import com.cloud.dips.gov.api.entity.GovPolicyExplain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author C.Z.H
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExplainDTO extends GovPolicyExplain{

	/**
	 * 政策原文
	 */
	private List<Integer> policyList;

	/**
	 * 机构
	 */
	private List<Integer> organizationList;

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
