package com.web3.service.tag.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.service.TagMapperService;
import com.web3.service.tag.TagService;
import com.web3.service.tag.dto.TagDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapperService tagMapperService;

    @Override
    public boolean create(String name, String categoryId, String note, String official, String operator) {

        Tag tag = new Tag();
        tag.setName(name);
        tag.setCategoryId(categoryId);
        tag.setNote(note);
        tag.setOfficial(official);
        tag.setCreator(operator);
        tag.setModifier(operator);

        return tagMapperService.save(tag);
    }

    @Override
    public List<TagDTO> list() {
        List<Tag> tagList = tagMapperService.list();
        if (CollectionUtils.isEmpty(tagList)) {
            return null;
        }

        return tagList.stream().map(tag -> {
            TagDTO dto = new TagDTO();
            BeanUtils.copyProperties(tag, dto);
            return dto;
        }).toList();
    }

    @Override
    public PageInfo<TagDTO> listPageTag(String categoryId, Integer pageNum, Integer pageSize) {
        Tag query = new Tag();
        query.setCategoryId(categoryId);
        PageInfo<Tag> pageInfo = tagMapperService.listPage(query, pageNum, pageSize);

        PageInfo<TagDTO> pageRes = new PageInfo<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<TagDTO> list = pageInfo.getList().stream().map(tag -> {
                TagDTO dto = new TagDTO();
                BeanUtils.copyProperties(tag, dto);
                return dto;
            }).toList();
            pageRes.setList(list);
            pageRes.setPages(pageInfo.getPages());
            pageRes.setTotal(pageInfo.getTotal());
        }
        return pageRes;
    }
}
