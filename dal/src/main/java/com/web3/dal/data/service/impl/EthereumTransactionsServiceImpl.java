package com.web3.dal.data.service.impl;

import com.web3.dal.data.entity.EthereumTransactions;
import com.web3.dal.data.mapper.EthereumTransactionsMapper;
import com.web3.dal.data.service.EthereumTransactionsMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Service
public class EthereumTransactionsServiceImpl extends ServiceImpl<EthereumTransactionsMapper, EthereumTransactions> implements EthereumTransactionsMapperService {

}
