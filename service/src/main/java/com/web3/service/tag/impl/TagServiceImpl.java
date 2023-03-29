package com.web3.service.tag.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.service.AddressTagMapperService;
import com.web3.dal.meta.service.TagMapperService;
import com.web3.framework.consts.TagConst;
import com.web3.framework.enums.TagOfficialEnum;
import com.web3.framework.enums.TagOriginEnum;
import com.web3.service.file.FileService;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.TagService;
import com.web3.service.tag.dto.TagDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapperService tagMapperService;

    @Resource
    private AddressTagService addressTagService;

    @Resource
    private AddressTagMapperService addressTagMapperService;



    @Resource
    private FileService fileService;

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
    @Transactional
    public boolean removeById(String id) {

        // 删除地址和标签的关联数据
        addressTagMapperService.removeByTagId(id);

        // 删除标签
        return tagMapperService.removeById(id);
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

    @Override
    @Transactional
    public void importEtherScanTags(String path, String categoryId) {
        try {
            List<String[]> elements = fileService.readAllCsv(path);
            elements.remove(0);

            elements.forEach(element -> {
                Tag tag = new Tag();
                tag.setName(element[2]);
                tag.setCategoryId(categoryId);
                tag.setOfficial(TagOfficialEnum.OFFICIAL.getKey());
                tag.setCreator(TagConst.systemUser);
                tag.setModifier(TagConst.systemUser);
                tagMapperService.save(tag);

                addressTagService.create(element[1], tag.getId().toString(), TagOriginEnum.EtherScan.getKey(), TagConst.systemUser);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
