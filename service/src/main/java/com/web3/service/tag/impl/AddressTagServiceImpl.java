package com.web3.service.tag.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.service.AddressTagMapperService;
import com.web3.dal.meta.service.TagMapperService;
import com.web3.framework.exception.ParamException;
import com.web3.service.tag.AddressTagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<AddressTag> listByAddress(String address) {

        QueryWrapper<AddressTag> wrapper = new QueryWrapper<>();
        wrapper.eq("address", address);

        return addressTagMapperService.list(wrapper);
    }
}
