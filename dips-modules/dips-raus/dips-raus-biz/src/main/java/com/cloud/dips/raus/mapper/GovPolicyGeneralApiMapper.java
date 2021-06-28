package com.cloud.dips.raus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.entity.GovPolicyGeneral;
import com.cloud.dips.raus.api.entity.PreposeUser;
import com.cloud.dips.raus.api.pojo.GeneralApiPOJO;

/**
 * 通用政策模型
 *
 * @author yangyin
 * @date 2018-09-11 11:19:21
 */
public interface GovPolicyGeneralApiMapper extends BaseMapper<GovPolicyGeneral> {
	/**
	  * 查询通用API数据
	 */
	@SuppressWarnings("rawtypes")
	List<GeneralApiPOJO> selectGeneralApi(Query query,@Param("one") PreposeUser one,
			@Param("beforeMonth")String beforeMonth,@Param("currentMonth")String currentMonth);
	/**
	  *  查询正文
	 * @param generalApiId
	 * @return
	 */
	List<GeneralApiPOJO> selectGeneralText(@Param("generalApiId")List<Integer> generalApiId);
	/**
	  *  选择标签
	 * @param tagApiId
	 * @return
	 */
	List<GeneralApiPOJO> selectGeneralTag(@Param("tagApiId")List<Integer> tagApiId);
	/**
	  *  选择发文部门
	 * @param dispatchId
	 * @return
	 */
	List<GeneralApiPOJO> selectGeneralDispatch(@Param("dispatchId")List<Integer> dispatchId);
	/**
	  *  选择联合发文部门
	 * @param unionId
	 * @return
	 */
	List<GeneralApiPOJO> selectGeneralUnion(@Param("unionId")List<Integer> unionId);
}
