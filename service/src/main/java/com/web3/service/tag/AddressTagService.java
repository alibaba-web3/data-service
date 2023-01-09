package com.web3.service.tag;

import java.util.List;

import com.web3.dal.meta.entity.AddressTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 地址标签 服务类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
public interface AddressTagService extends IService<AddressTag> {

    AddressTag create(String address, String tagId, String origin, String operator);

    List<AddressTag> listByAddress(String address);

}
