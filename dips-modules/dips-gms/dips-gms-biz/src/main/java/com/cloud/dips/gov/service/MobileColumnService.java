package com.cloud.dips.gov.service;

/**
 * @author yinzan
 * @ClassName: MobileColumnService
 * @ProjectName dips
 * @Description: TODO
 * @date 2019/3/11下午3:31
 */
public interface MobileColumnService {
    /**
     *
     * @param ids  源数据id
     * @param source  源数据
     * @param target     目标
     * @return
     */
    Boolean movepolicy(String ids,String source,String target);
}
