package com.web3.service.tag.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.service.AddressTagMapperService;
import com.web3.dal.meta.service.TagMapperService;
import com.web3.framework.exception.ParamException;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.dto.AddressTagDTO;
import com.web3.service.tag.dto.TagDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
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
                BeanUtils.copyProperties(addressTag, dto);
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
}
