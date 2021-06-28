package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.OrganizationDTO;
import com.cloud.dips.gov.api.entity.GovOrganization;
import com.cloud.dips.gov.api.vo.OrganizationVO;
import com.cloud.dips.gov.mapper.GovOrganizationMapper;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.service.GovOrganizationService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */

@Service("govOrganizationService")
@AllArgsConstructor
public class GovOrganizationServiceImpl extends ServiceImpl<GovOrganizationMapper, GovOrganization> implements GovOrganizationService {

	private final RemoteTagRelationService remoteTagRelationService;
	private final GovOrganizationMapper govOrganizationMapper;
	private final GovRelationMapper govRelationMapper;

	/**
	 * 新增
	 */
	@Override
	public R<Boolean> save(OrganizationDTO organizationDTO) {
		GovOrganization govOrganization = new GovOrganization();
		// 主表存储
		BeanUtils.copyProperties(organizationDTO, govOrganization);
		govOrganization.setCreatorId(SecurityUtils.getUser().getId());
		govOrganization.setCreateTime(new Date());
		govOrganization.setDelFlag(CommonConstant.STATUS_NORMAL);
		this.insert(govOrganization);

		// 合同标签添加(feign)
		if (organizationDTO.getTagList() != null) {
			Map<String, Object> organizationTag = new HashMap<>(0);
			organizationTag.put("relationId", govOrganization.getId());
			organizationTag.put("node", "gov_organization");
			organizationTag.put("tagKeyWords", getTagKeyWords(organizationDTO.getTagList()));
			remoteTagRelationService.saveTagRelation(organizationTag);
		}
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 批量逻辑删除
	 */
	@Override
	public R<Boolean> deleteOrMore(Integer[] ids) {
		// 逻辑删除主表
		GovOrganization govOrganization = new GovOrganization();
		govOrganization.setDelFlag(CommonConstant.STATUS_DEL);
		this.update(govOrganization, new EntityWrapper<GovOrganization>().in("id", ids));
		return new R<>(true);
	}

	/**
	 * 修改
	 */
	@Override
	public R<Boolean> update(OrganizationDTO organizationDTO) {
		GovOrganization govOrganization = new GovOrganization();
		// 主表存储
		BeanUtils.copyProperties(organizationDTO, govOrganization);
		this.insertOrUpdate(govOrganization);

		// 合同标签添加(feign)
		Map<String, Object> organizationTag = new HashMap<>(0);
		organizationTag.put("relationId", govOrganization.getId());
		organizationTag.put("node", "gov_organization");
		organizationTag.put("tagKeyWords", getTagKeyWords(organizationDTO.getTagList()));
		remoteTagRelationService.saveTagRelation(organizationTag);

		return new R<>(Boolean.TRUE);
	}

	/**
	 * 查询
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page selectOrLike(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("name", query.getCondition().get("name"));
		map.put("level", query.getCondition().get("level"));
		map.put("username", query.getCondition().get("username"));
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
        map.put("prop", query.getCondition().get("prop"));
        map.put("order", query.getCondition().get("order"));
		query.setRecords(govOrganizationMapper.selectOrPage(query, map));
		return query;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<OrganizationVO> selectPageByTagId(Query query) {
		int tagId = (int) query.getCondition().get("tagId");
		query.setRecords(govOrganizationMapper.selectByTagId(query, tagId));
		return query;
	}

	@Override
	public GovOrganization insertReturn(GovOrganization organization) {
		this.insert(organization);
		return organization;
	}

	/**
	 * 回收站彻底删除
	 */
	@Override
	public Boolean recycleDelete(Integer id) {
		// 删除tag关联
		Map<String, Object> organizationTag = new HashMap<>(0);
		organizationTag.put("relationId", id);
		organizationTag.put("node", "gov_organization");
		remoteTagRelationService.deleteTagRelation(id,"gov_organization");

		// 删除被关联(通用政策联合发文单位)
		govRelationMapper.deleteBeRelation(GovConstant.GENERAL_UNION, GovConstant.GOV_EXPLAIN_ORGANIZATION, id);
		// 删除被关联(政策解读发文单位)
		govRelationMapper.deleteBeRelation(GovConstant.DECLARE_DISPATCH, GovConstant.GOV_EXPLAIN_ORGANIZATION, id);
		// 删除被关联(政策解读联合发文单位)
		govRelationMapper.deleteBeRelation(GovConstant.DECLARE_UNION, GovConstant.GOV_EXPLAIN_ORGANIZATION, id);
		// 删除被关联(通用政策发文单位)
		govRelationMapper.deleteBeRelation(GovConstant.GENERAL_DISPATCH, GovConstant.GOV_EXPLAIN_ORGANIZATION, id);

		govOrganizationMapper.deleteById(id);
		return Boolean.TRUE;
	}

	/**
	 * 回收站机构列表
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page selectRecyclePage(Query query) {
		Map<String, Object> map = new HashMap<>(0);
		map.put("name", query.getCondition().get("name"));
		map.put("username", query.getCondition().get("username"));
		query.setRecords(govOrganizationMapper.selectRecyclePage(query, map));
		return query;
	}

	@Override
	public Boolean repeat(String title) {
		List<GovOrganization> list = govOrganizationMapper.repeat(title);
		if (list != null && list.size() > 0) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	/**
	 * 字符串拼接
	 *
	 * @param tags
	 * @return
	 */
	private String getTagKeyWords(List<String> tags) {
		StringBuilder tagKeyWords = new StringBuilder();
		if (tags != null) {
			Set<String> set = new HashSet<>(tags);
			String[] godtagNames = set.toArray(new String[0]);
			for (int i = 0; i < godtagNames.length; i++) {
				if (i != godtagNames.length - 1) {
					tagKeyWords.append(godtagNames[i]).append(",");
				} else {
					tagKeyWords.append(godtagNames[i]);
				}
			}
		}
		return tagKeyWords.toString();
	}
}
