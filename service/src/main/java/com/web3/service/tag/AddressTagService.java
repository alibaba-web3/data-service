package com.web3.service.tag;

import com.web3.dal.meta.entity.AddressTag;

import java.util.List;

/**
 * <p>
 * 地址标签 服务类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
public interface AddressTagService {

    AddressTag create(String address, String tagId, String origin, String operator);

    List<AddressTag> listByAddress(String address);

}
