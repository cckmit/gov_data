package com.cloud.gov.theme.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.feign.RemoteUserService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.cloud.dips.tag.api.vo.UserTagVO;
import com.cloud.dips.theme.api.dto.WebUserContactDTO;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.vo.WebusercontactVo;
import com.cloud.gov.theme.service.WebUserContactService;

import io.swagger.annotations.ApiOperation;


/**
 * @author BigPan
 * @date 2018-12-07 10:33:33
 */
@RestController
@RequestMapping("/webusercontact")
public class WebUserContactController {
	@Autowired
	private WebUserContactService webUserContactService;

	@Autowired
	private RemoteDictService remoteDictService;
	
	@Autowired
	private RemoteUserService remoteUserService;
	@Autowired
	private RemoteTagRelationService remoteTagRelationService;


	/**
	 * 主题列表
	 *
	 * @param
	 * @return
	 */
	@GetMapping("/themepage")
	@ApiOperation(value = "用户主题列表", notes = "用户主题列表")
	public Page<DictValueVO> themeNumbers() {
		Page<DictValueVO> page = new Page<DictValueVO>();
		WebUserContact webUserContact = new WebUserContact();
		Integer userId = SecurityUtils.getUser().getId();
		webUserContact.setWebUserId(userId);
		WebUserContact selectOne = webUserContactService.selectOne(new EntityWrapper<>(webUserContact));
		if (null == selectOne) {
			return page;
		}
		if (StringUtils.isBlank(selectOne.getDictValueId()) && selectOne.getType() == 0) {
			return page;
		}
		
		if (selectOne.getType() == 0) {
		String dictValueId = selectOne.getDictValueId();
		String[] themeId = dictValueId.split(",");
		if (themeId.length == 1) {
			List<DictValueVO> themeValue = remoteDictService.getlistDictValue(Integer.valueOf(themeId[0]));
			page.setRecords(themeValue);
			return page;
		}

		List<DictValueVO> themeList = new ArrayList<DictValueVO>();
		List<Integer> covertId = new ArrayList<Integer>();
		for (String integer : themeId) {
			covertId.add(Integer.valueOf(integer));
		}
		for (Integer integer : covertId) {
			List<DictValueVO> themeValueList = remoteDictService.getlistDictValue(integer);
			themeList.addAll(themeValueList);
		}
		for (DictValueVO dictValueVO : themeList) {
			dictValueVO.setType(selectOne.getType());
		}
		page.setRecords(themeList);
		return page;
		}
		String tagStatus = selectOne.getTagStatus();
		List<UserTagVO> selectTagsByIds = remoteTagRelationService.selectTagsByIds(tagStatus);
		List<DictValueVO> themeList = new ArrayList<DictValueVO>();
			for (UserTagVO userTagVO : selectTagsByIds) {
				DictValueVO dictValueVO = new DictValueVO();
				dictValueVO.setKey(String.valueOf(userTagVO.getTagId()));
				dictValueVO.setValue(userTagVO.getName());
				dictValueVO.setType(selectOne.getType());
				themeList.add(dictValueVO);
			}
		page.setRecords(themeList);
		return page;
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id) {
		WebUserContact webUserContact = webUserContactService.selectById(id);
		return new R<>(webUserContact);
	}

