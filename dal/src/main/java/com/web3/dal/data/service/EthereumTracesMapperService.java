package com.web3.dal.data.service;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.dal.data.entity.EthereumTraces;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author system
 * @since 2023-02-17
 */
public interface EthereumTracesMapperService extends IService<EthereumTraces> {

    /**
     * 查询一段时间范围内的 trace
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 区块列表
     */
    List<EthereumTraces> list(LocalDateTime start, LocalDateTime end);

}
