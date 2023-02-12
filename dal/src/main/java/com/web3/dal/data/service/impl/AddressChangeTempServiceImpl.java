package com.web3.dal.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.mapper.AddressChangeTempMapper;
import com.web3.dal.data.service.AddressChangeTempMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址余额变化中间表 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Service
public class AddressChangeTempServiceImpl extends ServiceImpl<AddressChangeTempMapper, AddressChangeTemp> implements AddressChangeTempMapperService {

    @Override
    public AddressChangeTemp getLatest() {
        QueryWrapper<AddressChangeTemp> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("time");
        wrapper.last("limit 1");

        return getOne(wrapper);
    }
}
