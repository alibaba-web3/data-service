package com.web3.dal.meta.service.impl;

import com.web3.dal.meta.entity.DataMetric;
import com.web3.dal.meta.mapper.DataMetricMapper;
import com.web3.dal.meta.service.DataMetricMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据相关指标（数据爬取进度|等） 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-12
 */
@Service
public class DataMetricServiceImpl extends ServiceImpl<DataMetricMapper, DataMetric> implements DataMetricMapperService {

}
