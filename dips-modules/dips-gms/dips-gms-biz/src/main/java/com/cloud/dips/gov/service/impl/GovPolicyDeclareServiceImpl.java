package com.cloud.dips.gov.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.gov.api.entity.*;
import com.cloud.dips.gov.api.vo.CommonVO;
import com.cloud.dips.gov.utils.MapUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.StringMatching;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.DeclareDTO;
import com.cloud.dips.gov.api.vo.ComparisonVO;
import com.cloud.dips.gov.api.vo.DeclareVO;
import com.cloud.dips.gov.api.vo.GeneralVO;
import com.cloud.dips.gov.mapper.GovPolicyDeclareMapper;
import com.cloud.dips.gov.mapper.GovRelationMapper;
import com.cloud.dips.gov.mapper.GovRelationTypeMapper;
import com.cloud.dips.gov.service.GovPolicyDeclareService;
import com.cloud.dips.gov.service.GovPolicyExamineCountService;
import com.cloud.dips.gov.service.GovPolicyExamineRecordService;
import com.cloud.dips.gov.service.SortingDataService;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;


/**
 * @author C.Z.H
 */
@Service("govPolicyDeclareService")
@AllArgsConstructor
public class GovPolicyDeclareServiceImpl extends ServiceImpl<GovPolicyDeclareMapper, GovPolicyDeclare> implements GovPolicyDeclareService {

    private final GovPolicyDeclareMapper mapper;
    private final RemoteTagRelationService remoteTagService;
    private final GovRelationMapper govRelationMapper;
    private final GovRelationTypeMapper govRelationTypeMapper;
    private final GovPolicyExamineRecordService govPolicyExamineRecordService;
    private final GovPolicyExamineCountService govPolicyExamineCountService;
    private final SortingDataService sortingDataService;
    @Autowired
	private RemoteDictService remoteDictService;

    @Override
    public DeclareVO selectDeclareVOById(Integer id) {
//        mapper.viewUp(id);
        DeclareVO declareVO = mapper.selectDeclareVOById(id);
        String text = mapper.selectDeclareText(declareVO.getId());
        declareVO.setText(text);
		setDeclareValue(declareVO);
		List<DeclareVO> allForConsole = new ArrayList<DeclareVO>();
		setDeclareText(allForConsole,declareVO,"web");
        return declareVO;
    }
    

