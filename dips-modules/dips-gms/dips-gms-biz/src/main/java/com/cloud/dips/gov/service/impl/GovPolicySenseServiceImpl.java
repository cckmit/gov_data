package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.dto.SenseDTO;
import com.cloud.dips.gov.api.entity.GovPolicyExamineRecord;
import com.cloud.dips.gov.api.entity.GovPolicySense;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicyGeneral;
import com.cloud.dips.gov.api.entity.ScrapyGovPolicySense;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.api.vo.SenseVO;
import com.cloud.dips.gov.mapper.GovPolicySenseMapper;
import com.cloud.dips.gov.service.GovPolicySenseService;
import com.cloud.dips.gov.service.ScrapyGovPolicyGeneralService;
import com.cloud.dips.gov.service.ScrapyGovPolicySenseService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 政策常识模型实现类
 *
 * @author Z.Y.S
 * @date 2018-09-11 10:14:26
 */
@Service("govPolicySenseService")
@AllArgsConstructor
public class GovPolicySenseServiceImpl extends ServiceImpl<GovPolicySenseMapper, GovPolicySense> implements GovPolicySenseService {

	private final GovPolicySenseMapper govPolicySenseMapper;
	private final RemoteTagRelationService remoteTagRelationService;
	private final RemoteTagRelationService remoteTagService;

	/**
	 * 逻辑删除，只修改状态值
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Boolean deletePolicySense(Integer id) {
		GovPolicySense govPolicySense = govPolicySenseMapper.selectById(id);
		govPolicySense.setDelFlag(CommonConstant.STATUS_DEL);
		govPolicySenseMapper.updateById(govPolicySense);

		return Boolean.TRUE;
	}

	/**
	 * 根据ID批量删除
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public Boolean deletePolicySenseAll(Integer[] ids) {
		for (Integer id : ids) {
			deletePolicySense(id);
		}
		return Boolean.TRUE;
	}

	/**
	 * 添加政策常识
	 */
	@Override
	public Boolean insertPolicySense(SenseDTO senseDTO) {
		GovPolicySense govPolicySense = new GovPolicySense();
		BeanUtils.copyProperties(senseDTO, govPolicySense);
		govPolicySense.setCreatorId(SecurityUtils.getUser().getId());
		govPolicySense.setDelFlag(CommonConstant.STATUS_NORMAL);
		govPolicySense.setCreateTime(new Date());
		govPolicySense.setProcessorId(SecurityUtils.getUser().getId());
		govPolicySense.setExamineStatus(0);
		this.insert(govPolicySense);

		//政策常识标签添加（跨服务调用）
		if (senseDTO.getTagList() != null) {
			Map<String, Object> params = new HashMap<>(0);
			params.put("relationId", govPolicySense.getId());
			params.put("node", "gov_policy_sense");
			params.put("tagKeyWords", getTagKeyWords(senseDTO.getTagList()));
			remoteTagService.saveTagRelation(params);
		}
		return Boolean.TRUE;

	}

	/**
	 * 修改政策常识
	 */
	@Override
	public Boolean updatePolicySenseById(SenseDTO senseDTO) {
		GovPolicySense govPolicySense = new GovPolicySense();
		BeanUtils.copyProperties(senseDTO, govPolicySense);
		govPolicySense.setDelFlag(CommonConstant.STATUS_NORMAL);
		this.updateById(govPolicySense);
		//修改关联标签
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", govPolicySense.getId());
		params.put("node", "gov_policy_sense");
		params.put("tagKeyWords", getTagKeyWords(senseDTO.getTagList()));
		remoteTagService.saveTagRelation(params);
		return Boolean.TRUE;
	}

