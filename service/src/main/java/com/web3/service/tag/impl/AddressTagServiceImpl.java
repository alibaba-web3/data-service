package com.web3.service.tag.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.service.AddressTagMapperService;
import com.web3.dal.meta.service.TagMapperService;
import com.web3.framework.consts.TagConst;
import com.web3.framework.enums.TagOfficialEnum;
import com.web3.framework.enums.TagOriginEnum;
import com.web3.framework.exception.ParamException;
import com.web3.service.file.FileService;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.TagCategoryService;
import com.web3.service.tag.TagService;
import com.web3.service.tag.dto.AddressTagDTO;
import com.web3.service.tag.dto.TagDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 地址标签 服务实现类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
@Service
public class AddressTagServiceImpl implements AddressTagService {

    @Resource
    private TagMapperService tagMapperService;
    @Resource
    private AddressTagMapperService addressTagMapperService;

    @Resource
    private TagService tagService;

    @Resource
    private FileService fileService;

    @Override
    public AddressTag create(String address, String tagId, String origin, String operator) {
        Tag tag = tagMapperService.getById(tagId);

        if (tag == null) {
            throw new ParamException("400", "tagId not exit");
        }

        AddressTag addressTag = new AddressTag();
        addressTag.setTagId(tagId);
        addressTag.setAddress(address);
        addressTag.setCategoryId(tag.getCategoryId());
        addressTag.setCreator(operator);
        addressTag.setModifier(operator);
        addressTag.setOrigin(origin);

        addressTagMapperService.save(addressTag);

        return addressTag;
    }

    @Override
    public List<AddressTagDTO> listByAddress(String address) {

        List<AddressTag> addressTagList = addressTagMapperService.listByAddress(address);
        if (CollectionUtils.isEmpty(addressTagList)) {
            return null;
        }

        return addressTagList.stream().map(addressTag -> {
            AddressTagDTO dto = new AddressTagDTO();
            Tag tag = tagMapperService.getById(addressTag.getTagId());
            if (tag != null) {
                addressTag.setName(tag.getName());
            }
            BeanUtils.copyProperties(addressTag, dto);
            return dto;
        }).toList();

    }

    @Override
    public PageInfo<AddressTagDTO> listPageAddressByTag(String tagId, String creator, Integer pageNum, Integer pageSize) {
        AddressTag query = new AddressTag();
        query.setTagId(tagId);
        query.setCreator(creator);
        PageInfo<AddressTag> pageInfo = addressTagMapperService.listPage(query, pageNum, pageSize);

        PageInfo<AddressTagDTO> pageRes = new PageInfo<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<AddressTagDTO> list = pageInfo.getList().stream().map(addressTag -> {
                AddressTagDTO dto = new AddressTagDTO();
                Tag tag = tagMapperService.getById(addressTag.getTagId());
                BeanUtils.copyProperties(addressTag, dto);
                dto.setOfficial(tag.getOfficial());
                return dto;
            }).toList();
            pageRes.setList(list);
            pageRes.setPages(pageInfo.getPages());
            pageRes.setTotal(pageInfo.getTotal());
        }
        return pageRes;
    }

    @Override
    public Boolean delete(Long addressTagId) {
        return addressTagMapperService.removeById(addressTagId);
    }

    @Override
    public List<String> listAddressByTagCategory(String categoryId) {
        List<Tag> binanceTagList = tagMapperService.listByCategoryId(categoryId);
        if (!CollectionUtils.isEmpty(binanceTagList)) {
            List<Long> tagIdList = binanceTagList.stream().map(Tag::getId).toList();

            List<AddressTag> addressTagList = addressTagMapperService.listByTagIds(tagIdList);
            if (!CollectionUtils.isEmpty(addressTagList)) {
                return addressTagList.stream().map(AddressTag::getAddress).toList();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> listBinanceAddress() {
        return listAddressByTagCategory("2");
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

                create(element[1], tag.getId().toString(), TagOriginEnum.EtherScan.getKey(), TagConst.systemUser);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
