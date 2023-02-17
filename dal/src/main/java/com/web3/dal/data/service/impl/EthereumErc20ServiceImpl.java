package com.web3.dal.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.EthereumErc20;
import com.web3.dal.data.mapper.EthereumErc20Mapper;
import com.web3.dal.data.service.EthereumErc20MapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-02-03
 */
@Service
public class EthereumErc20ServiceImpl extends ServiceImpl<EthereumErc20Mapper, EthereumErc20> implements EthereumErc20MapperService {

    @Override
    public List<EthereumErc20> searchBySymbol(String symbol) {
        QueryWrapper<EthereumErc20> wrapper = new QueryWrapper<>();
        wrapper.like("symbol", symbol);
        return list(wrapper);
    }

    @Override
    public List<EthereumErc20> listByContractAddress(List<String> contractAddressList) {
        QueryWrapper<EthereumErc20> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("contract_address", contractAddressList);
        List<EthereumErc20> list = list(queryWrapper);
        return list;
    }
}
