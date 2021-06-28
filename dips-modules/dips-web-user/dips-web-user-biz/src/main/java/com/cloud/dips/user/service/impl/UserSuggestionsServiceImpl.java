package com.cloud.dips.user.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.user.api.entity.UserSuggestions;
import com.cloud.dips.user.api.vo.UserSuggestionsVO;
import com.cloud.dips.user.mapper.UserSuggestionsMapper;
import com.cloud.dips.user.service.UserSuggestionsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinzan
 * @ClassName: UserSuggestionsServiceImpl
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/2下午4:53
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserSuggestionsServiceImpl extends ServiceImpl<UserSuggestionsMapper, UserSuggestions> implements UserSuggestionsService {
	@Autowired
	private UserSuggestionsMapper mapper;

	@Override
	public Page<UserSuggestionsVO> selectPageByTagId(Query query) {
		Object userId = query.getCondition().get("userId");
		query.setRecords(mapper.selectById(query, userId));

		return query;
	}
}
