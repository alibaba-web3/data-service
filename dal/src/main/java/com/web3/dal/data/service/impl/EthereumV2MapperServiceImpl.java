package com.web3.dal.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.EthereumV2Data;
import com.web3.dal.data.mapper.EthereumV2Mapper;
import com.web3.dal.data.service.EthereumV2MapperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Service
public class EthereumV2MapperServiceImpl extends ServiceImpl<EthereumV2Mapper, EthereumV2Data> implements EthereumV2MapperService {

    @Resource
    private EthereumV2Mapper ethereumV2Mapper;

    @Override
    public boolean saveOrUpdateBatchData(List<EthereumV2Data> list) {
        return saveOrUpdateBatch(list);
    }

    @Override
    public void batchInsertOrUpdateData(List<EthereumV2Data> list) {
        ethereumV2Mapper.batchInsertOrUpdate(list);
    }
}
