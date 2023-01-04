package com.web3.service.Tag.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.entity.TagCategory;
import com.web3.mapper.TagCategoryMapper;
import com.web3.service.Tag.TagCategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签分类 服务实现类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-04
 */
@Service
public class TagCategoryServiceImpl extends ServiceImpl<TagCategoryMapper, TagCategory> implements TagCategoryService {

    @Override
    public boolean create(String name, String operator) {

        TagCategory tagCategory = new TagCategory();
        tagCategory.setName(name);
        tagCategory.setCreator(operator);
        tagCategory.setModifier(operator);

        return this.save(tagCategory);
    }

    @Override
    public boolean updateName(Long id, String name, String operator) {

        TagCategory tagCategory = new TagCategory();
        tagCategory.setId(id);
        tagCategory.setName(name);
        tagCategory.setModifier(operator);

        return this.updateById(tagCategory);
    }
}
