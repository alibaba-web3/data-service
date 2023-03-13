package com.web3.dal.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.ProtocolProfit;
import com.web3.dal.data.mapper.ProtocolProfitMapper;
import com.web3.dal.data.service.ProtocolProfitMapperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
@Service
public class ProtocolProfitMapperServiceImpl extends ServiceImpl<ProtocolProfitMapper, ProtocolProfit> implements ProtocolProfitMapperService {

    @Resource
    private ProtocolProfitMapper protocolProfitMapper;

    @Override
    public void batchInsertOrUpdateData(List<ProtocolProfit> list) {
        protocolProfitMapper.batchInsertOrUpdate(list);
    }
}
