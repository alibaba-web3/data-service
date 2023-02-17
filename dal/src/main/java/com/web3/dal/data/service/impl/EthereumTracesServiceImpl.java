package com.web3.dal.data.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.dal.data.entity.EthereumTraces;
import com.web3.dal.data.mapper.EthereumTracesMapper;
import com.web3.dal.data.service.EthereumTracesMapperService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-02-17
 */
@Service
public class EthereumTracesServiceImpl extends ServiceImpl<EthereumTracesMapper, EthereumTraces> implements EthereumTracesMapperService {

    @Override
    public List<EthereumTraces> list(LocalDateTime start, LocalDateTime end) {
        QueryWrapper<EthereumTraces> wrapper = new QueryWrapper<>();
        wrapper.ge("block_timestamp", start);
        wrapper.le("block_timestamp", end);

        return list(wrapper);
    }
}
