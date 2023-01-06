package com.web3.service.tag.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.entity.AddressTag;
import com.web3.entity.Tag;
import com.web3.framework.exception.ParamException;
import com.web3.mapper.AddressTagMapper;
import com.web3.service.tag.AddressTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.service.tag.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址标签 服务实现类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
@Service
public class AddressTagServiceImpl extends ServiceImpl<AddressTagMapper, AddressTag> implements AddressTagService {

    @Resource
    private TagService tagService;

    @Override
    public AddressTag create(String address, String tagId, String origin, String operator) {
        Tag tag = tagService.getById(tagId);

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

        save(addressTag);

        return addressTag;
    }

    @Override
    public List<AddressTag> listByAddress(String address) {

        QueryWrapper<AddressTag> wrapper = new QueryWrapper<>();
        wrapper.eq("address", address);

        return list(wrapper);
    }
}
