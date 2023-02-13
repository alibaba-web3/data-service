package com.web3.dal.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.EthereumV2Data;
import com.web3.dal.data.mapper.EthereumV2Mapper;
import com.web3.dal.data.service.EthereumV2MapperService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Service
public class EthereumV2MapperServiceImpl extends ServiceImpl<EthereumV2Mapper, EthereumV2Data> implements EthereumV2MapperService {

    @Override
    public boolean saveOrUpdateBatchData(List<EthereumV2Data> list) {
        return saveOrUpdateBatch(list);
    }

}
