package com.web3.dal.meta.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.mapper.AddressTagMapper;
import com.web3.dal.meta.service.AddressTagMapperService;
import org.springframework.stereotype.Service;

/**
 * @Author: smy
 * @Date: 2023/1/10 12:02 PM
 */
@Service
public class AddressTagMapperServiceImpl extends ServiceImpl<AddressTagMapper, AddressTag> implements AddressTagMapperService {

    @Override
    public List<AddressTag> listByAddress(String address) {
        QueryWrapper<AddressTag> wrapper = new QueryWrapper<>();
        wrapper.eq("address", address);

        return list(wrapper);
    }
}
