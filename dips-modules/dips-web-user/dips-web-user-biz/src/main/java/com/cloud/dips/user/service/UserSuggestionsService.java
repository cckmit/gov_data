package com.cloud.dips.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.user.api.entity.UserSuggestions;
import com.cloud.dips.user.api.vo.UserSuggestionsVO;

/**
 * @author yinzan
 * @ClassName: UserSuggestionsService
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/2下午4:53
 */
public interface UserSuggestionsService extends IService<UserSuggestions> {

	/**
	 * 分页查询用户建议
	 * @param query
	 * @return
	 */
	Page<UserSuggestionsVO> selectPageByTagId(Query query);
}
