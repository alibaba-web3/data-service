package com.web3.dal.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.dal.data.entity.ProtocolProfit;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
public interface ProtocolProfitMapperService extends IService<ProtocolProfit> {

    /**
     * 批量插入和更新数据
     *
     * @param list
     */
    void batchInsertOrUpdateData(List<ProtocolProfit> list);

}
