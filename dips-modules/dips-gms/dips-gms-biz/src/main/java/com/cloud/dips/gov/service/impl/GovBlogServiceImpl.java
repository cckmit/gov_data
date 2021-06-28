package com.cloud.dips.gov.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.gov.api.entity.GovBlog;
import com.cloud.dips.gov.mapper.GovBlogMapper;
import com.cloud.dips.gov.service.GovBlogService;
import org.springframework.stereotype.Service;

/**
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */

@Service("govBlogService")
public class GovBlogServiceImpl extends ServiceImpl<GovBlogMapper, GovBlog> implements GovBlogService {

}
