package com.cloud.dips.theme.api.constant;

/**
 * @author johan
 * @date 2018/12/15
 */
public interface ThemeConstant {
	/**
	 * 已做状态
	 */
	Integer IS_DO = 1;
	/**
	 * 未做状态
	 */
	Integer IS_NOT_DO = 0;
	/**
	 * 申报政策联合发文单位    联合 发文单位（其它模块）
	 */
	String DECLARE_UNION = "gov_declare_union";
	/**
	 * 申报政策适用区域    使用区域（其它模块）
	 */
	String DECLARE_CITY = "gov_declare_city";
	/**
	 * 申报政策关联政策    关联政策（其它模块）
	 */
	String DECLARE_GENERAL = "gov_declare_general";
	/**
	 * 通用政策	政策依据（其它模块）
	 */
	String GENERAL_NODE = "gov_general_policy";
	/**
	 * 通用政策发文单位
	 */
	String GENERAL_DISPATCH = "gov_general_dispatch";
	/**
	 * 通用政策联合发文单位
	 */
	String GENERAL_UNION = "gov_general_union";
	/**
	 * 通用政策适用区域
	 */
	String GENERAL_CITY = "gov_general_city";
	/**
	 * 政策资讯
	 */
	String GOV_INFORMATION = "gov_information";
	/**
	 * 机构
	 */
	String GOV_EXPLAIN_ORGANIZATION = "gov_explain_organization";
	/**
	 * 政策解读
	 */
	String GOV_EXPLAIN_NODE = "explain";
	/**
	 * 解读关联政策原文
	 */
	String GOV_EXPLAIN_GENERAL = "gov_explain_general";
	/**
	 * 机构Node
	 */
	String ORGANIZTION_NODE = "gov_organization";
}
