package com.web3.dal.data.service;

import java.util.List;

import com.web3.dal.data.entity.EthereumBalanceLatest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 最新 eth 余额 服务类
 * </p>
 *
 * @author system
 * @since 2023-01-11
 */
public interface EthereumBalanceLatestMapperService extends IService<EthereumBalanceLatest> {

    List<EthereumBalanceLatest> listAmountEmpty();

    List<EthereumBalanceLatest> listAmountNotEmpty();

}
