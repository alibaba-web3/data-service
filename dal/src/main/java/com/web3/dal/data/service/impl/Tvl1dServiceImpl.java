package com.web3.dal.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.mapper.Tvl1dMapper;
import com.web3.dal.data.service.Tvl1dMapperService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * TVL 日维度数据 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-02-06
 */
@Service
public class Tvl1dServiceImpl extends ServiceImpl<Tvl1dMapper, Tvl1d> implements Tvl1dMapperService {

    @Override
    public Tvl1d getLatestTvl(String protocol) {
        QueryWrapper<Tvl1d> wrapper = new QueryWrapper<>();
        wrapper.eq("name", protocol)
            .orderByDesc("date")
            .last("limit 1");

        return getOne(wrapper);
    }

    @Override
    public void replaceIntoBatch(List<Tvl1d> list) {
        baseMapper.replaceIntoBatch(list);
    }
}