	/**
	 * 查询用户主题信息
	 *
	 * @return
	 */
	@GetMapping("/getcontact")
	public R<WebUserContactDTO> getcontact(String webId) {
		WebUserContact webUserContact = new WebUserContact();
		if (StringUtils.isBlank(webId)) {
			webUserContact.setWebUserId(SecurityUtils.getUser().getId());		
		}else {
			int userId = Integer.parseInt(webId);
			webUserContact.setWebUserId(userId);	
		}
		WebUserContactDTO webUserContactDTO = new WebUserContactDTO();
		webUserContact = webUserContactService.selectOne(new EntityWrapper<>(webUserContact));
		if (null != webUserContact) {
			String[] copyOf = Arrays.copyOf(StringUtils.split(webUserContact.getDictValueId(), ","), StringUtils.split(webUserContact.getDictValueId(), ",").length);
			Integer[] convert = (Integer[])ConvertUtils.convert(copyOf, Integer.class);
			webUserContactDTO.setTheme(convert);
			webUserContactDTO.setTarget(Arrays.copyOf(StringUtils.split(webUserContact.getTarget(), ","), StringUtils.split(webUserContact.getTarget(), ",").length));
			webUserContactDTO.setDepartment(Arrays.copyOf(StringUtils.split(webUserContact.getDepartment(), ","), StringUtils.split(webUserContact.getDepartment(), ",").length));
			webUserContactDTO.setIndustry(Arrays.copyOf(StringUtils.split(webUserContact.getIndustry(), ","), StringUtils.split(webUserContact.getIndustry(), ",").length));
			webUserContactDTO.setLevel(Arrays.copyOf(StringUtils.split(webUserContact.getLevel(), ","), StringUtils.split(webUserContact.getLevel(), ",").length));
			webUserContactDTO.setRegion(Arrays.copyOf(StringUtils.split(webUserContact.getRegion(), ","), StringUtils.split(webUserContact.getRegion(), ",").length));
			webUserContactDTO.setScale(Arrays.copyOf(StringUtils.split(webUserContact.getScale(), ","), StringUtils.split(webUserContact.getScale(), ",").length));
			List<UserTagVO> tagStatus = remoteTagRelationService.selectTagsByIds(webUserContact.getTagStatus());
			webUserContactDTO.setTagStatus(tagStatus);
			webUserContactDTO.setKeys(Arrays.copyOf(StringUtils.split(webUserContact.getDictValueKey(), ","), StringUtils.split(webUserContact.getDictValueKey(), ",").length));
			webUserContactDTO.setRegionName(Arrays.copyOf(StringUtils.split(webUserContact.getRegionName(), ","), StringUtils.split(webUserContact.getRegionName(), ",").length));
			webUserContactDTO.setType(webUserContact.getType());
			webUserContactDTO.setIsMail(webUserContact.getIsMail());
		}
		return new R<>(webUserContactDTO);

	}

	/**
	 * 保存主题
	 *
	 * @param userDto 用户的信息
	 * @param webusercontactVo 订阅的信息
	 * @return R
	 * @throws Exception
	 */
	@PostMapping("/insert")
	@ApiOperation(value = "保存用户订阅主题", notes = "保存用户订阅主题")
	@SysLog(value = "保存用户订阅主题",role = EnumRole.WEB_TYE)
	public R save(WebusercontactVo webusercontactVo,UserDTO userDto){
			Integer userId = SecurityUtils.getUser().getId();
			boolean isNotNull = checkObjAllFieldsIsNull(userDto);
			// 设置“不再提示”开关
			if (isNotNull) {
				remoteUserService.editInfo(userDto);
			}
			WebUserContact webUserContact = new WebUserContact();
			webUserContact.setWebUserId(userId);
			int count = webUserContactService.selectCount(new EntityWrapper<>(webUserContact, "id"));
			//  设置用户订阅的是标签还是字段
			webUserContactService.setUserContact(webusercontactVo, webUserContact,userId);
			if (count > 0) {
				boolean updateForSet = webUserContactService.update(webUserContact,
					new EntityWrapper<WebUserContact>().eq("web_users_id",
						webUserContact.getWebUserId()));
				return new R<>(updateForSet);
			}
			return new R<>(webUserContactService.insert(webUserContact));
	}

	private boolean checkObjAllFieldsIsNull(UserDTO userDto) {
		Integer isCheck = userDto.getIsCheck();
		String userDepartment = userDto.getUserDepartment();
		String userJob = userDto.getUserJob();
		String userLevel = userDto.getUserLevel();
		String username = userDto.getUsername();
		String userRegion = userDto.getUserRegion();
		String userScale = userDto.getUserScale();
		String userTarget = userDto.getUserTarget();
		if (null == isCheck && null == userDepartment && null == userJob && null == userLevel &&
				null == username && null == userRegion && null == userScale && null == userTarget) {
			return false;
		}
        return true;
    }

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public R delete(@PathVariable Integer id) {
		return new R<>(webUserContactService.deleteById(id));
	}

}
