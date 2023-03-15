package com.web3.dal.meta.mapper;

import com.web3.dal.meta.entity.AddressTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 地址标签 Mapper 接口
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
@Mapper
public interface AddressTagMapper extends BaseMapper<AddressTag> {

    List<AddressTag> selectByParam(AddressTag query);

}
