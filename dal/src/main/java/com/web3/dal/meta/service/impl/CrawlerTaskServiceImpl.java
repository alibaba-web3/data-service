package com.web3.dal.meta.service.impl;

import com.web3.dal.meta.entity.CrawlerTask;
import com.web3.dal.meta.mapper.CrawlerTaskMapper;
import com.web3.dal.meta.service.CrawlerTaskMapperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据爬虫任务相关状态 服务实现类
 * </p>
 *
 * @author system
 * @since 2023-01-12
 */
@Service
public class CrawlerTaskServiceImpl extends ServiceImpl<CrawlerTaskMapper, CrawlerTask> implements CrawlerTaskMapperService {

}
