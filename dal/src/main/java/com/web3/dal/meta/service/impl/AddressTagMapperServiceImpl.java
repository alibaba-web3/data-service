package com.web3.dal.meta.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.entity.Tag;
import com.web3.dal.meta.mapper.AddressTagMapper;
import com.web3.dal.meta.service.AddressTagMapperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: smy
 * @Date: 2023/1/10 12:02 PM
 */
@Service
public class AddressTagMapperServiceImpl extends ServiceImpl<AddressTagMapper, AddressTag> implements AddressTagMapperService {

    @Resource
    private AddressTagMapper addressTagMapper;

    @Override
    public List<AddressTag> listByAddress(String address) {
        QueryWrapper<AddressTag> wrapper = new QueryWrapper<>();
        wrapper.eq("address", address);

        return list(wrapper);
    }

    @Override
    public PageInfo<AddressTag> listPage(AddressTag query, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AddressTag> addressTagList = addressTagMapper.selectByParam(query);
        return new PageInfo<AddressTag>(addressTagList);
    }
}
