package com.web3.dal.meta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.Tag;

/**
 * @Author: smy
 * @Date: 2023/1/10 11:57 AM
 */
public interface TagMapperService extends IService<Tag> {

    PageInfo<Tag> listPage(Tag query, Integer pageNum, Integer pageSize);

}
