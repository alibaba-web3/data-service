package com.web3.dal.meta.service.impl;

import com.web3.dal.meta.entity.EthereumBalanceLatest;
import com.web3.dal.meta.mapper.EthereumBalanceLatestMapper;
import com.web3.dal.meta.service.EthereumBalanceLatestMapperService;
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

}
