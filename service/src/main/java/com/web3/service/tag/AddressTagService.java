package com.web3.service.tag;

import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.service.tag.dto.AddressTagDTO;
import com.web3.service.tag.dto.TagDTO;

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

    /**
     * 分页查询 Tag-Address
     *
     * @param tagId    标签 id
     * @param creator  创建人
     * @param pageNum  页码
     * @param pageSize 页容量
     * @return
     */
    PageInfo<AddressTagDTO> listPageAddressByTag(String tagId, String creator, Integer pageNum, Integer pageSize);

    /**
     * 删除地址-标签记录
     *
     * @param addressTagId
     * @return
     */
    Boolean delete(Long addressTagId);

}
