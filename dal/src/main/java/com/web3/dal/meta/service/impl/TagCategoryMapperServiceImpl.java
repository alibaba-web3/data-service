package com.web3.dal.meta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.meta.entity.TagCategory;
import com.web3.dal.meta.mapper.TagCategoryMapper;
import com.web3.dal.meta.service.TagCategoryMapperService;
import org.springframework.stereotype.Service;

/**
 * @Author: smy
 * @Date: 2023/1/10 12:02 PM
 */
@Service
public class TagCategoryMapperServiceImpl extends ServiceImpl<TagCategoryMapper, TagCategory> implements TagCategoryMapperService {
}
