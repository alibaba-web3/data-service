package com.web3.dal.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.mapper.AddressChangeTempMapper;
import com.web3.dal.data.service.AddressChangeTempMapperService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Override
    public void replaceIntoBatch(List<AddressChangeTemp> list) {

        int maxSize = 300000;

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (list.size() > maxSize) {
            List<List<AddressChangeTemp>> partitionList = ListUtils.partition(list, maxSize);
            partitionList.stream().parallel().forEach(partition -> {
                baseMapper.replaceIntoBatch(partition);
            });
        } else {
            baseMapper.replaceIntoBatch(list);
        }

    }
}
