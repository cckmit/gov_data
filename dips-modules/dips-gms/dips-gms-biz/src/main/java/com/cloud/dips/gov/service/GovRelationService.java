package com.cloud.dips.gov.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.gov.api.entity.GovRelation;

import java.util.Map;


/**
 * @author C.Z.H
 */
public interface GovRelationService extends IService<GovRelation> {

	Boolean saveRelation(Map<String, Object> params);

	Boolean deleteRelation(Map<String, Object> params);
}
