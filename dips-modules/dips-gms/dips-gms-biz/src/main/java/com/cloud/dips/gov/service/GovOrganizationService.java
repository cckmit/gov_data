package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.dto.OrganizationDTO;
import com.cloud.dips.gov.api.entity.GovOrganization;

/**
 * 机构模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:04:59
 */
public interface GovOrganizationService extends IService<GovOrganization> {
	/**
	 * 保存
	 */
	R<Boolean> save(OrganizationDTO organizationDTO);

	/**
	 * 逻辑删除（包含批量）
	 */
	R<Boolean> deleteOrMore(Integer[] ids);

	/**
	 * 更新
	 */
	R<Boolean> update(OrganizationDTO organizationDTO);

	/**
	 * 查询
	 */
	Page selectOrLike(Query query);

	/**
	 * 根据标签ID查询
	 */
	Page selectPageByTagId(Query query);

	/**
	 * 保存返回entity
	 */
	GovOrganization insertReturn(GovOrganization organization);

	/**
	 * 回收站机构列表
	 */
	Page selectRecyclePage(Query<Object> query);

	/**
	 * 回收站彻底删除
	 */
	Boolean recycleDelete(Integer id);

	/**
	 * 查重
	 * */
	Boolean repeat(String title);
}
