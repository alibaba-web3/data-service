package com.web3.dal.data.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.dal.data.entity.EthereumV2Data;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
public interface EthereumV2MapperService extends IService<EthereumV2Data> {

    /**
     * 批量插入/更新数据
     *
     * @param list
     * @return
     */
    boolean saveOrUpdateBatchData(List<EthereumV2Data> list);

    void batchInsertOrUpdateData(List<EthereumV2Data> list);

}
