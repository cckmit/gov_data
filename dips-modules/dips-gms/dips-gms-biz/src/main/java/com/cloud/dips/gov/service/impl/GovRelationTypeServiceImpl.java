package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.entity.GovRelationType;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.GovRelationTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author C.Z.H
 */
@Service
@AllArgsConstructor
public class GovRelationTypeServiceImpl extends ServiceImpl<GovRelationTypeMapper, GovRelationType> implements GovRelationTypeService {

	private final GovRelationTypeMapper mapper;

	@Override
	public GovRelationType selectByNumber(String number) {
		return mapper.selectByNumber(number);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<GovRelationType> selectAllPage(Query query) {
		Object name = query.getCondition().get("name");
		Object number = query.getCondition().get("number");
		query.setRecords(mapper.selectAllPage(query, name, number));
		return query;
	}
}
