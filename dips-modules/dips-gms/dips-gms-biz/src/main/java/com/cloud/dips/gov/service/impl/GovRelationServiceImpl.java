package com.cloud.dips.gov.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.gov.api.entity.GovRelation;
import com.cloud.dips.gov.api.entity.GovRelationType;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.GovRelationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author C.Z.H
 */
@Slf4j
@Service
@AllArgsConstructor
public class GovRelationServiceImpl extends ServiceImpl<GovRelationMapper, GovRelation> implements GovRelationService {

	private final GovRelationMapper relationMapper;
	private final GovRelationTypeMapper relationTypeMapper;

	/**
	 * 添加、修改关联关系
	 */
	@Override
	public Boolean saveRelation(Map<String, Object> params) {
		if (StrUtil.isBlank(params.getOrDefault("relationId", "").toString())
			|| StrUtil.isBlank(params.getOrDefault("node", "").toString())) {
			return Boolean.FALSE;
		} else {
			String[] correlationIds = params.get("correlationIds").toString().split(",");
			Integer relationId = Integer.parseInt(params.get("relationId").toString());
			String node = params.get("node").toString();
			String number = params.get("number").toString();
			GovRelationType relationType = relationTypeMapper.selectByNumber(number);
			if (relationType != null) {
				EntityWrapper<GovRelation> e = new EntityWrapper<GovRelation>();
				e.where("relation_id = {0}", relationId).where("node = {0}", node).where("type_id = {0}",
					relationType.getId());
				relationMapper.delete(e);
				for (String correlationId : correlationIds) {
					GovRelation relation = new GovRelation();
					relation.setNode(node);
					relation.setTypeId(relationType.getId());
					relation.setRelationId(relationId);
					if (correlationId != null) {
						relation.setCorrelationId(Integer.parseInt(correlationId));
					}
					EntityWrapper<GovRelation> ew = new EntityWrapper<>();
					ew.where("relation_id = {0}", relationId).where("node = {0}", node)
						.where("type_id = {0}", relationType.getId())
						.where("correlation_id = {0}", correlationId);
					if (this.selectOne(ew) == null) {
						relationMapper.insert(relation);
					}
				}
			}
			return Boolean.TRUE;
		}
	}

	@Override
	public Boolean deleteRelation(@RequestParam Map<String, Object> params) {
		if (StrUtil.isBlank(params.getOrDefault("relationId", "").toString())
			|| StrUtil.isBlank(params.getOrDefault("node", "").toString())) {
			return Boolean.FALSE;
		} else {
			Integer relationId = Integer.parseInt(params.get("relationId").toString());
			String node = params.get("node").toString();
			EntityWrapper<GovRelation> e = new EntityWrapper<>();
			e.where("relation_id = {0}", relationId).where("node = {0}", node);
			relationMapper.delete(e);
			return Boolean.TRUE;
		}
	}

}
