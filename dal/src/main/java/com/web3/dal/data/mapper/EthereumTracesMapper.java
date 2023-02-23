package com.web3.dal.data.mapper;

import com.web3.dal.data.entity.EthereumTraces;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author system
 * @since 2023-02-17
 */
@Mapper
public interface EthereumTracesMapper extends BaseMapper<EthereumTraces> {

    void batchInsertOrUpdate(@Param("list") List<EthereumTraces> list);

}
