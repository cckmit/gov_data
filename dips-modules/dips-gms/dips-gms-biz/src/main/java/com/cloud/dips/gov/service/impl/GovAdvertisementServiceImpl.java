package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.gov.api.entity.GovAdvertisement;
import com.cloud.dips.gov.mapper.GovAdvertisementMapper;
import com.cloud.dips.gov.service.GovAdvertisementService;
import org.springframework.stereotype.Service;

/**
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */

@Service("govAdvertisementService")
public class GovAdvertisementServiceImpl extends ServiceImpl<GovAdvertisementMapper, GovAdvertisement> implements GovAdvertisementService {

}
