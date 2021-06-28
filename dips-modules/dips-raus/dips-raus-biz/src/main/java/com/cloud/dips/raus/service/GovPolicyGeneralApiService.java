package com.cloud.dips.raus.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.entity.GovPolicyGeneral;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.vo.GeneralApiVO;

/**
 * 通用政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:19:21
 */
public interface GovPolicyGeneralApiService extends IService<GovPolicyGeneral> {
    /**
	 * 查询通用API数据
	 */
    Page<GeneralApiVO> selectGeneralApi(Query query,PreposeUser one,String queryTime);
}

