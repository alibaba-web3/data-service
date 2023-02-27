package com.web3.dal.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web3.dal.data.entity.EthereumV2Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Mapper
public interface EthereumV2Mapper extends BaseMapper<EthereumV2Data> {

    void batchInsertOrUpdate(@Param("list") List<EthereumV2Data> list);
}
