package com.cloud.dips.tag.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.Valid;

import com.cloud.dips.common.log.enmu.EnumRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.dto.GovTagDTO;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagMergeRecord;
import com.cloud.dips.tag.api.entity.GovTagModificationRecord;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.api.vo.UserTagVO;
import com.cloud.dips.tag.service.GovTagDescriptionService;
import com.cloud.dips.tag.service.GovTagMergeRecordService;
import com.cloud.dips.tag.service.GovTagModificationRecordService;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagService;
import com.cloud.dips.tag.service.GovTagTypeRelationService;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.HanLP;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/tag")
public class TagController {
	@Autowired
	private GovTagService service;
	
	@Autowired
	private GovTagDescriptionService govTagDescriptionService;
	
	@Autowired
	private GovTagRelationService govTagRelationService;
	
	@Autowired
	private GovTagModificationRecordService recordService;
	
	@Autowired
	private GovTagMergeRecordService mergeRecordService;
	
	@Autowired
	private GovTagRelationService relationService;
	
	@Autowired
	private GovTagTypeRelationService govTagTypeRelationService;
	
	
	
	/**
	 * 
	 * 通过ID查询标签
	 *
	 * @param id
	 * 
	 * @return 标签
	 * 
	 */
	@GetMapping("/{id}")
	@SysLog(value="查询标签详情",role = EnumRole.WEB_TYE)
	@ApiOperation(value = "查询标签详情", notes = "根据ID查询标签详情: params{标签ID: id}",httpMethod="GET")
	public GovTagVO tag(@PathVariable Integer id) {
		GovTagVO bean=service.selectGovTagVoById(id);
		bean.addTypeObjs();
		return bean;
	}
	
	/**
	 * 校验标签是否存在
	 * @param name 标签名称
	 * @param id 标签id
	 * @return
	 */
	@PostMapping("/check")
	@ApiOperation(value = "标签检验", notes = "标签检验",httpMethod="POST")
	public R<Boolean> check(@RequestBody Map<String, Object> params) {
		String name=params.getOrDefault("name", "").toString();
		Integer i=service.findByGovTagName(name);
			if(i<1){
				return new R<Boolean>(Boolean.TRUE);
			}else{
				return new R<Boolean>(Boolean.FALSE);
			}
	}	
	
	/**
	 * 分页查询标签
	 *
	 * @param params 参数集
	 * 
	 * @return 标签集合
	 */
	@GetMapping("/page")

	@ApiOperation(value = "分页查询标签", notes = "标签集合",httpMethod="GET")
	public Page<GovTagVO> tagPage(@RequestParam Map<String, Object> params) {
		String orderByField = "orderByField";
		//判断前后台
		String fob = "fob";
		if(StrUtil.isBlank(params.getOrDefault(orderByField, "").toString())){
			params.put(orderByField, "id");
		}
		if(StrUtil.isBlank(params.getOrDefault(fob, "").toString())){
			params.put(fob, "b");
		}
		return service.selectAllPage(new Query<>(params));
	}

	/**
	 * 删除标签
	 *
	 * @param id
	 * 
	 * @return Rviews
	 */
	@SysLog("删除标签")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('gov_tag_del')")
	@ApiOperation(value = "删除标签", notes = "根据ID删除标签: params{标签ID: tagId}",httpMethod="POST")
	public R<Boolean> tagDel(@PathVariable Integer id) {
		GovTag govTag = service.selectById(id);
		govTagTypeRelationService.deleteById(id);
		if(govTag==null){
			return new R<Boolean>(Boolean.FALSE);
		}else{
				govTagDescriptionService.deleteByTagId(govTag.getTagId());
				govTagRelationService.deleteById(govTag.getTagId());
				govTagRelationService.deleteTagRelation(govTag.getTagId(), "tag");
				EntityWrapper<GovTagModificationRecord> er=new EntityWrapper<GovTagModificationRecord>();
				er.eq("tag_id", govTag.getTagId());
				recordService.delete(er);
				EntityWrapper<GovTagMergeRecord> em=new EntityWrapper<GovTagMergeRecord>();
				em.eq("tag_id", govTag.getTagId()).or().eq("merge_id", govTag.getTagId());
				mergeRecordService.delete(em);
				return new R<Boolean>(service.deleteGovTagById(govTag));
		}
	}
	
	@SysLog("添加标签")
	@PostMapping("/create")
	@PreAuthorize("@pms.hasPermission('gov_tag_add')")
	@ApiOperation(value = "添加标签", notes = "添加标签", httpMethod = "POST")
	public R<Boolean> saveTag(@Valid @RequestBody GovTagDTO govTagDto) {
		Integer i=service.findByGovTagName(govTagDto.getName());
		if(i<1){
			GovTag govTag = new GovTag();
			BeanUtils.copyProperties(govTagDto, govTag);
			// 获取当前用户 
			DipsUser user = SecurityUtils.getUser();
			govTag.setCreatorId(user.getId());
			govTag=service.save(govTag,govTagDto.getTypeIds());
			String[] relationTags=govTagDto.getTagList();
			StringBuilder tagKeyWords=new StringBuilder();
			for(String relation:relationTags){
				tagKeyWords.append(relation+",");
			}
			Map<String, Object> params=new HashMap<String, Object>(0);
			params.put("relationId", govTag.getTagId());
			params.put("node", "tag");
			params.put("tagKeyWords", tagKeyWords.toString());
			relationService.saveTagRelation(params);
			return new R<Boolean>(Boolean.TRUE);
		}else{
			return new R<Boolean>(Boolean.FALSE,"标签已存在");
		}
		
	}	
	
