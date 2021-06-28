package com.cloud.gov.theme.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.gov.api.vo.SortDataVO;
import com.cloud.dips.theme.api.dto.SendEmailToClineDTO;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.vo.WebUserPushVO;

/**
 * 用户d订阅主题推送表
 *
 * @author BigPan
 * @date 2018-12-13 09:43:04
 */
@Mapper
public interface WebUserPushMapper extends BaseMapper<WebUserPush> {

	
	public void insertUniquePush(@Param("pushList") List<WebUserPush> pushList);
	
	/**
	 * 根据key number查询字典value，该方法可以提出来单独使用
	 */
	String selectDictLabelByKey(@Param("key") String key, @Param("number") String number);
	
	List<WebUserPushVO> queryPushByTheme(Query query,@Param("webUserId") Object webUserId, @Param("theme") Object theme);
	
	/**
	 * 搜索需要邮箱推送的用户
	 */
	List<SendEmailToClineDTO> selectEmailUser();
	/**
	 * 搜索用户的邮箱地址
	 */
	List<SendEmailToClineDTO> selectEmailAddress();
	/**
	 * 查询需要邮箱推送的用户id
	 */
	public List<Integer> selectPushUser();
	/**
	 * 根据用户订阅的标签查找对应的政策id
	 */
	List<Integer> selectRelationId(@Param("tagStatus") String tagStatus);
	/**
	 * 根据用户订阅的标签查找对应的政策id
	 */
	List<SortDataVO> selectPolicyById(Query query,@Param("policyId") List<Integer> policyId,@Param("beforeMonth") Object beforeMonth,
			@Param("currentMonth") Object currentMonth,@Param("mark") Object mark);
	/**
	 * 根据个人标签查找符合的政策
	 */
	List<SortDataVO> queryThemeForTwo(Query query,@Param("region") Object region,@Param("target") Object target,@Param("scale") Object scale,@Param("themeId")Object themeId,
			@Param("beforeMonth") Object beforeMonth,@Param("currentMonth") Object currentMonth,@Param("mark") Object mark);
}
