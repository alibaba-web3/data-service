package com.web3.dal.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web3.dal.data.entity.ProtocolProfit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
@Mapper
public interface ProtocolProfitMapper extends BaseMapper<ProtocolProfit> {

    void batchInsertOrUpdate(@Param("list") List<ProtocolProfit> list);

}
