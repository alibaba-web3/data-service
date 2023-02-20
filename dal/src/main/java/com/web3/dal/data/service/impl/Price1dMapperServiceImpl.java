package com.web3.dal.data.service.impl;

import java.util.List;

import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.mapper.Price1dMapper;
import com.web3.dal.data.service.Price1dMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-09
 */
@Service
public class Price1dMapperServiceImpl extends ServiceImpl<Price1dMapper, Price1d> implements Price1dMapperService {

    @Override
    public void replaceIntoBatch(List<Price1d> list) {
        baseMapper.replaceIntoBatch(list);
    }
}