	/**
	 * 更新标签浏览量
	 * @param id标签ID
	 * @return
	 */
	@PostMapping("/views/{id}")
	@SysLog(value="更新标签浏览量",role = EnumRole.WEB_TYE)
	@ApiOperation(value = "更新标签浏览量", notes = "更新标签浏览量", httpMethod = "POST")
	public R<Boolean> tagViews(@PathVariable String id) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		boolean matches = pattern.matcher(id).matches();
		if (matches) {
			GovTag govTag = service.selectById(id);
			if(govTag==null){
				return new R<Boolean>(Boolean.FALSE,"标签不存在");	
			}else{
				govTag.setViews(govTag.getViews()+1);
				return new R<Boolean>(service.updateById(govTag));	
			}	
		}
		return new R<>(Boolean.FALSE,"地址不允许含有非法字符，请查看其它页面！");
	}
	
	@SysLog("更新标签")
	@PostMapping("/update")
	@PreAuthorize("@pms.hasPermission('gov_tag_edit')")
	@ApiOperation(value = "更新标签", notes = "更新标签", httpMethod = "POST")
	public R<Boolean> updateTag(@Valid @RequestBody GovTagDTO govTagDto) {
		GovTag govTag = service.selectById(govTagDto.getTagId());
		if(StrUtil.equals(govTagDto.getName(), govTag.getName())){
			String[] relationTags=govTagDto.getTagList();
			StringBuilder tagKeyWords=new StringBuilder();
			for(String relation:relationTags){
				tagKeyWords.append(relation+",");
			}
			Map<String, Object> params=new HashMap<String, Object>(0);
			params.put("relationId", govTag.getTagId());
			params.put("node", "tag");
			params.put("tagKeyWords", tagKeyWords.toString());
			relationService.saveTagRelation(params);
			
			BeanUtils.copyProperties(govTagDto, govTag);
			govTag.setUpdateTime(new Date());
			govTagTypeRelationService.saveTagTypeRelation(govTag.getTagId(), govTagDto.getTypeIds());
			return new R<Boolean>(service.updateById(govTag));
		}else{
			Integer i=service.findByGovTagName(govTagDto.getName());
			i=0;
			if(i<1){
				DipsUser user = SecurityUtils.getUser();
				GovTagModificationRecord record=new GovTagModificationRecord();
				record.setCreatorId(user.getId());
				record.setTagId(govTag.getTagId());
				record.setDescription("“"+govTag.getName()+"”更名为“"+govTagDto.getName()+"”");
				recordService.insert(record);

				String[] relationTags=govTagDto.getTagList();
				StringBuilder tagKeyWords=new StringBuilder();
				for(String relation:relationTags){
					tagKeyWords.append(relation+",");
				}
				Map<String, Object> params=new HashMap<String, Object>(0);
				params.put("relationId", govTag.getTagId());
				params.put("node", "tag");
				params.put("tagKeyWords", tagKeyWords.toString());
				relationService.saveTagRelation(params);

				BeanUtils.copyProperties(govTagDto, govTag);
				govTag.setUpdateTime(new Date());
				govTagTypeRelationService.saveTagTypeRelation(govTag.getTagId(), govTagDto.getTypeIds());
				return new R<Boolean>(service.updateById(govTag));
			}else{
				return new R<Boolean>(Boolean.FALSE,"标签已存在！");
			}	
		}
	}
	
	@PostMapping("/review")
	@PreAuthorize("@pms.hasPermission('gov_tag_edit')")
	@ApiOperation(value = "批量审核标签", notes = "批量审核标签", httpMethod = "POST")
	public R<Boolean> review(@RequestBody(required=false) List<Integer> ids) {
		if(null != ids && ids.size()>0){
			EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
			e.in("id", ids);
			return new R<Boolean>(service.updateForSet("status = 1", e));
		}else{
			return new R<Boolean>(Boolean.FALSE,"请选择要审核的标签！");
		}
	}
	
	@PostMapping("/disable")
	@PreAuthorize("@pms.hasPermission('gov_tag_edit')")
	@ApiOperation(value = "批量禁用标签", notes = "批量禁用标签", httpMethod = "POST")
	public R<Boolean> disable(@RequestBody(required=false) List<Integer> ids) {
		if(null != ids && ids.size()>0){
			EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
			e.in("id", ids);
			return new R<Boolean>(service.updateForSet("status = 0", e));
		}else{
			return new R<Boolean>(Boolean.FALSE,"请选择要禁用的标签！");
		}
	}
	
	@SysLog("批量删除标签")
	@PostMapping("/delete")
	@PreAuthorize("@pms.hasPermission('gov_tag_del')")
	@ApiOperation(value = "批量删除标签", notes = "批量删除标签", httpMethod = "POST")
	public R<Boolean> delete(@RequestBody List<Integer> ids) {
		for(Integer id:ids){
			GovTag govTag = service.selectById(id);
			govTagTypeRelationService.deleteById(id);
			if(govTag!=null){
					govTagDescriptionService.deleteByTagId(govTag.getTagId());
					govTagRelationService.deleteById(govTag.getTagId());
					govTagRelationService.deleteTagRelation(govTag.getTagId(), "tag");
					EntityWrapper<GovTagModificationRecord> er=new EntityWrapper<GovTagModificationRecord>();
					er.eq("tag_id", govTag.getTagId());
					recordService.delete(er);
					EntityWrapper<GovTagMergeRecord> em=new EntityWrapper<GovTagMergeRecord>();
					em.eq("tag_id", govTag.getTagId()).or().eq("merge_id", govTag.getTagId());
					mergeRecordService.delete(em);
					service.deleteGovTagById(govTag);
			}
		}
		return new R<Boolean>(Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@SysLog("标签合并")
	@PostMapping("/merge")
	@ApiOperation(value = "标签合并", notes = "标签合并", httpMethod = "POST")
	public R<Boolean> tagMerge(@RequestBody Map<String, Object> param) {
		Integer mainId=(Integer) param.get("mainId");
		List<Integer> mergeIds=(List<Integer>) param.get("mergeIds");
		GovTag mainTag = service.selectById(mainId);
		if (mainTag.getStatus() == 1 && mainTag.getEnable() == 1) {
			for (Integer mergeId : mergeIds) {
				GovTag mergeTag = service.selectById(mergeId);
				// 更新标签的关联表
				EntityWrapper<GovTagRelation> govTagRelation = new EntityWrapper<GovTagRelation>();
				govTagRelation.eq("tag_id", mergeId);
				govTagRelationService.updateForSet("tag_id="+"\""+mainId+"\"",govTagRelation);
				// 将被合并的标签设置为禁用
				EntityWrapper<GovTag> govTag = new EntityWrapper<GovTag>();
				govTag.eq("id", mergeId);
				service.updateForSet("status=0",govTag);
				// 插入合并的标签记录关系表
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tag_id", mainId);
				map.put("merge_id", mergeId);
				List<GovTagMergeRecord> selectByMap = mergeRecordService.selectByMap(map);
				if (selectByMap.size() == 0) {
					GovTagMergeRecord govTagMergeRecord = new GovTagMergeRecord();
					govTagMergeRecord.setTagId(mainId);
					govTagMergeRecord.setMergeId(mergeId);
					mergeRecordService.insert(govTagMergeRecord);
				}
				// 插入合并标签记录日志吧
				Integer userId = SecurityUtils.getUser().getId();
				GovTagModificationRecord record=new GovTagModificationRecord();
				record.setCreatorId(userId);
				record.setTagId(mainId);
				record.setDescription("“"+mainTag.getName()+"”与“"+mergeTag.getName()+"”合并");
				recordService.insert(record);
				// 更新标签合并后的应用次数
				EntityWrapper<GovTagRelation> tagRelation = new EntityWrapper<GovTagRelation>();
				tagRelation.eq("tag_id", mainId);
				int refersCount = govTagRelationService.selectCount(tagRelation);
				GovTag tag = new GovTag();
				tag.setRefers(refersCount);
				tag.setTagId(mainId);
				service.updateById(tag);
			}
			return new R<Boolean>(true);
		}
		
		return new R<Boolean>(false);
	}
	

	@GetMapping("/extract_tag")
	@ApiOperation(value = "提取标签", notes = "根据内容提取标签", httpMethod = "GET")
	public List<String> extractTag(@RequestParam String content,@RequestParam Integer num) {
		List<String> keywordList = HanLP.extractKeyword(content, num);
		return keywordList;
	}
	
	@GetMapping("/assn_tag")
	@ApiOperation(value = "联想标签", notes = "联想标签", httpMethod = "GET")
	public List<Object> assnTag(@RequestParam String keyWord) {
		EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
		e.setSqlSelect("name");
		e.like("name", keyWord);
		return service.selectObjs(e);
	}

	/**
	 * 获取所有的标签（web端用户订阅选择用）
	 */
	@GetMapping("/selectAllTags")
	@ApiOperation(value = "获取所有的标签（web端用户订阅选择用）", notes = "获取所有的标签（web端用户订阅选择用）", httpMethod = "GET")
	public List<UserTagVO> selectAllTags(@RequestParam String q){
		return service.selectAllTags(q);
	}
	
	/**
	 * 根据ids获取标签名字
	 */
	@GetMapping("/selectTagsByIds")
	public List<UserTagVO> selectTagsByIds(String ids){
		return service.selectTagsByIds(ids);
	}
}
