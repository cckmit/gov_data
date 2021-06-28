package com.cloud.dips.raus.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.dto.UserApplicationDTO;
import com.cloud.dips.raus.api.entity.UserApplication;
import com.cloud.dips.raus.api.vo.UserApplicationVO;

/**
 * 
 *
 * @author BigPan
 * @date 2019-02-17 08:45:31
 */
public interface UserApplicationService extends IService<UserApplication> {
	
	 /**
	  * 用户申请共享数据库的主题和字段
	  */
	 public String applyApiInformation(UserApplicationDTO userApplicationDTO);
	 /**
	  * 查找用户申请的数据库信息
	  */
	  public Page<UserApplicationVO> selectApplicationPage(Query query);
}

