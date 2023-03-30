package com.web3.service.tag;

import java.util.List;

import com.github.pagehelper.PageInfo;
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

    /**
     * 删除标签
     *
     * @param id id
     * @return
     */
    boolean removeById(String id);

    List<TagDTO> list();

    /**
     * 分页查询 Tags
     *
     * @param categoryId 标签分类 id
     * @param pageNum    页码
     * @param pageSize   页容量
     * @return
     */
    PageInfo<TagDTO> listPageTag(String categoryId, Integer pageNum, Integer pageSize);

}
