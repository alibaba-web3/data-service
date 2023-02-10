package com.web3.service.address.dto;

import java.util.Set;

import com.web3.dal.data.entity.EthereumBlocks;
import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/10
 */
@Data
public class BalanceChangeAddressInfo {

    Set<String> addressSet;

    EthereumBlocks firstBlock;
    EthereumBlocks lastBlock;

}
