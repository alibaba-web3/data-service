package com.web3.dal.meta.service;

import java.util.List;

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

    /**
     * 根据标签分类 id 查询标签
     *
     * @param categoryId 标签分类 id
     * @return
     */
    List<Tag> listByCategoryId(String categoryId);

}
