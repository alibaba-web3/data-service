package com.web3.dal.meta.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;

/**
 * @Author: smy
 * @Date: 2023/1/10 11:59 AM
 */
public interface AddressTagMapperService extends IService<AddressTag> {

    List<AddressTag> listByAddress(String address);

    PageInfo<AddressTag> listPage(AddressTag query, Integer pageNum, Integer pageSize);

    void removeByTagId(String tagId);

    /**
     * 根据标签 id 查询标签
     *
     * @param tagId 标签 id
     * @return 标签
     */
    List<AddressTag> listByTagId(String tagId);

    /**
     * 根据标签 id 列表查询标签
     *
     * @param tagIds 标签 id 列表
     * @return 标签列表
     */
    List<AddressTag> listByTagIds(List<Long> tagIds);

}
