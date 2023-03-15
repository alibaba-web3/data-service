package com.web3.dal.meta.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.dal.meta.entity.Tag;

/**
 * @Author: smy
 * @Date: 2023/1/10 11:59 AM
 */
public interface AddressTagMapperService extends IService<AddressTag> {

    List<AddressTag> listByAddress(String address);

    PageInfo<AddressTag>  listPage(AddressTag query, Integer pageNum, Integer pageSize);

}
