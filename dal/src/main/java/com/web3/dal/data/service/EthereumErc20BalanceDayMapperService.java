package com.web3.dal.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.dal.data.entity.EthereumErc20BalanceDay;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/16
 */
public interface EthereumErc20BalanceDayMapperService extends IService<EthereumErc20BalanceDay> {

    /**
     * 根据 address 查询
     *
     * @param address
     * @return
     */
    List<EthereumErc20BalanceDay> queryByAddress(String address);

}
