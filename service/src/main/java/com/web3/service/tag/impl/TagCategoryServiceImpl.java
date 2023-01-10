package com.web3.service.tag.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.meta.entity.TagCategory;
import com.web3.dal.meta.service.TagCategoryMapperService;
import com.web3.framework.exception.ParamException;
import com.web3.service.tag.TagCategoryService;
import jakarta.annotation.Resource;
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
public class TagCategoryServiceImpl implements TagCategoryService {

    @Resource
    private TagCategoryMapperService tagCategoryMapperService;


    @Override
    public boolean create(String name, String operator) {
        // 重名校验
        QueryWrapper<TagCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        if (tagCategoryMapperService.count(wrapper) > 0) {
            throw new ParamException("400", "category name is duplicate");
        }

        TagCategory tagCategory = new TagCategory();
        tagCategory.setName(name);
        tagCategory.setCreator(operator);
        tagCategory.setModifier(operator);

        return tagCategoryMapperService.save(tagCategory);
    }

    @Override
    public boolean updateName(Long id, String name, String operator) {

        TagCategory tagCategory = new TagCategory();
        tagCategory.setId(id);
        tagCategory.setName(name);
        tagCategory.setModifier(operator);

        return tagCategoryMapperService.updateById(tagCategory);
    }
}
