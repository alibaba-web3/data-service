package com.web3.service.tag;

import com.web3.dal.meta.entity.AddressTag;
import com.web3.service.tag.dto.AddressTagDTO;

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

    /**
     * 地址打标
     *
     * @param address  被打标的地址
     * @param tagId    标签 id
     * @param origin   打标来源
     * @param operator 操作人
     * @return 地址标签
     */
    AddressTag create(String address, String tagId, String origin, String operator);

    /**
     * 查询地址的标签
     *
     * @param address 地址
     * @return 标签列表
     */
    List<AddressTagDTO> listByAddress(String address);

}
