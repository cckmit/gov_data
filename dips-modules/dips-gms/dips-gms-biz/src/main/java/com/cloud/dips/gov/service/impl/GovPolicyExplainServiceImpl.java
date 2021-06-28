package com.cloud.dips.gov.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.ExplainDTO;
import com.cloud.dips.gov.api.dto.GeneralDTO;
import com.cloud.dips.gov.api.entity.*;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.ExplainVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.mapper.GovPolicyDeclareMapper;
import com.cloud.dips.gov.mapper.GovPolicyExplainMapper;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;
import com.cloud.dips.gov.service.GovPolicyExplainService;
import com.cloud.dips.gov.service.GovRelationService;
import com.cloud.dips.gov.service.SortingDataService;
import com.cloud.dips.gov.utils.MapUtils;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author C.Z.H
 */
@Service("govPolicyExplainService")
@AllArgsConstructor
public class GovPolicyExplainServiceImpl extends ServiceImpl<GovPolicyExplainMapper, GovPolicyExplain>
        implements GovPolicyExplainService {

    private final GovPolicyExplainMapper mapper;
    private final GovRelationService govRelationService;
    private final RemoteTagRelationService remoteTagRelation;
    private final GovPolicyExamineRecordServiceImpl govPolicyExamineRecordServiceImpl;
    private final GovPolicyExamineCountService govPolicyExamineCountService;
    private final SortingDataService sortingDataService;
    @Autowired
  	private RemoteDictService remoteDictService;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<ExplainVO> selectPageByTagId(Query query) {
        Object tagId = query.getCondition().get("tagId");
        query.setRecords(mapper.selectExplainVoPageByTagId(query, tagId));
        return query;
    }

    /**
     * 回收站政策解读列表
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<ExplainVO> selectRecyclePage(Query query) {
        Object title = query.getCondition().get("title");
        Object username = query.getCondition().get("username");
        return query.setRecords(mapper.selectRecyclePage(query, title, username));
    }

    /**
     * 回收站查询单个
     */
    @Override
    public ExplainVO selectRecycleById(Integer id) {
        return mapper.selectAllVo(id);
    }

    /**
     * 回收站彻底删除
     */
    @Override
    public Boolean recycleDelete(Integer id) {

        Map<String, Object> explainTag = new HashMap<>(0);
        explainTag.put("relationId", id);
        explainTag.put("node", GovConstant.GOV_EXPLAIN_NODE);
        remoteTagRelation.deleteTagRelation(id, GovConstant.GOV_EXPLAIN_NODE);

        Map<String, Object> explainPolicy = new HashMap<>(0);
        explainPolicy.put("relationId", id);
        explainPolicy.put("node", GovConstant.GOV_EXPLAIN_NODE);
        govRelationService.deleteRelation(explainPolicy);

        mapper.deleteById(id);

        return Boolean.TRUE;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Page<ExplainVO> selectAllPage(Query query) {
        String title = null;
        Object main = query.getCondition().get("main");
        Object level = query.getCondition().get("level");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object priority = query.getCondition().get("priority");
        Object username = query.getCondition().get("username");
        Object theme = query.getCondition().get("theme");
        Object industry = query.getCondition().get("industry");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
        	String string = query.getCondition().get("title").toString().trim();
    		// 去除空格，并添加匹配符
    		 title = sortingDataService.replaceSpecialSign(string);
        }
            List<ExplainVO> explainPageVO = mapper.explainPageVO(query, title, main, priority, sort, startTime, endTime,
                    level, username, theme, industry, regionCode);
            setExplainText(explainPageVO,null,"web");
            query.setRecords(explainPageVO);
        return query;
    }


    /**
     * 小程序政策解读查询
     *
     * @param query
     * @return
     */
    @Override
    public Page<ExplainVO> selectWechatPage(Query query) {

        Object title = query.getCondition().get("title");
        Object main = query.getCondition().get("main");
        Object level = query.getCondition().get("level");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object priority = query.getCondition().get("priority");
        Object username = query.getCondition().get("username");
        Object theme = query.getCondition().get("theme");
        Object industry = query.getCondition().get("industry");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        List<ExplainVO> explainPageVO = mapper.selectWechatPage(query, title, main, priority,startTime, endTime, level, username, theme, industry, regionCode);
        setExplainText(explainPageVO,null,"web");
        query.setRecords(explainPageVO);
        return query;
    }

    /**
     * 政策解读新增
     */
    @Override
    public R insertExplain(ExplainDTO explainDTO) {
        GovPolicyExplain govPolicyExplain = new GovPolicyExplain();
        BeanUtils.copyProperties(explainDTO, govPolicyExplain);
		govPolicyExplain.setExamineStatus(0);
        Optional<Object> id = Optional.ofNullable(explainDTO.getId());
        if (id.isPresent()) {
            Map<String, Object> entity = MapUtils.beanToMap(govPolicyExplain);
            entity.remove("id");
            try {
                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                govPolicyExplain = new GovPolicyExplain();
                org.apache.commons.beanutils.BeanUtils.populate(govPolicyExplain, entity);
				govPolicyExplain.setExamineStatus(6);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        govPolicyExplain.setDelFlag(CommonConstant.STATUS_NORMAL);
        govPolicyExplain.setCreatorId(SecurityUtils.getUser().getId());
        govPolicyExplain.setCreateTime(new Date());
        govPolicyExplain.setProcessorId(SecurityUtils.getUser().getId());
        if(explainDTO.getRegionArr() != null && explainDTO.getRegionArr().size() > 0) {
        	String regionArrstring = explainDTO.getRegionArr().toString();
        	String regionArr = StringUtils.substringBetween(regionArrstring, "[", "]");
        	String replaceAll = regionArr.replaceAll(" ", "");
        	govPolicyExplain.setRegionArray(replaceAll);
        }
        govPolicyExplain = this.save(govPolicyExplain);

        // 标签添加(远程调用)
        Map<String, Object> explainTag = new HashMap<>(0);
        explainTag.put("relationId", govPolicyExplain.getId());
        explainTag.put("node", GovConstant.GOV_EXPLAIN_NODE);
        explainTag.put("tagKeyWords", getTagKeyWords(explainDTO.getTagList()));
        remoteTagRelation.saveTagRelation(explainTag);
        explainDTO.setId(govPolicyExplain.getId());

        // 关联政策原文
        if (explainDTO.getPolicyList() != null && explainDTO.getPolicyList().size() > 0) {
            Map<String, Object> explainPolicy = new HashMap<>(0);
            explainPolicy.put("relationId", govPolicyExplain.getId());
            explainPolicy.put("correlationIds", getStrParam(explainDTO.getPolicyList()));
            explainPolicy.put("node", GovConstant.GOV_EXPLAIN_NODE);
            explainPolicy.put("number", GovConstant.GOV_EXPLAIN_GENERAL);
            govRelationService.saveRelation(explainPolicy);
        }

        // 关联机构
        if (explainDTO.getOrganizationList() != null && explainDTO.getOrganizationList().size() > 0) {
            Map<String, Object> organization = new HashMap<>(0);
            organization.put("relationId", govPolicyExplain.getId());
            organization.put("correlationIds", getStrParam(explainDTO.getOrganizationList()));
            organization.put("node", GovConstant.GOV_EXPLAIN_NODE);
            organization.put("number", GovConstant.GOV_EXPLAIN_ORGANIZATION);
            govRelationService.saveRelation(organization);
        }
        return new R<>(Boolean.TRUE,govPolicyExplain.getId().toString());

    }

    /**
     * 政策解读新增并提交
     */
    @Override
    public Boolean insertExplainAndCommit(ExplainDTO explainDTO) {
        insertExplain(explainDTO);
        List<Integer> ids = new ArrayList<>();
        ids.add(explainDTO.getId());
        return commit(ids);
    }

    /**
     * 政策修改
     */
    @Override
    public Boolean updateExplain(ExplainDTO explainDTO) {
        GovPolicyExplain govPolicyExplain = new GovPolicyExplain();
        BeanUtils.copyProperties(explainDTO, govPolicyExplain);
        govPolicyExplain.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<String> regionArrstring = explainDTO.getRegionArr();
        if (null != regionArrstring && regionArrstring.size() > 0) {
            String regionArr = StringUtils.substringBetween(regionArrstring.toString(), "[", "]");
            String replaceAll = regionArr.replaceAll(" ", "");
            govPolicyExplain.setRegionArray(replaceAll);
        }
        mapper.updateById(govPolicyExplain);

        // 标签添加(远程调用)
        Map<String, Object> explainTag = new HashMap<>(0);
        explainTag.put("relationId", govPolicyExplain.getId());
        explainTag.put("node", GovConstant.GOV_EXPLAIN_NODE);
        explainTag.put("tagKeyWords", getTagKeyWords(explainDTO.getTagList()));
        remoteTagRelation.saveTagRelation(explainTag);
        sortingDataService.explainMethod(explainDTO, govPolicyExplain);
        return Boolean.TRUE;
    }

	

    /**
     * 政策修改并提交
     */
    @Override
    public Boolean updateExplainAndCommit(ExplainDTO explainDTO) {
        updateExplain(explainDTO);
        List<Integer> ids = new ArrayList<>();
        ids.add(explainDTO.getId());
        return commit(ids);
    }

    @Override
    public GovPolicyExplain save(GovPolicyExplain govPolicyExplain) {
        this.insert(govPolicyExplain);
        return govPolicyExplain;
    }

    @Override
    public GovPolicyExplain update(GovPolicyExplain govPolicyExplain) {
        this.updateById(govPolicyExplain);
        return govPolicyExplain;
    }

    @Override
    public Boolean repeat(String title) {
        List<GovPolicyExplain> list = mapper.repeat(title);
        if (list != null && list.size() > 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    // 将前台获取的接口参数标签集合转换成字符串
    private String getTagKeyWords(List<String> tags) {
        String tagKeyWords = "";
        if (tags != null) {
            Set<String> set = new HashSet<String>(tags);
            String[] godtagNames = set.toArray(new String[0]);
            for (int i = 0; i < godtagNames.length; i++) {
                if (i != godtagNames.length - 1) {
                    tagKeyWords = tagKeyWords + godtagNames[i] + ",";
                } else {
                    tagKeyWords = tagKeyWords + godtagNames[i];
                }
            }
        }
        return tagKeyWords;
    }

    // 将前台获取的接口参数用户ID集合转换成字符串
    private String getStrParam(List<Integer> ids) {
        String params = "";
        if (ids != null) {
            Set<Integer> set = new HashSet<Integer>(ids);
            Integer[] godtagNames = set.toArray(new Integer[0]);
            for (int i = 0; i < godtagNames.length; i++) {
                if (i != godtagNames.length - 1) {
                    params = params + godtagNames[i] + ",";
                } else {
                    params = params + godtagNames[i];
                }
            }
        }
        return params;
    }

    @Override
    public Boolean deleteExplain(Integer[] explainIds) {
        for (Integer id : explainIds) {
            mapper.updateDelFlag(id);
        }
        return true;
    }

    @Override
    public ExplainVO selectAllVo(Integer id) {
        ExplainVO explainVO = mapper.selectAllVo(id);
        String text = mapper.selectExplainText(explainVO.getId());
        explainVO.setText(text);
		setExplainValue(explainVO);
		List<ExplainVO> allForConsole = new ArrayList<ExplainVO>();
		setExplainText(allForConsole,explainVO,"web");
        return explainVO;
    }
    
    @Override
    public ExplainVO selectAllVoForConsole(Integer id) {
    	ExplainVO explainVO = mapper.selectAllVoForConsole(id);
    	String text = mapper.selectExplainText(explainVO.getId());
        explainVO.setText(text);
		setExplainValue(explainVO);
		List<ExplainVO> allForConsole = new ArrayList<ExplainVO>();
		setExplainText(allForConsole,explainVO,"web");
        return explainVO;
    }

    // 浏览次数加1
    @Override
    public void updateViews(Integer id) {
        mapper.updateViews(id);
    }

    @Override
    public Page<ExplainVO> selectSelfPage(Query query) {
        Object title = null;
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
        	if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
    			String string = query.getCondition().get("title").toString().trim();
        		try {
    				title = URLDecoder.decode(string, "UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
        }
        Object processorId = SecurityUtils.getUser().getId();
        Object examineStatus = query.getCondition().get("examineStatus");
        Object main = query.getCondition().get("main");
        Object level = query.getCondition().get("level");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object priority = query.getCondition().get("priority");
        Object username = query.getCondition().get("username");
        Object theme = query.getCondition().get("theme");
        Object industry = query.getCondition().get("industry");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        query.setRecords(mapper.selectSelfPage(query, title, processorId, examineStatus, main, priority, null, sort, startTime, endTime,
                level, username, theme, industry, regionCode, prop, order));
        return query;
    }

    @Override
    public Page<GeneralVO> selectExaminePage(Query query) {
        Object title = null;
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
        	String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        Object processorName = query.getCondition().get("processorName");
        Object examineStatus = query.getCondition().get("examineStatus");
        Object main = query.getCondition().get("main");
        Object level = query.getCondition().get("level");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object priority = query.getCondition().get("priority");
        Object username = query.getCondition().get("username");
        Object theme = query.getCondition().get("theme");
        Object industry = query.getCondition().get("industry");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        query.setRecords(mapper.selectExaminePage(query, title, processorName, examineStatus, main, priority, null, sort, startTime, endTime,
                level, username, theme, industry, regionCode, prop, order));
        return query;
    }

    @Override
    public Boolean commit(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyExplain explain = this.selectById(id);
            if (null == explain.getTitle() || null == explain.getPublishTime()
                    || null == explain.getSource() || null == explain.getUrl()
                    || null == explain.getLevel() || null == explain.getMain()
                    || null == explain.getTheme() || null == explain.getIndustry()
                    || null == explain.getRegion() || null == explain.getSummary()
                    || null == explain.getText()) {
                return Boolean.FALSE;
            }
            if (StringUtils.isEmpty(explain.getTitle()) || StringUtils.isEmpty(explain.getPublishTime().toString())
                    || StringUtils.isEmpty(explain.getSource()) || StringUtils.isEmpty(explain.getUrl())
                    || StringUtils.isEmpty(explain.getLevel()) || StringUtils.isEmpty(explain.getMain())
                    || StringUtils.isEmpty(explain.getTheme()) || StringUtils.isEmpty(explain.getIndustry())
                    || StringUtils.isEmpty(explain.getRegion()) || StringUtils.isEmpty(explain.getSummary())
                    || StringUtils.isEmpty(explain.getText())) {
                return Boolean.FALSE;
            }
            if(mapper.checkTag(explain.getId()) <= 0) {
            	return Boolean.FALSE;
            }
            if (0 == explain.getExamineStatus() || 4 == explain.getExamineStatus() || 6 == explain.getExamineStatus()) {
                explain.setExamineStatus(1);
                explain.setModifiedTime(new Date());
                explain.setCommitTime(new Date());
                markRecord(1, explain.getId(), explain.getProcessorId(), "提交", explain.getTitle());
                this.updateById(explain);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean reCommit(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyExplain explain = this.selectById(id);
            if (1 == explain.getExamineStatus()) {
                explain.setExamineStatus(0);
                this.updateById(explain);
                markRecord(1, explain.getId(), explain.getProcessorId(), "重新提交", explain.getTitle());
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean giveUpProcess(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyExplain explain = this.selectById(id);
            if (0 == explain.getExamineStatus() || -1 == explain.getExamineUser()) {
                if (null != explain.getProcessorId()) {
                    String scrapyId = "";
                    if (null != explain.getScrapyId()) {
                        scrapyId = Integer.toString(explain.getScrapyId());
                    }
                    String processorId = explain.getProcessorId().toString();
                    if (StringUtils.isNotEmpty(scrapyId) && StringUtils.isNotEmpty(processorId)) {
                        mapper.updateForGiveUpProcess(scrapyId, processorId);
                        mapper.updateForGiveUpProcessToExplain(scrapyId, processorId);
                    }
                }
            } else {
                return Boolean.FALSE;
            }
            markRecord(5, explain.getId(), explain.getProcessorId(), "自放弃加工", explain.getTitle());
            this.deleteById(explain.getId());
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean accept(Integer id) {
        GovPolicyExplain explain = this.selectById(id);
        if (1 == explain.getExamineStatus()) {
            explain.setExamineStatus(2);
            this.updateById(explain);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean doExamine(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyExplain explain = this.selectById(id);
            if (1 == explain.getExamineStatus() || 2 == explain.getExamineStatus()) {
                explain.setExamineStatus(3);
                explain.setExamineDate(new Date());
                explain.setExamineUser(SecurityUtils.getUser().getId());
                this.updateById(explain);
                markRecord(2, explain.getId(), explain.getProcessorId(), "审核通过", explain.getTitle());
                increaseCount(explain.getProcessorId());
            }else {
				 return Boolean.FALSE;
			}
           
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean disExamine(Query query) {
        int id = Integer.parseInt(query.getCondition().get("id").toString());
        Object retreatContent = query.getCondition().get("retreatContent");
        GovPolicyExplain explain = this.selectById(id);
        int isCommit = 1;
        int isAccept = 2;
        if (isCommit == explain.getExamineStatus() || isAccept == explain.getExamineStatus()) {
            if (null == explain.getRetreatCount() || explain.getRetreatCount() == 0) {
                explain.setRetreatCount(1);
            } else {
                explain.setRetreatCount(explain.getRetreatCount() + 1);
            }
            explain.setRetreatContent((String) retreatContent);
            explain.setRetreatUser(SecurityUtils.getUser().getId());
            explain.setExamineStatus(4);
            this.updateById(explain);
            markRecord(3, explain.getId(), explain.getProcessorId(), (String) retreatContent, explain.getTitle());
            reduceCount(explain.getProcessorId());
            return Boolean.TRUE;
		}else {
			return Boolean.FALSE;
		}
    }

    @Override
    public Boolean retreatPolicy(Query query) {
        int id = Integer.parseInt(query.getCondition().get("id").toString());
        Object retreatContent = query.getCondition().get("retreatContent");
        GovPolicyExplain explain = this.selectById(id);
        int isExamined = 3;
        if (isExamined == explain.getExamineStatus()) {
            if (null == explain.getRetreatCount() || explain.getRetreatCount() == 0) {
                explain.setRetreatCount(1);
            } else {
                explain.setRetreatCount(explain.getRetreatCount() + 1);
            }
            explain.setRetreatContent((String) retreatContent);
            explain.setRetreatUser(SecurityUtils.getUser().getId());
            explain.setExamineStatus(5);
            this.updateById(explain);
            markRecord(4, explain.getId(), explain.getProcessorId(), (String) retreatContent, explain.getTitle());
            reduceCount(explain.getProcessorId());
        }
        return Boolean.TRUE;
    }


    private void increaseCount(Integer processorId) {
        GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
        if (null == govPolicyExamineCount) {
            govPolicyExamineCount = new GovPolicyExamineCount();
            govPolicyExamineCount.setMark(0.5);
            //初始化
            govPolicyExamineCount.setCreateTime(new Date());
            govPolicyExamineCount.setProcessorId(processorId);
            govPolicyExamineCount.setDelFlag("0");
            govPolicyExamineCount.setAgreeCount(1);
        } else {
            govPolicyExamineCount.setMark(govPolicyExamineCount.getMark() + 0.5);
            govPolicyExamineCount.setAgreeCount(govPolicyExamineCount.getAgreeCount() + 1);
        }
        govPolicyExamineCount.setModifiedTime(new Date());
        govPolicyExamineCountService.insertOrUpdate(govPolicyExamineCount);
    }

    private void reduceCount(Integer processorId) {
        GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
        if (null == govPolicyExamineCount) {
            govPolicyExamineCount = new GovPolicyExamineCount();
            //初始化
            govPolicyExamineCount.setCreateTime(new Date());
            govPolicyExamineCount.setProcessorId(processorId);
            govPolicyExamineCount.setDelFlag("0");
            govPolicyExamineCount.setDisagreeCount(1);
        } else {
            govPolicyExamineCount.setDisagreeCount(govPolicyExamineCount.getDisagreeCount() + 1);
        }
        govPolicyExamineCount.setModifiedTime(new Date());
        govPolicyExamineCountService.insertOrUpdate(govPolicyExamineCount);
    }

    private void markRecord(Integer behavior, Integer policyId, Integer processorId, String content, String title) {
        GovPolicyExamineRecord govPolicyExamineRecord = new GovPolicyExamineRecord();
        govPolicyExamineRecord.setBehavior(behavior);
        govPolicyExamineRecord.setPolicyId(policyId);
        govPolicyExamineRecord.setCreate_user(SecurityUtils.getUser().getId());
        govPolicyExamineRecord.setCreateTime(new Date());
        govPolicyExamineRecord.setModifiedTime(new Date());
        govPolicyExamineRecord.setDelFlag("0");
        govPolicyExamineRecord.setProcessorId(processorId);
        govPolicyExamineRecord.setContent(content);
        govPolicyExamineRecord.setTitle(title);
        govPolicyExamineRecord.setPolicyType(2);
        govPolicyExamineRecordServiceImpl.insert(govPolicyExamineRecord);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Page<ExplainVO> selectConsolePage(Query query) {
        Object title = query.getCondition().get("title");
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
        Object main = query.getCondition().get("main");
        Object level = query.getCondition().get("level");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object priority = query.getCondition().get("priority");
        Object username = query.getCondition().get("username");
        Object theme = query.getCondition().get("theme");
        Object industry = query.getCondition().get("industry");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        if (GovConstant.PUSH_TIME.equals(sort)) {
            List<ExplainVO> explainPageVO = mapper.explainPageVOForConsole(query, title, main, priority, null, sort, startTime, endTime,
                    level, username, theme, industry, regionCode, prop, order);
            query.setRecords(explainPageVO);
            return query;

        }
        if (GovConstant.CLICK_VIEWS.equals(sort)) {
            List<ExplainVO> explainPageVO = mapper.explainPageVOForConsole(query, title, main, priority, sort, null, startTime, endTime,
                    level, username, theme, industry, regionCode, prop, order);
            query.setRecords(explainPageVO);
            return query;
        }
        List<ExplainVO> explainPageVO = mapper.explainPageVOForConsole(query, title, main, priority, null, null, startTime, endTime,
                level, username, theme, industry, regionCode, prop, order);
        query.setRecords(explainPageVO);
        return query;
    }

    /**
     * 查询相关解读    通用政策
     *
     * @param id
     * @return
     */
    @Override
    public List<ExplainVO> selectGenerList(Integer id) {
        return mapper.selectgenerlist(id);
    }


    @Override
    public List<ExplainVO> findPolicyText(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = StringUtils.split(ids, ",");
            List<Integer> integers = Lists.newArrayList();
            for (int i = 0; i < split.length; i++) {
                integers.add(Integer.valueOf(split[i]));
            }
            return mapper.findPolicyText(integers);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<ExplainVO> findInterPreTaboo(String ids, Integer id) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = StringUtils.split(ids, ",");
            List<Integer> integers = Lists.newArrayList();
            for (int i = 0; i < split.length; i++) {
                integers.add(Integer.valueOf(split[i]));
            }
            return mapper.findInterPreTaboo(integers, id);
        }
        return Lists.newArrayList();
    }
    
    /**
   	 * 重构页面逻辑
   	 * @param allForConsole
   	 * @param mark
   	 */
   	private void setExplainText(List<ExplainVO> explainVOList,ExplainVO explain,String mark) {
   		if (null != mark && explainVOList.size() > 0) {
   		List<Integer> explainIds = new ArrayList<Integer>();
   		for (ExplainVO explainId : explainVOList) {
   			explainIds.add(explainId.getId());
   		}
   		List<CommonVO> selectGeneralTag = mapper.selectNewExplainTag(explainIds);
   		List<CommonVO> selectGeneralDispatch = mapper.selectNewExplainDispatch(explainIds);
   		for (ExplainVO explainVO : explainVOList) {
   			for (CommonVO commonVO : selectGeneralTag) {
   				if (explainVO.getId().equals(commonVO.getRelationId())) {
   					commonVO.setCommonId(commonVO.getTagId());
   					commonVO.setCommonName(commonVO.getTagName());
   					explainVO.getTagList().add(commonVO);
   				}
   			}
   			for (CommonVO commonVO : selectGeneralDispatch) {
   				if (explainVO.getId().equals(commonVO.getDispatchId())) {
   					commonVO.setCommonId(commonVO.getDispatchId());
   					commonVO.setCommonName(commonVO.getDispatchName());
   					explainVO.getDispatchList().add(commonVO);
   				}
   			}

   		 }
   		}
   		if (null != mark && null != explain) {
   			List<Integer> generalIds = new ArrayList<Integer>();
   			generalIds.add(explain.getId());
   			List<CommonVO> selectGeneralTag = mapper.selectNewExplainTag(generalIds);
   			List<CommonVO> selectGeneralDispatch = mapper.selectNewExplainDispatch(generalIds);
   			for (CommonVO commonVO : selectGeneralTag) {
   				if (explain.getId().equals(commonVO.getRelationId())) {
   					commonVO.setCommonId(commonVO.getTagId());
   					commonVO.setCommonName(commonVO.getTagName());
   					explain.getTagList().add(commonVO);
   				}
   			}
   			for (CommonVO commonVO : selectGeneralDispatch) {
   				if (explain.getId().equals(commonVO.getDispatchId())) {
   					commonVO.setCommonId(commonVO.getDispatchId());
   					commonVO.setCommonName(commonVO.getDispatchName());
   					explain.getDispatchList().add(commonVO);
   				}
   			}
   		}
   		
   	}
   	/**
   	 * 重构单个政策逻辑代码，增加查询速度
   	 * @param generalVO
   	 */
   	private void setExplainValue(ExplainVO explainVO) {
   		if (StringUtils.isNotBlank(explainVO.getRegionArrString())) {
   			String[] split = explainVO.getRegionArrString().split(",");
   			explainVO.setRegionArr(Arrays.asList(split));
   		}
   		String[] numberList = new String[] {"POLICY_THEME","POLICY_INDUSTRY","POLICY_LEVEL","POLICY_MAIN"};
           Map<String, List<DictValueVO>> dictMap = remoteDictService.getDictMap(numberList);
           if (StringUtils.isNotBlank(explainVO.getTheme())) {
        	   String[] themeSplit = explainVO.getTheme().split(",");
        	   List<DictValueVO> themeList = dictMap.get("POLICY_THEME");
        	   for (int i = 0; i < themeSplit.length; i++) {
        		   for (DictValueVO dictValueVO : themeList) {
        			   if (dictValueVO.getKey().equals(themeSplit[i])) {
        				   explainVO.getThemeList().add(dictValueVO.getValue());
        			   }
        		   }
        		   
        	   }
           }
           if (StringUtils.isNotBlank(explainVO.getIndustry())) {
        	   String[] industrySplit = explainVO.getIndustry().split(",");
        	   List<DictValueVO> industryList = dictMap.get("POLICY_INDUSTRY");
        	   for (int i = 0; i < industrySplit.length; i++) {
        		   for (DictValueVO dictValueVO : industryList) {
        			   if (dictValueVO.getKey().equals(industrySplit[i])) {
        				   explainVO.getIndustryList().add(dictValueVO.getValue());
        			   }
        		   }
        		   
        	   }
        	   
           }
   				String level = explainVO.getLevel();
   				if (null != level) {
   					
   					List<DictValueVO> levelList = dictMap.get("POLICY_LEVEL");
   					for (DictValueVO dictValueVO : levelList) {
   						if (dictValueVO.getKey().equals(level)) {
   							explainVO.setLevelName(dictValueVO.getValue());
   						}
   					}
   				}
   				String main = explainVO.getMain();
   				if (null != main) {
   					
   					List<DictValueVO> mainList = dictMap.get("POLICY_MAIN");
   					for (DictValueVO dictValueVO : mainList) {
   						if (dictValueVO.getKey().equals(level)) {
   							explainVO.setMainName(dictValueVO.getValue());
   						}
   					}
   				}
   				
   	}
    
    
}
