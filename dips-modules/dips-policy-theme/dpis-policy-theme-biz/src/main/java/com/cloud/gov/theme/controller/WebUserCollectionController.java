package com.cloud.gov.theme.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.log.enmu.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.feign.RemoteGovInformationService;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyDeclareService;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyExplainService;
import com.cloud.dips.gov.api.feign.RemoteGovPolicyGeneralService;
import com.cloud.dips.gov.api.feign.RemoteGovPolicySenseService;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.theme.api.entity.WebUserCollection;
import com.cloud.dips.theme.api.entity.WebUserContact;
import com.cloud.dips.theme.api.entity.WebUserFootprint;
import com.cloud.gov.theme.service.WebUserCollectionService;
import com.cloud.gov.theme.service.WebUserFootprintService;

import io.swagger.annotations.ApiOperation;


	/**
 	* 
 	* @author johan
	* @Date 2018年12月10日
	* @Company 佛山司马钱技术有限公司
	* @description 用户收藏表
 	*/
	@RestController
	@RequestMapping("/webusercollection")
	public class WebUserCollectionController {
    @Autowired
    private WebUserCollectionService webUserCollectionService;
    @Autowired
    private WebUserFootprintService webUserFootprintService;

    @Autowired
    private RemoteGovPolicyDeclareService remoteGovPolicyDeclareService;
    @Autowired
    private RemoteGovInformationService remoteGovInformationService;
    @Autowired
    private RemoteGovPolicyExplainService remoteGovPolicyExplainService;
    @Autowired
    private RemoteGovPolicyGeneralService remoteGovPolicyGeneralService;
    @Autowired
    private RemoteGovPolicySenseService remoteGovPolicySenseService;
    
    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/queryCollectionList")
    public Page page(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
    	WebUserCollection webUserCollection = new WebUserCollection();
		Integer userId = SecurityUtils.getUser().getId();
		webUserCollection.setWebUserId(userId);
		EntityWrapper<WebUserCollection> entityWrapper = new EntityWrapper<>(webUserCollection, "id,policy_id,policy_type,title");
		return  webUserCollectionService.selectPage(new Page(page,limit,"create_time",false), entityWrapper);
    }
	

    /**
     * 查找用户是否收藏
     */
    @GetMapping("/queryIsCollection/{collectionId}/{mark}")
    @ApiOperation(value = "查找用户是否收藏", notes = "查找用户是否收藏")
    public R queryIsCollection(@PathVariable String collectionId, @PathVariable String mark) {
    	int parseInt = Integer.parseInt(collectionId);
    	String collectionTitle = webUserCollectionService.queryCollectionTitle(parseInt,mark);
    	// 每次点开政策页面就保存足迹
    	webUserFootprintService.saveFootprint(collectionId, mark, collectionTitle);
		WebUserCollection webUserCollection = webUserCollectionService.setCollectionValue(collectionId, mark,null);
    	EntityWrapper<WebUserCollection> entityWrapper = new EntityWrapper<>(webUserCollection);
    	int count = webUserCollectionService.selectCount(entityWrapper);
    	if (count > 0) {
    		return new R<>("该政策已经收藏了");
    	}
      return  new R<>(count, "true");
    }

    /**
     * 保存用户收藏
     * @param webUserCollection
     * @return R
     * @throws UnsupportedEncodingException 
     */
    @PostMapping("/insertCollection")
    @ApiOperation(value = "保存用户收藏", notes = "保存用户收藏")
	@SysLog(value = "保存用户收藏",role = EnumRole.WEB_TYE)
    public R saveCollection(@RequestParam("collectionId") String collectionId, 
    		@RequestParam("mark") String mark,@RequestParam("title") String title) throws UnsupportedEncodingException{
    	String decodeTitle = URLDecoder.decode(title,"UTF-8");
    	WebUserCollection webUserCollection = webUserCollectionService.setCollectionValue(collectionId, mark,decodeTitle);
    	boolean success = webUserCollectionService.insert(webUserCollection);
    	if (!success) {
    		return new R<>("收藏失败");
    	}
		return new R<>(success,"success");
    }



    /**
     * 修改
     * @param webUserCollection
     * @return R
     */
    @PutMapping
    public R update(@RequestBody WebUserCollection webUserCollection){
			webUserCollectionService.updateById(webUserCollection);
      return new R<>(Boolean.TRUE);
    }

    /**
     * 删除用户收藏
     * @param id
     * @return R
     */
    @DeleteMapping("/deleteCollection/{collectionId}/{mark}")
    @ApiOperation(value = "删除用户收藏", notes = "删除用户收藏")
    public R delete(@PathVariable Integer collectionId,@PathVariable String mark){
    	Map<String, Object> columnMap = new HashMap<String, Object>();
    	Integer userId = SecurityUtils.getUser().getId();
    	columnMap.put("web_user_id", userId);
    	columnMap.put("policy_id", collectionId);
    	columnMap.put("policy_type", mark);
    	return new R<>(webUserCollectionService.deleteByMap(columnMap));
    }
    
   

}
