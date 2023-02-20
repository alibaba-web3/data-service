package com.web3.dal.data.mapper;

import java.util.List;

import com.web3.dal.data.entity.Price1d;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web3.dal.data.entity.Tvl1d;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author system
 * @since 2023-01-09
 */
public interface Price1dMapper extends BaseMapper<Price1d> {

    /**
     * replace into 批量插入
     *
     * @param list
     */
    void replaceIntoBatch(List<Price1d> list);

}
