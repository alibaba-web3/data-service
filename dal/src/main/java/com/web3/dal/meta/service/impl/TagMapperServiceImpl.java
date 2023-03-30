package com.web3.dal.meta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.mapper.TagMapper;
import com.web3.dal.meta.service.TagMapperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: smy
 * @Date: 2023/1/10 12:01 PM
 */
@Service
public class TagMapperServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagMapperService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public PageInfo<Tag> listPage(Tag query, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.selectByParam(query);
        return new PageInfo<Tag>(tags);
    }

    @Override
    public List<Tag> listByCategoryId(String categoryId) {
        QueryWrapper<Tag> query = new QueryWrapper<>();
        query.eq("category_id", categoryId);

        return list(query);
    }
}
