package com.web3.service.tag;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */

import java.io.Serializable;
import java.util.List;

import com.web3.dal.meta.entity.TagCategory;

/**
 * <p>
 * 标签分类 服务类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-04
 */
public interface TagCategoryService {

    /**
     * 创建标签分类
     *
     * @param name     分类名称
     * @param operator 操作人
     * @return 调用是否成功
     */
    boolean create(String name, String operator);

    boolean updateName(Long id, String name, String operator);

    List<TagCategory> list();

    boolean removeById(Serializable id);

}
