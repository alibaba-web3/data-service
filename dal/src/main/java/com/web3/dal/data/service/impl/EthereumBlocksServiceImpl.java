package com.web3.dal.data.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.mapper.EthereumBlocksMapper;
import com.web3.dal.data.service.EthereumBlocksMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Service
public class EthereumBlocksServiceImpl extends ServiceImpl<EthereumBlocksMapper, EthereumBlocks> implements EthereumBlocksMapperService {

    @Override
    public List<EthereumBlocks> list(LocalDateTime start, LocalDateTime end) {
        QueryWrapper<EthereumBlocks> wrapper = new QueryWrapper<>();
        wrapper.ge("timestamp", start);
        wrapper.le("timestamp", end);

        return list(wrapper);
    }
}
