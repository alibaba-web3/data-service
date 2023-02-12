package com.web3.crawler.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.service.EthereumBlocksMapperService;
import jakarta.annotation.Resource;
import jnr.ffi.annotations.In;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: smy
 * @Date: 2023/1/12 8:06 PM
 */
@Service
public class EthereumBlockService {

    @Resource
    private EthereumBlocksMapperService ethereumBlocksMapperService;

    public Long getBlockNumberByTime(LocalDateTime dateTime) {
        QueryWrapper<EthereumBlocks> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("timestamp", dateTime).orderByDesc("blockNumber");
        EthereumBlocks ethereumBlock = ethereumBlocksMapperService.getOne(queryWrapper);
        if (ethereumBlock == null) {
            return 0L;
        }
        return ethereumBlock.getBlockNumber();
    }
}
