package com.web3.service.tag;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.entity.TagCategory;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */

/**
 * <p>
 * 标签分类 服务类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-04
 */
public interface TagCategoryService extends IService<TagCategory> {

    /**
     * 创建标签分类
     *
     * @param name     分类名称
     * @param operator 操作人
     * @return 调用是否成功
     */
    boolean create(String name,String operator);

    boolean updateName(Long id,String name,String operator);

}
