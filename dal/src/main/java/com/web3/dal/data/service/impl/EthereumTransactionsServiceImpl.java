package com.web3.dal.data.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.entity.EthereumTransactions;
import com.web3.dal.data.mapper.EthereumTransactionsMapper;
import com.web3.dal.data.service.EthereumTransactionsMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Service
public class EthereumTransactionsServiceImpl extends ServiceImpl<EthereumTransactionsMapper, EthereumTransactions> implements EthereumTransactionsMapperService {

    @Override
    public List<EthereumTransactions> list(LocalDateTime start, LocalDateTime end) {
        QueryWrapper<EthereumTransactions> wrapper = new QueryWrapper<>();
        wrapper.ge("block_timestamp", start);
        wrapper.le("block_timestamp", end);
        //wrapper.eq("success", true);
        wrapper.select("distinct `from`", "`to`");

        return list(wrapper);
    }

}
