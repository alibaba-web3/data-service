package com.web3.dal.data.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.web3.dal.data.entity.Tvl1d;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * TVL 日维度数据 服务类
 * </p>
 *
 * @author system
 * @since 2023-02-06
 */
public interface Tvl1dMapperService extends IService<Tvl1d> {

    /**
     * 查询最新的 tvl 数据
     *
     * @param protocol 协议
     * @return tvl数据
     */
    Tvl1d getLatestTvl(String protocol);

}
