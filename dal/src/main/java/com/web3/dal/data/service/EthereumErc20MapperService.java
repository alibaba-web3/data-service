package com.web3.dal.data.service;

import java.util.List;

import com.web3.dal.data.entity.EthereumErc20;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author system
 * @since 2023-02-03
 */
public interface EthereumErc20MapperService extends IService<EthereumErc20> {

    /**
     * 根据 Symbol 搜索
     *
     * @param symbol 币种
     * @return 代币信息
     */
    List<EthereumErc20> searchBySymbol(String symbol);

}
