package com.web3.dal.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.EthereumErc20BalanceDay;
import com.web3.dal.data.mapper.EthereumErc20BalanceDayMapper;
import com.web3.dal.data.service.EthereumErc20BalanceDayMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/16
 */
@Service
public class EthereumErc20BalanceDayMapperServiceImpl extends ServiceImpl<EthereumErc20BalanceDayMapper, EthereumErc20BalanceDay> implements EthereumErc20BalanceDayMapperService {

    @Override
    public List<EthereumErc20BalanceDay> queryByAddress(String address) {
        QueryWrapper<EthereumErc20BalanceDay> queryWrapper = new QueryWrapper();
        queryWrapper.eq("owner", address);
        return list(queryWrapper);
    }

}
