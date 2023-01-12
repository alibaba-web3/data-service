package com.web3.dal.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.EthereumBalanceLatest;
import com.web3.dal.data.mapper.EthereumBalanceLatestMapper;
import com.web3.dal.data.service.EthereumBalanceLatestMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 最新 eth 余额 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-11
 */
@Service
public class EthereumBalanceLatestServiceImpl extends ServiceImpl<EthereumBalanceLatestMapper, EthereumBalanceLatest> implements EthereumBalanceLatestMapperService {

    @Override
    public List<EthereumBalanceLatest> listAmountEmpty() {
        QueryWrapper<EthereumBalanceLatest> wrapper = new QueryWrapper<>();
        wrapper.isNull("amount");
        wrapper.last("limit 2000");

        return list(wrapper);
    }

    @Override
    public List<EthereumBalanceLatest> listAmountNotEmpty() {
        QueryWrapper<EthereumBalanceLatest> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("amount");
        wrapper.last("limit 20");

        return list(wrapper);
    }
}
