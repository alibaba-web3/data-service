package com.web3.dal.data.mapper;

import java.util.List;

import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.entity.Tvl1d;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * TVL 日维度数据 Mapper 接口
 * </p>
 *
 * @author system
 * @since 2023-02-06
 */
public interface Tvl1dMapper extends BaseMapper<Tvl1d> {

    /**
     * replace into 批量插入
     *
     * @param list
     */
    void replaceIntoBatch(List<Tvl1d> list);

}