    public DeclareVO selectDeclareVOByIdForConsole(Integer id) {
    	DeclareVO declareVO = mapper.selectDeclareVOByIdForConsole(id);
    	String text = mapper.selectDeclareText(declareVO.getId());
        declareVO.setText(text);
 		setDeclareValue(declareVO);
 		List<DeclareVO> allForConsole = new ArrayList<DeclareVO>();
 		setDeclareText(allForConsole,declareVO,"web");
        return declareVO;
    }
    /**
     * 分页查询信息
     *
     * @param query 查询条件
     * @return query
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<DeclareVO> selectAllPage(Query query) {
    	Map<String, Object> map = new HashMap<>(0);
    	String title = null;
        Object level = query.getCondition().get("level");
        Object status = query.getCondition().get("status");
        Object special = query.getCondition().get("special");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object username = query.getCondition().get("username");
        Object target = query.getCondition().get("target");
        Object mode = query.getCondition().get("mode");
        Object formality = query.getCondition().get("formality");
        Object support = query.getCondition().get("support");
        Object theme = query.getCondition().get("theme");
        Object fund = query.getCondition().get("fund");
        Object scale = query.getCondition().get("scale");
        Object industry = query.getCondition().get("industry");
        Object style = query.getCondition().get("style");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
        	String string = query.getCondition().get("title").toString().trim();
    		// 去除空格，并添加匹配符
    		 title = sortingDataService.replaceSpecialSign(string);
        	map.put("title", title);
    	}
        if (null == query.getCondition().get("title")) {
        	map.put("title", query.getCondition().get("title"));
        }
            List declareVoPage = mapper.selectDeclareVoPage(query, title, level, status, special, startTime, endTime, username,
                    target, mode, formality, support, theme, fund, scale, industry, style, sort, regionCode);
            setDeclareText(declareVoPage,null,"web");
            query.setRecords(declareVoPage);


        return query;
    }

    @Override
    public Page<DeclareVO> selectAllWxPage(Query query) {
        Object title = query.getCondition().get("title");
        Object level = query.getCondition().get("level");
        Object status = query.getCondition().get("status");
        Object special = query.getCondition().get("special");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object username = query.getCondition().get("username");
        Object target = query.getCondition().get("target");
        Object mode = query.getCondition().get("mode");
        Object formality = query.getCondition().get("formality");
        Object support = query.getCondition().get("support");
        Object theme = query.getCondition().get("theme");
        Object fund = query.getCondition().get("fund");
        Object scale = query.getCondition().get("scale");
        Object industry = query.getCondition().get("industry");
        Object style = query.getCondition().get("style");
        Object regionCode = query.getCondition().get("region");
        List declareVoPage = mapper.selectDeclareVoPageWx(query, title, level, status, special, startTime, endTime, 
        		username, target, mode, formality, support, theme, fund, scale, industry, style, regionCode);
        setDeclareText(declareVoPage,null,"web");
        query.setRecords(declareVoPage);
        return query;
    }


    /**
     * 保存
     *
     * @param declareDTO 新增DTO
     * @return query
     */
    @Override
    public R insertDeclare(DeclareDTO declareDTO) {
        GovPolicyDeclare declare = new GovPolicyDeclare();
        BeanUtils.copyProperties(declareDTO, declare);
		declare.setExamineStatus(0);
        Optional<Object> id = Optional.ofNullable(declareDTO.getId());
        if (id.isPresent()) {
            Map<String, Object> entity = MapUtils.beanToMap(declare);
            entity.remove("id");
            try {
                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                declare = new GovPolicyDeclare();
                org.apache.commons.beanutils.BeanUtils.populate(declare, entity);
				declare.setExamineStatus(6);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        declare.setDelFlag(CommonConstant.STATUS_NORMAL);
        declare.setCreatorId(SecurityUtils.getUser().getId());
        declare.setCreateTime(new Date());
        declare.setProcessorId(SecurityUtils.getUser().getId());
        if(declareDTO.getRegionArr() != null && declareDTO.getRegionArr().size() > 0) {
        	String regionArrstring = declareDTO.getRegionArr().toString();
        	String regionArr = StringUtils.substringBetween(regionArrstring, "[", "]");
        	String replaceAll = regionArr.replaceAll(" ", "");
        	declare.setRegionArray(replaceAll);
        }
        declare = this.save(declare);

        // 发文机构
        if (declareDTO.getDispatchList() != null && declareDTO.getDispatchList().size() > 0) {
            saveRelation(declareDTO.getDispatchList(), declare, GovConstant.DECLARE_DISPATCH);
        }
        // 联合发文机构
        if (declareDTO.getUnionList() != null && declareDTO.getUnionList().size() > 0) {
            saveRelation(declareDTO.getUnionList(), declare, GovConstant.DECLARE_UNION);
        }
        // 适用区域
        if (declareDTO.getRegionList() != null && declareDTO.getRegionList().size() > 0) {
            saveRelation(declareDTO.getRegionList(), declare, GovConstant.DECLARE_CITY);
        }
        // 关联政策
        if (declareDTO.getPolicyList() != null && declareDTO.getPolicyList().size() > 0) {
            saveRelation(declareDTO.getPolicyList(), declare, GovConstant.DECLARE_GENERAL);
        }

        // 标签
        Map<String, Object> params = new HashMap<>(0);
        params.put("relationId", declare.getId());
        params.put("node", GovConstant.DECLARE_NODE);
        params.put("tagKeyWords", getTagKeyWords(declareDTO.getTagList()));
        remoteTagService.saveTagRelation(params);
        declareDTO.setId(declare.getId());
        return new R<>(Boolean.TRUE,declare.getId().toString()) ;

    }

    /**
     * 保存并提交
     *
     * @param declareDTO 新增DTO
     * @return query
     */
    @Override
    public Boolean insertDeclareAndCommit(DeclareDTO declareDTO) {
        insertDeclare(declareDTO);
        List<Integer> ids = new ArrayList<>();
        ids.add(declareDTO.getId());
        return commit(ids);
    }


    @Override
    public void updateDeclare(DeclareDTO declareDTO) {
        GovPolicyDeclare declare = new GovPolicyDeclare();
        BeanUtils.copyProperties(declareDTO, declare);
        List<String> regionArrstring = declareDTO.getRegionArr();
        if (null != regionArrstring && regionArrstring.size() > 0) {
            String regionArr = StringUtils.substringBetween(regionArrstring.toString(), "[", "]");
            String replaceAll = regionArr.replaceAll(" ", "");
            declare.setRegionArray(replaceAll);
        }
        declare.setDelFlag(CommonConstant.STATUS_NORMAL);
        declare = this.update(declare);

        // 发文机构
        List<GovRelation> dispatchList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.DECLARE_NODE, declare.getId(), GovConstant.DECLARE_DISPATCH);
        for (GovRelation gr : dispatchList) {
            govRelationMapper.deleteOne(GovConstant.DECLARE_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
        }
        if (declareDTO.getDispatchList() != null && declareDTO.getDispatchList().size() > 0) {
            saveRelation(declareDTO.getDispatchList(), declare, GovConstant.DECLARE_DISPATCH);
        }

        // 联合发文机构
        List<GovRelation> unionList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.DECLARE_NODE, declare.getId(), GovConstant.DECLARE_UNION);
        for (GovRelation gr : unionList) {
            govRelationMapper.deleteOne(GovConstant.DECLARE_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
        }
        if (declareDTO.getUnionList() != null && declareDTO.getUnionList().size() > 0) {
            saveRelation(declareDTO.getUnionList(), declare, GovConstant.DECLARE_UNION);
        }

        // 适用区域
        List<GovRelation> regionList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.DECLARE_NODE, declare.getId(), GovConstant.DECLARE_CITY);
        for (GovRelation gr : regionList) {
            govRelationMapper.deleteOne(GovConstant.DECLARE_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
        }
        if (declareDTO.getRegionList() != null && declareDTO.getRegionList().size() > 0) {
            saveRelation(declareDTO.getRegionList(), declare, GovConstant.DECLARE_CITY);
        }

        // 关联政策
        List<GovRelation> policyList = govRelationMapper.selectByNodeAndIdAndType(GovConstant.DECLARE_NODE, declare.getId(), GovConstant.DECLARE_GENERAL);
        for (GovRelation gr : policyList) {
            govRelationMapper.deleteOne(GovConstant.DECLARE_NODE, gr.getRelationId(), gr.getCorrelationId(), gr.getTypeId());
        }
        if (declareDTO.getPolicyList() != null && declareDTO.getPolicyList().size() > 0) {
            saveRelation(declareDTO.getPolicyList(), declare, GovConstant.DECLARE_GENERAL);
        }

        // 标签
        Map<String, Object> params = new HashMap<>(0);
        params.put("relationId", declare.getId());
        params.put("node", GovConstant.DECLARE_NODE);
        params.put("tagKeyWords", getTagKeyWords(declareDTO.getTagList()));
        remoteTagService.saveTagRelation(params);
    }


    @Override
    public Boolean updateDeclareAndCommit(DeclareDTO declareDTO) {
        updateDeclare(declareDTO);
        List<Integer> ids = new ArrayList<>();
        ids.add(declareDTO.getId());
        return commit(ids);
    }

    @Override
    public void deleteDeclare(Integer id) {
        GovPolicyDeclare declare = mapper.selectById(id);
        if (declare == null) {
            return;
        }
        declare.setDelFlag(CommonConstant.STATUS_DEL);
        // 申报政策逻辑删
        mapper.updateById(declare);
    }

    /**
     * 回收站申报政策列表
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<DeclareVO> selectRecyclePage(Query query) {
        Object title = query.getCondition().get("title");
        Object username = query.getCondition().get("username");
        query.setRecords(mapper.selectRecyclePage(query, title, username));
        return query;
    }

    /**
     * 回收站查询单个
     */
    @Override
    public DeclareVO selectRecycleById(Integer id) {
        return mapper.selectRecycleById(id);
    }

    /**
     * 回收站彻底删除
     */
    @Override
    public Boolean recycleDelete(Integer id) {
        // 删除申报政策关联
        EntityWrapper<GovRelation> e2 = new EntityWrapper<>();
        e2.where("node = {0}", GovConstant.DECLARE_NODE).where("relation_id = {0}", id);
        govRelationMapper.delete(e2);

        // 删除申报政策关联的标签
        Map<String, Object> params = new HashMap<>(0);
        params.put("relationId", id);
        params.put("node", GovConstant.DECLARE_NODE);
        remoteTagService.deleteTagRelation(id, GovConstant.DECLARE_NODE);

        mapper.deleteById(id);
        return Boolean.TRUE;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<DeclareVO> selectPageByTagId(Query query) {
        Object tagId = query.getCondition().get("tagId");
        query.setRecords(mapper.selectDeclareVoPageByTagId(query, tagId));
        return query;
    }

    @Override
    public GovPolicyDeclare save(GovPolicyDeclare declare) {
        this.insert(declare);
        return declare;
    }

    @Override
    public GovPolicyDeclare update(GovPolicyDeclare declare) {
        this.updateById(declare);
        return declare;
    }

    @Override
    public Boolean repeat(String title) {
        List<GovPolicyDeclare> list = mapper.repeat(title);
        if (list != null && list.size() > 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * 将前台获取的接口参数标签集合转换成字符串
     */
    private String getTagKeyWords(List<String> tags) {
        StringBuilder tagKeyWords = new StringBuilder();
        if (tags != null) {
            Set<String> set = new HashSet<>(tags);
            String[] tagNames = set.toArray(new String[0]);
            for (int i = 0; i < tagNames.length; i++) {
                if (i != tagNames.length - 1) {
                    tagKeyWords.append(tagNames[i]).append(",");
                } else {
                    tagKeyWords.append(tagNames[i]);
                }
            }
        }
        return tagKeyWords.toString();
    }

    /**
     * 关联新增，直接使用id
     */
    private GovRelation findOrCreate(GovPolicyDeclare declare, Integer id, String number) {
        GovRelation bean = govRelationMapper.findOne(GovConstant.DECLARE_NODE, declare.getId(), id, number);
        if (bean == null) {
            GovRelationType type = govRelationTypeMapper.selectByNumber(number);
            bean = new GovRelation(GovConstant.DECLARE_NODE, declare.getId(), id, type.getId());
            return bean;
        }
        return null;
    }

    /**
     * 保存关联
     */
    private void saveRelation(List<Integer> ids, GovPolicyDeclare declare, String number) {
        for (Integer id : ids) {
            GovRelation relation = findOrCreate(declare, id, number);
            if (relation != null) {
                govRelationMapper.insert(relation);
            }
        }
    }

    @Override
    public Boolean retreatPolicy(Query query) {
        int id = Integer.parseInt(query.getCondition().get("id").toString());
        Object retreatContent = query.getCondition().get("retreatContent");
        GovPolicyDeclare govPolicyDeclare = this.selectById(id);
        int isExamined = 3;
        if (isExamined == govPolicyDeclare.getExamineStatus()) {
            if (null == govPolicyDeclare.getRetreatCount() || govPolicyDeclare.getRetreatCount() == 0) {
                govPolicyDeclare.setRetreatCount(1);
            } else {
                govPolicyDeclare.setRetreatCount(govPolicyDeclare.getRetreatCount() + 1);
            }
            govPolicyDeclare.setRetreatContent((String) retreatContent);
            govPolicyDeclare.setRetreatUser(SecurityUtils.getUser().getId());
            govPolicyDeclare.setExamineStatus(5);
            this.updateById(govPolicyDeclare);
            markRecord(4, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), (String) retreatContent, govPolicyDeclare.getTitle());
            reduceCount(govPolicyDeclare.getProcessorId());
        }
        return Boolean.TRUE;
    }

    @Override
    public Page<DeclareVO> selectSelfPage(Query query) {
        Object title = null;
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
    		String string = query.getCondition().get("title").toString().trim();
        	try {
    			title = URLDecoder.decode(string, "UTF-8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        }
        Object processorId = SecurityUtils.getUser().getId();
        Object examineStatus = query.getCondition().get("examineStatus");
        Object level = query.getCondition().get("level");
        Object status = query.getCondition().get("status");
        Object special = query.getCondition().get("special");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object username = query.getCondition().get("username");
        Object target = query.getCondition().get("target");
        Object mode = query.getCondition().get("mode");
        Object formality = query.getCondition().get("formality");
        Object support = query.getCondition().get("support");
        Object theme = query.getCondition().get("theme");
        Object fund = query.getCondition().get("fund");
        Object scale = query.getCondition().get("scale");
        Object industry = query.getCondition().get("industry");
        Object style = query.getCondition().get("style");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        List selfPage = mapper.selectSelfPage(query, title, processorId, examineStatus, level, status, special, startTime, endTime, null, username,
                target, mode, formality, support, theme, fund, scale, industry, style, sort, regionCode, prop, order);

        query.setRecords(selfPage);
        return query;
    }

    @Override
    public Page<DeclareVO> selectExaminePage(Query query) {
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
        Object level = query.getCondition().get("level");
        Object status = query.getCondition().get("status");
        Object special = query.getCondition().get("special");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object username = query.getCondition().get("username");
        Object target = query.getCondition().get("target");
        Object mode = query.getCondition().get("mode");
        Object formality = query.getCondition().get("formality");
        Object support = query.getCondition().get("support");
        Object theme = query.getCondition().get("theme");
        Object fund = query.getCondition().get("fund");
        Object scale = query.getCondition().get("scale");
        Object industry = query.getCondition().get("industry");
        Object style = query.getCondition().get("style");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        List examinePage = mapper.selectExaminePage(query, title, processorName, examineStatus, level, status, special, startTime, endTime, null, username,
                target, mode, formality, support, theme, fund, scale, industry, style, sort, regionCode, prop, order);
        query.setRecords(examinePage);
        return query;
    }

    @Override
    public Boolean commit(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyDeclare govPolicyDeclare = this.selectById(id);
            if (null == govPolicyDeclare.getTitle() || null == govPolicyDeclare.getSource()
                    || null == govPolicyDeclare.getTheme() || null == govPolicyDeclare.getScale()
                    || null == govPolicyDeclare.getIndustry() || null == govPolicyDeclare.getFormality()
                    || null == govPolicyDeclare.getSupport() || null == govPolicyDeclare.getTarget()
                    || null == govPolicyDeclare.getRegion() || null == govPolicyDeclare.getCondition()
                    || null == govPolicyDeclare.getProcess() || null == govPolicyDeclare.getPublishTime()) {
                return Boolean.FALSE;
            }
            if (StringUtils.isEmpty(govPolicyDeclare.getTitle()) || StringUtils.isEmpty(govPolicyDeclare.getSource())
                    || StringUtils.isEmpty(govPolicyDeclare.getTheme()) || StringUtils.isEmpty(govPolicyDeclare.getScale())
                    || StringUtils.isEmpty(govPolicyDeclare.getIndustry()) || StringUtils.isEmpty(govPolicyDeclare.getFormality())
                    || StringUtils.isEmpty(govPolicyDeclare.getSupport()) || StringUtils.isEmpty(govPolicyDeclare.getTarget())
                    || StringUtils.isEmpty(govPolicyDeclare.getRegion()) || StringUtils.isEmpty(govPolicyDeclare.getCondition())
                    || StringUtils.isEmpty(govPolicyDeclare.getProcess()) || StringUtils.isEmpty(govPolicyDeclare.getPublishTime().toString())) {
                return Boolean.FALSE;
            }
            if (0 == govPolicyDeclare.getExamineStatus() || 4 == govPolicyDeclare.getExamineStatus() || 6 == govPolicyDeclare.getExamineStatus()) {
                govPolicyDeclare.setExamineStatus(1);
                govPolicyDeclare.setModifiedTime(new Date());
                govPolicyDeclare.setCommitTime(new Date());
                markRecord(1, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), "提交", govPolicyDeclare.getTitle());
                this.updateById(govPolicyDeclare);
            }
        }
        return Boolean.TRUE;
    }


    @Override
    public Boolean reCommit(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyDeclare govPolicyDeclare = this.selectById(id);
            if (1 == govPolicyDeclare.getExamineStatus()) {
                govPolicyDeclare.setExamineStatus(0);
                this.updateById(govPolicyDeclare);
                markRecord(1, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), "重新提交", govPolicyDeclare.getTitle());
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean giveUpProcess(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyDeclare govPolicyDeclare = this.selectById(id);
            if (0 == govPolicyDeclare.getExamineStatus() || -1 == govPolicyDeclare.getExamineUser()) {
                if (null != govPolicyDeclare.getProcessorId()) {
                    String scrapyId = "";
                    if (null != govPolicyDeclare.getScrapyId()) {
                        scrapyId = Integer.toString(govPolicyDeclare.getScrapyId());
                    }
                    String processorId = govPolicyDeclare.getProcessorId().toString();
                    if (StringUtils.isNotEmpty(scrapyId) && StringUtils.isNotEmpty(processorId)) {
                        mapper.updateForGiveUpProcess(scrapyId, processorId);
                        mapper.updateForGiveUpProcessToDeclare(scrapyId, processorId);
                    }
                }
            } else {
                return Boolean.FALSE;
            }
            markRecord(5, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), "自放弃加工", govPolicyDeclare.getTitle());
            this.deleteById(govPolicyDeclare.getId());
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean accept(Integer id) {
        return mapper.upToAccept(id);
    }

    @Override
    public Boolean doExamine(List<Integer> ids) {
        for (Integer id : ids) {
            GovPolicyDeclare govPolicyDeclare = this.selectById(id);
            if (1 == govPolicyDeclare.getExamineStatus() || 2 == govPolicyDeclare.getExamineStatus()) {
                govPolicyDeclare.setExamineStatus(3);
                govPolicyDeclare.setExamineDate(new Date());
                govPolicyDeclare.setExamineUser(SecurityUtils.getUser().getId());
                this.updateById(govPolicyDeclare);
                markRecord(2, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), "审核通过", govPolicyDeclare.getTitle());
                increaseCount(govPolicyDeclare.getProcessorId());
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
        GovPolicyDeclare govPolicyDeclare = this.selectById(id);
        int isCommit = 1;
        int isAccept = 2;
        if (isCommit == govPolicyDeclare.getExamineStatus() || isAccept == govPolicyDeclare.getExamineStatus()) {
            if (null == govPolicyDeclare.getRetreatCount() || govPolicyDeclare.getRetreatCount() == 0) {
                govPolicyDeclare.setRetreatCount(1);
            } else {
                govPolicyDeclare.setRetreatCount(govPolicyDeclare.getRetreatCount() + 1);
            }
            govPolicyDeclare.setRetreatContent((String) retreatContent);
            govPolicyDeclare.setRetreatUser(SecurityUtils.getUser().getId());
            govPolicyDeclare.setExamineStatus(4);
            this.updateById(govPolicyDeclare);
            markRecord(3, govPolicyDeclare.getId(), govPolicyDeclare.getProcessorId(), (String) retreatContent, govPolicyDeclare.getTitle());
            reduceCount(govPolicyDeclare.getProcessorId());
            return Boolean.TRUE;
		}else {
			return Boolean.FALSE;
		}
    }

    private void increaseCount(Integer processorId) {
        GovPolicyExamineCount govPolicyExamineCount = govPolicyExamineCountService.selectByProcessorId(processorId);
        if (null == govPolicyExamineCount) {
            govPolicyExamineCount = new GovPolicyExamineCount();
            govPolicyExamineCount.setMark(2.0);
            //初始化
            govPolicyExamineCount.setCreateTime(new Date());
            govPolicyExamineCount.setProcessorId(processorId);
            govPolicyExamineCount.setDelFlag("0");
            govPolicyExamineCount.setAgreeCount(1);
        } else {
            govPolicyExamineCount.setMark(govPolicyExamineCount.getMark() + 2);
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
        govPolicyExamineRecord.setPolicyType(1);
        govPolicyExamineRecordService.insert(govPolicyExamineRecord);
    }


    /**
     * 分页查询信息
     *
     * @param query 查询条件
     * @return query
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<DeclareVO> selectConsolePage(Query query) {
        Object title = query.getCondition().get("title");
        if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			String string = query.getCondition().get("title").toString().trim();
    		try {
				title = URLDecoder.decode(string, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
        Object level = query.getCondition().get("level");
        Object status = query.getCondition().get("status");
        Object special = query.getCondition().get("special");
        Object startTime = query.getCondition().get("startTime");
        Object endTime = query.getCondition().get("endTime");
        Object username = query.getCondition().get("username");
        Object target = query.getCondition().get("target");
        Object mode = query.getCondition().get("mode");
        Object formality = query.getCondition().get("formality");
        Object support = query.getCondition().get("support");
        Object theme = query.getCondition().get("theme");
        Object fund = query.getCondition().get("fund");
        Object scale = query.getCondition().get("scale");
        Object industry = query.getCondition().get("industry");
        Object style = query.getCondition().get("style");
        Object sort = query.getCondition().get("sort");
        Object regionCode = query.getCondition().get("region");
        Object prop = query.getCondition().get("prop");
        Object order = query.getCondition().get("order");
        if (GovConstant.PUSH_TIME.equals(sort)) {
            List declareVoPage = mapper.selectDeclareVoPageForConsole(query, title, level, status, special, startTime, endTime, null, username,
                    target, mode, formality, support, theme, fund, scale, industry, style, sort, regionCode, prop, order);
            query.setRecords(declareVoPage);
            return query;
        }

        if (GovConstant.CLICK_VIEWS.equals(sort)) {
            List declareVoPage = mapper.selectDeclareVoPageForConsole(query, title, level, status, special, startTime, endTime, sort, username,
                    target, mode, formality, support, theme, fund, scale, industry, style, null, regionCode, prop, order);
            query.setRecords(declareVoPage);
            return query;
        }
        List declareVoPage = mapper.selectDeclareVoPageForConsole(query, title, level, status, special, startTime, endTime, null, username,
                target, mode, formality, support, theme, fund, scale, industry, style, null, regionCode, prop, order);
        query.setRecords(declareVoPage);
        return query;
    }

    @Override
    public List<DeclareVO> selectMatching(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = StringUtils.split(ids, ",");
            List<Integer> integers = Lists.newArrayList();
            for (int i = 0; i < split.length; i++) {
                integers.add(Integer.valueOf(split[i]));
            }
            List<DeclareVO> declareVOS = mapper.selectMatching(ids, integers);

            declareVOS.stream().forEach(generalVO ->
                    generalVO.setSimilarityDegree
                            (StringMatching.levenshtein(ids, generalVO.getRelatedTags())));

            Collections.sort(declareVOS, Comparator.comparing(DeclareVO::getSimilarityDegree).reversed());
            if (declareVOS.size() > 5) {
                return declareVOS.subList(0, 5);
            }
            return declareVOS;
        }
        return Lists.newArrayList();
    }

    @Override
    public List<DeclareVO> selctRelation(String ids, Integer id) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = StringUtils.split(ids, ",");
            List<Integer> integers = Lists.newArrayList();
            for (int i = 0; i < split.length; i++) {
                integers.add(Integer.valueOf(split[i]));
            }
            return mapper.selctRelation(integers, id);
        }
        return Lists.newArrayList();
    }

    /**
     * 申报对比
     */
    @Override
    public List<ComparisonVO> selectComparisonByDeclare(Integer[] ids) {
        List<Integer> asList = Arrays.asList(ids);
        return mapper.selectComparisonByDeclare(asList);
    }
    
    
    /**
	 * 重构页面逻辑
	 * @param allForConsole
	 * @param mark
	 */
	private void setDeclareText(List<DeclareVO> declareVOList,DeclareVO declare,String mark) {
		if (null != mark && declareVOList.size() > 0) {
		List<Integer> generalIds = new ArrayList<Integer>();
		for (DeclareVO generalId : declareVOList) {
			generalIds.add(generalId.getId());
		}
		List<CommonVO> selectGeneralTag = mapper.selectNewDeclareTag(generalIds);
		List<CommonVO> selectGeneralDispatch = mapper.selectNewDeclareDispatch(generalIds);
		for (DeclareVO generalVO : declareVOList) {
			for (CommonVO commonVO : selectGeneralTag) {
				if (generalVO.getId().equals(commonVO.getRelationId())) {
					commonVO.setCommonId(commonVO.getTagId());
					commonVO.setCommonName(commonVO.getTagName());
					generalVO.getTagList().add(commonVO);
				}
			}
			for (CommonVO commonVO : selectGeneralDispatch) {
				if (generalVO.getId().equals(commonVO.getDispatchId())) {
					commonVO.setCommonId(commonVO.getDispatchId());
					commonVO.setCommonName(commonVO.getDispatchName());
					generalVO.getDispatchList().add(commonVO);
				}
			}

		 }
		}
		if (null != mark && null != declare) {
			List<Integer> generalIds = new ArrayList<Integer>();
			generalIds.add(declare.getId());
			List<CommonVO> selectGeneralTag = mapper.selectNewDeclareTag(generalIds);
			List<CommonVO> selectGeneralDispatch = mapper.selectNewDeclareDispatch(generalIds);
			for (CommonVO commonVO : selectGeneralTag) {
				if (declare.getId().equals(commonVO.getRelationId())) {
					commonVO.setCommonId(commonVO.getTagId());
					commonVO.setCommonName(commonVO.getTagName());
					declare.getTagList().add(commonVO);
				}
			}
			for (CommonVO commonVO : selectGeneralDispatch) {
				if (declare.getId().equals(commonVO.getDispatchId())) {
					commonVO.setCommonId(commonVO.getDispatchId());
					commonVO.setCommonName(commonVO.getDispatchName());
					declare.getDispatchList().add(commonVO);
				}
			}
		}
		
	}
	/**
	 * 重构单个政策逻辑代码，增加查询速度
	 * @param generalVO
	 */
	private void setDeclareValue(DeclareVO declareVO) {
		if (StringUtils.isNotBlank(declareVO.getRegionArrString())) {
			String[] split = declareVO.getRegionArrString().split(",");
			declareVO.setRegionArr(Arrays.asList(split));
		}
		String[] numberList = new String[] {"DECLARE_TARGET","POLICY_THEME","POLICY_SCALE","POLICY_INDUSTRY","POLICY_LEVEL","POLICY_STYLE"};
        Map<String, List<DictValueVO>> dictMap = remoteDictService.getDictMap(numberList);
        if (StringUtils.isNotBlank(declareVO.getTarget())) {
        	String[] targetSplit = declareVO.getTarget().split(",");
        	List<DictValueVO> targetList = dictMap.get("DECLARE_TARGET");
        	for (int i = 0; i < targetSplit.length; i++) {
        		for (DictValueVO dictValueVO : targetList) {
        			if (dictValueVO.getKey().equals(targetSplit[i])) {
        				declareVO.getTargetList().add(dictValueVO.getValue());
        			}
        		}
        		
        	}
        	
        }
        if (StringUtils.isNotBlank(declareVO.getTheme())) {
        	String[] themeSplit = declareVO.getTheme().split(",");
        	List<DictValueVO> themeList = dictMap.get("POLICY_THEME");
        	for (int i = 0; i < themeSplit.length; i++) {
        		for (DictValueVO dictValueVO : themeList) {
        			if (dictValueVO.getKey().equals(themeSplit[i])) {
        				declareVO.getThemeList().add(dictValueVO.getValue());
        			}
        		}
        		
        	}
        	
        }
        if (StringUtils.isNotBlank(declareVO.getScale())) {
        	
        	String[] scaleSplit = declareVO.getScale().split(",");
        	List<DictValueVO> scaleList = dictMap.get("POLICY_SCALE");
        	for (int i = 0; i < scaleSplit.length; i++) {
        		for (DictValueVO dictValueVO : scaleList) {
        			if (dictValueVO.getKey().equals(scaleSplit[i])) {
        				declareVO.getScaleList().add(dictValueVO.getValue());
        			}
        		}
        		
        	}
        }
		if (StringUtils.isNotBlank(declareVO.getIndustry())) {
			String[] industrySplit = declareVO.getIndustry().split(",");
			List<DictValueVO> industryList = dictMap.get("POLICY_INDUSTRY");
			for (int i = 0; i < industrySplit.length; i++) {
				for (DictValueVO dictValueVO : industryList) {
					if (dictValueVO.getKey().equals(industrySplit[i])) {
						declareVO.getIndustryList().add(dictValueVO.getValue());
					}
				}
				
			}		
			
		}
				String level = declareVO.getLevel();
				if (null != level) {
					
					List<DictValueVO> levelList = dictMap.get("POLICY_LEVEL");
					for (DictValueVO dictValueVO : levelList) {
						if (dictValueVO.getKey().equals(level)) {
							declareVO.setLevelName(dictValueVO.getValue());
						}
					}
				}
				String style = declareVO.getStyle();
				if (null != style) {
					List<DictValueVO> styleList = dictMap.get("POLICY_STYLE");
					for (DictValueVO dictValueVO : styleList) {
						if (dictValueVO.getKey().equals(style)) {
							declareVO.setStyleName(dictValueVO.getValue());
						}
					}	
					
				}
	}
}
