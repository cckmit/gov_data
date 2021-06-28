package com.cloud.dips.gov.service.impl;

import com.cloud.dips.gov.api.constant.GovConstant;
import com.cloud.dips.gov.api.dto.*;
import com.cloud.dips.gov.api.vo.*;
import com.cloud.dips.gov.service.*;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author yinzan
 * @ClassName: MobileColumnServiceImpl
 * @ProjectName dips
 * @Description: TODO
 * @date 2019/3/11下午3:41
 */
@Service
@AllArgsConstructor
public class MobileColumnServiceImpl implements MobileColumnService {
    private final GovPolicyDeclareService govPolicyDeclareService;
    private final GovPolicyExplainService govPolicyExplainService;
    private final GovInformationService govInformationService;
    private final GovPolicyGeneralService govPolicyGeneralService;


    @Override
    public Boolean movepolicy(String ids, String source, String target) {
        /**
         * 发文单位（关联机构）
         */
        List<Integer> dispatchList = Lists.newArrayList();
        /**
         * 标签（关联标签）
         */
        List<String> tagList = Lists.newArrayList();
        /**
         * 政策依据（关联政策）
         */
        List<Integer> policyList = Lists.newArrayList();
        /**
         * 联合发文机构
         */
        List<Integer> unionList = Lists.newArrayList();


        String[] split = StringUtils.split(ids,",");
        for (String id : split) {
            try {
                //判断来源数据
                switch (source) {
                    //申报
                    case "declare":

                        DeclareVO declareVO = govPolicyDeclareService.selectDeclareVOByIdForConsole(Integer.valueOf(id.trim()));
                        Optional<DeclareVO> declare = Optional.ofNullable(declareVO);
                        if (declare.isPresent()) {
                            dispatchList = declareVO.getDispatchList().stream().map(CommonVO::getCommonId).collect(Collectors.toList());
                            tagList = declareVO.getTagList().stream().map(CommonVO::getCommonName).collect(Collectors.toList());
                            declareVO.getRegionList().clear();
                            move(declareVO, target, dispatchList, tagList, policyList, unionList);
                            //删除到回收站
                            govPolicyDeclareService.deleteDeclare(Integer.valueOf(id.trim()));
                        }
                        break;
                    //通用
                    case "general":
                        GeneralVO generalVO = govPolicyGeneralService.selectGeneralVOByIdForConsole(Integer.valueOf(id.trim()));
                        Optional<GeneralVO> general = Optional.ofNullable(generalVO);
                        if (general.isPresent()) {
                            dispatchList = generalVO.getDispatchList().stream().map(CommonVO::getCommonId).collect(Collectors.toList());
                            tagList = generalVO.getTagList().stream().map(CommonVO::getCommonName).collect(Collectors.toList());
                            generalVO.getRegionList().clear();
                            unionList = generalVO.getUnionList().stream().map(CommonVO::getCommonId).collect(Collectors.toList());
                            move(generalVO, target, dispatchList, tagList, policyList, unionList);
                            govPolicyGeneralService.logicDelete(new Integer[]{Integer.valueOf(id.trim())});
                        }
                        break;
                    //政策解读
                    case "explain":
                        ExplainVO explainVO = govPolicyExplainService.selectAllVoForConsole(Integer.valueOf(id.trim()));
                        Optional<ExplainVO> explain = Optional.ofNullable(explainVO);
                        if (explain.isPresent()) {
                            tagList = explainVO.getTagList().stream().map(CommonVO::getCommonName).collect(Collectors.toList());
                            dispatchList = explainVO.getPolicyList().stream().map(CommonVO::getCommonId).collect(Collectors.toList());

                            policyList = explainVO.getPolicyList().stream().map(CommonVO::getCommonId).collect(Collectors.toList());
                            move(explainVO, target, dispatchList, tagList, policyList, unionList);

                            //删除到回收站
                            govPolicyExplainService.deleteExplain(new Integer[]{Integer.valueOf(id.trim())});
                        }
                        break;
                    //政策资讯
                    case "information":
                        InformationVO informationVO = govInformationService.selectInformationByIdForConsole(Integer.valueOf(id.trim()));

                        Optional<InformationVO> information = Optional.ofNullable(informationVO);
                        if (information.isPresent()) {
                            tagList = informationVO.getTagList().stream().map(CommonVO::getCommonName).collect(Collectors.toList());
                            move(informationVO, target, dispatchList, tagList, policyList, unionList);
                            govInformationService.deleteByIds(Arrays.asList(Integer.valueOf(id.trim())));
                        }
                        break;
                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;

    }

    /**
     * 实现具体逻辑方法
     *
     * @param object
     * @param target 1、把要移动数据查询出来
     *               2、把移动数据转换到目标表
     *               3、
     */
    public void move(Object object, String target, List<Integer> dispatchList, List<String> tagList, List<Integer> policyList, List<Integer> unionList) {

        if (target.trim().equals(GovConstant.DECLARE_NODE)) {
            //判断申报
            DeclareDTO vo = new DeclareDTO();
            BeanUtils.copyProperties(object, vo);
            vo.setMark(GovConstant.DECLARE_NODE);
            vo.getTagList().clear();
            vo.setPolicyList(policyList);
            tagList.stream().forEach(cen -> vo.getTagList().add(cen.toString()));
            vo.setUnionList(unionList);
            vo.setDispatchList(dispatchList);


            govPolicyDeclareService.insertDeclare(vo);

        } else if (target.trim().equals(GovConstant.GOV_GENERAL_NODE)) {
            //判断通用
            GeneralDTO generalDTO = new GeneralDTO();
            BeanUtils.copyProperties(object, generalDTO);
            generalDTO.setDispatchList(dispatchList);
            generalDTO.getTagList().clear();
            generalDTO.setPolicyList(policyList);
            tagList.stream().forEach(cen -> generalDTO.getTagList().add(cen.toString()));

            generalDTO.setMark(GovConstant.GOV_GENERAL_NODE);

            govPolicyGeneralService.insertPolicyGeneral(generalDTO);


        } else if (target.trim().equals(GovConstant.GOV_EXPLAIN_NODE)) {
            //解读

            ExplainDTO explainDTO = new ExplainDTO();
            BeanUtils.copyProperties(object, explainDTO);
            explainDTO.getTagList().clear();
            tagList.stream().forEach(cen -> explainDTO.getTagList().add(cen.toString()));
            explainDTO.setMark(GovConstant.GOV_EXPLAIN_NODE);
            govPolicyExplainService.insertExplain(explainDTO);


        } else if (target.trim().equals(GovConstant.GOV_INFORMATION_NODE)) {

           //政策资讯
            InformationDTO informationDTO = new InformationDTO();
            BeanUtils.copyProperties(object, informationDTO);
            informationDTO.getTagList().clear();
            tagList.stream().forEach(cen -> informationDTO.getTagList().add(cen.toString()));
            informationDTO.setMark(GovConstant.GOV_INFORMATION_NODE);
            govInformationService.insertInformation(informationDTO);


        }
    }
}
