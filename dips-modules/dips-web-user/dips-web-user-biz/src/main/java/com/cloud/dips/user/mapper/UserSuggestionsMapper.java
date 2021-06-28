package com.cloud.dips.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.user.api.entity.UserSuggestions;
import com.cloud.dips.user.api.vo.UserSuggestionsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yinzan
 * @ClassName: UserSuggestionsMapper
 * @ProjectName gov-cloud
 * @Description: TODO
 * @date 2019/1/2下午4:57
 */
@Mapper
public interface UserSuggestionsMapper extends BaseMapper<UserSuggestions> {
	/**
	 * 分页查询
	 *
	 * @param query
	 * @param userId
	 * @return
	 */
	List<UserSuggestionsVO> selectById(@Param("query")Query query, @Param("userId") Object userId);
}
