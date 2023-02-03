package com.web3.service.tag.impl;

import java.util.List;

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
}
