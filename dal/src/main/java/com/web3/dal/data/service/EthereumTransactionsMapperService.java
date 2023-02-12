package com.web3.dal.data.service;

import java.time.LocalDateTime;
import java.util.List;

import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.entity.EthereumTransactions;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
public interface EthereumTransactionsMapperService extends IService<EthereumTransactions> {

    /**
     * 查询一段时间范围内的交易
     * @param start 开始时间
     * @param end 结束时间
     * @return 区块列表
     */
    List<EthereumTransactions> list(LocalDateTime start, LocalDateTime end);

}