	/**
	 * 查看政策常识
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<SenseVO> selectAllPolicySense(Query query) {
		Object title = null;
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Object username = query.getCondition().get("username");
		Object prop = query.getCondition().get("prop");
		Object order = query.getCondition().get("order");
		query.setRecords(govPolicySenseMapper.selectPolicySenseList(query, title, username,prop,order));
		return query;
	}

	/**
	 * 回收站政策常识列表
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<SenseVO> selectRecyclePage(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(govPolicySenseMapper.selectRecyclePage(query, title, username));
		return query;
	}

	@Override
	public Boolean recycleDelete(Integer id) {
		Map<String, Object> params = new HashMap<>(0);
		params.put("relationId", id);
		params.put("node", "gov_policy_sense");
		remoteTagRelationService.deleteTagRelation(id,"gov_policy_sense");
		govPolicySenseMapper.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean repeat(String title) {
		List<GovPolicySense> list = govPolicySenseMapper.repeat(title);
		if (list != null && list.size() > 0) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	//将前台获取的接口参数标签集合转换成字符串
	private String getTagKeyWords(List<String> tags) {
		StringBuilder tagKeyWords = new StringBuilder();
		if (tags != null) {
			Set<String> set = new HashSet<>(tags);
			String[] names = set.toArray(new String[0]);
			for (int i = 0; i < names.length; i++) {
				if (i != names.length - 1) {
					tagKeyWords.append(names[i]).append(",");
				} else {
					tagKeyWords.append(names[i]);
				}
			}
		}
		return tagKeyWords.toString();
	}

	@Override
	public Boolean retreatPolicy(Query query) {
		int id = (int) query.getCondition().get("id");
		String retreatContent = (String) query.getCondition().get("retreatContent");
		GovPolicySense govPolicySense = new GovPolicySense();
		govPolicySense=govPolicySenseMapper.selectById(id);
		govPolicySense.setDelFlag("1");
		govPolicySense.setModifiedTime(new Date());
		this.updateById(govPolicySense);
		if(StringUtils.isNotEmpty(govPolicySense.getScrapyId().toString())) {
			
		}
		return Boolean.TRUE;
	}

	@Override
	public Page<SenseVO> selectSelfPage(Query query) {
		Object title = query.getCondition().get("title");
		Object processorId = SecurityUtils.getUser().getId();
		Object examineStatus = query.getCondition().get("examineStatus");
		query.setRecords(govPolicySenseMapper.selectSelfPage(query, title, processorId, examineStatus));
		return query;
	}
	
	@Override
	public Page<GeneralVO> selectExaminePage(Query query) {
		Object title = query.getCondition().get("title");
		Object processorName = query.getCondition().get("processorName");
		Object examineStatus = query.getCondition().get("examineStatus");
		query.setRecords(govPolicySenseMapper.selectExaminePage(query, title, processorName, examineStatus));
		return query;
	}

	@Override
	public Boolean commit(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicySense govPolicySense = this.selectById(id);
			if(0 == govPolicySense.getExamineStatus() || 4 == govPolicySense.getExamineStatus()) {
				govPolicySense.setExamineStatus(1);
				this.updateById(govPolicySense);
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean reCommit(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicySense govPolicySense = this.selectById(id);
			if(1 == govPolicySense.getExamineStatus()) {
				govPolicySense.setExamineStatus(0);
				this.updateById(govPolicySense);
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean giveUpProcess(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicySense govPolicySense = this.selectById(id);
			if(0 == govPolicySense.getExamineStatus() && -1 == govPolicySense.getExamineUser()) {
				if(0 == govPolicySense.getExamineStatus() && -1 == govPolicySense.getExamineUser()) {
					if(null != govPolicySense.getProcessorId()) {
						String scrapyId="";
						if(null != govPolicySense.getScrapyId()) {
							scrapyId=Integer.toString(govPolicySense.getScrapyId());
						}
						String processorId=govPolicySense.getProcessorId().toString();
						if(StringUtils.isNotEmpty(scrapyId) && StringUtils.isNotEmpty(processorId)) {
							govPolicySenseMapper.updateForGiveUpProcess(scrapyId,processorId);
							govPolicySenseMapper.updateForGiveUpProcessToSense(scrapyId,processorId);
						}
					}
				}
				markRecord(5,govPolicySense.getId(),govPolicySense.getProcessorId(),"自放弃加工");
				this.deleteById(govPolicySense.getId());
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean accept(Integer id) {
		GovPolicySense govPolicySense = this.selectById(id);
		if(1 == govPolicySense.getExamineStatus()) {
			govPolicySense.setExamineStatus(2);
			this.updateById(govPolicySense);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean doExamine(List<Integer> ids) {
		for (Integer id : ids) {
			GovPolicySense govPolicySense = this.selectById(id);
			if(1 == govPolicySense.getExamineStatus() || 2 == govPolicySense.getExamineStatus()) {
				govPolicySense.setExamineStatus(3);
				govPolicySense.setExamineDate(new Date());
				govPolicySense.setExamineUser(SecurityUtils.getUser().getId());
				this.updateById(govPolicySense);
			}
			markRecord(2,govPolicySense.getId(),govPolicySense.getProcessorId(),"审核通过");
		}
	    return Boolean.TRUE;
	}

	@Override
	public Boolean disExamine(Query query) {
		int id = Integer.parseInt(query.getCondition().get("id").toString());
		Object retreatContent = query.getCondition().get("retreatContent");
		GovPolicySense govPolicySense = this.selectById(id);
		int isCommit=1;
		int isAccept=2;
		if(isCommit == govPolicySense.getExamineStatus() || isAccept == govPolicySense.getExamineStatus()) {
			if(null == govPolicySense.getRetreatCount() || govPolicySense.getRetreatCount() == 0) {
				govPolicySense.setRetreatCount(1);
			}else {
				govPolicySense.setRetreatCount(govPolicySense.getRetreatCount()+1);
			}
			govPolicySense.setRetreatContent((String) retreatContent);
			govPolicySense.setRetreatUser(SecurityUtils.getUser().getId());
			govPolicySense.setExamineStatus(4);
			this.updateById(govPolicySense);
			
			markRecord(3,govPolicySense.getId(),govPolicySense.getProcessorId(),(String) retreatContent);
		}
		return Boolean.TRUE;
	}
	
	private void markRecord(Integer behavior,Integer policyId,Integer processorId,String content) {
		GovPolicyExamineRecord govPolicyExamineRecord=new GovPolicyExamineRecord(); 
		govPolicyExamineRecord.setBehavior(behavior);
		govPolicyExamineRecord.setPolicyId(policyId);
		govPolicyExamineRecord.setCreate_user(SecurityUtils.getUser().getId());
		govPolicyExamineRecord.setCreateTime(new Date());
		govPolicyExamineRecord.setModifiedTime(new Date());
		govPolicyExamineRecord.setDelFlag("0");
		govPolicyExamineRecord.setProcessorId(processorId);
		govPolicyExamineRecord.setContent(content);
		govPolicyExamineRecord.insert();
	}
	
	/**
	 * 查看政策常识
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<SenseVO> selectConsolePolicySense(Query query) {
		Object title = query.getCondition().get("title");
		Object username = query.getCondition().get("username");
		query.setRecords(govPolicySenseMapper.selectPolicySenseListForConsole(query, title, username));
		return query;
	}
	
}
