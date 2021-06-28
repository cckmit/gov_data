/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovRelationType;

/**
 * 
 * @author C.Z.H
 * 2019年1月3日
  *   关联类型
 */
public interface GovRelationTypeService extends IService<GovRelationType> {

	GovRelationType selectByNumber(String number);

	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Page<GovRelationType> selectAllPage(Query query);
}
