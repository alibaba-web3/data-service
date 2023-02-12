package com.web3.service.tag;

import java.util.List;

import com.web3.dal.meta.entity.Tag;
import com.web3.service.tag.dto.TagDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
public interface TagService {

    /**
     * 标签创建
     *
     * @return 创建是否成功
     */
    boolean create(String name, String categoryId, String note, String official, String operator);

    List<TagDTO> list();

}
