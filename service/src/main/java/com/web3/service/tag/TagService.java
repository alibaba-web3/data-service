package com.web3.service.tag;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.dal.meta.entity.Tag;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
public interface TagService extends IService<Tag> {

    /**
     * 标签创建
     *
     * @return 创建是否成功
     */
    boolean create(String name, String categoryId, String note, String official, String operator);

}
