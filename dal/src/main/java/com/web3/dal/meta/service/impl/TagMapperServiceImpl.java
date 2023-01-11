package com.web3.dal.meta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.mapper.TagMapper;
import com.web3.dal.meta.service.TagMapperService;
import org.springframework.stereotype.Service;

/**
 * @Author: smy
 * @Date: 2023/1/10 12:01 PM
 */
@Service
public class TagMapperServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagMapperService {
}
