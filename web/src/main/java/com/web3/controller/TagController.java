package com.web3.controller;

import java.util.List;

import com.web3.entity.ResultUtils;
import com.web3.entity.TagCategory;
import com.web3.entity.vo.Result;
import com.web3.service.Tag.TagCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/4
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    private TagCategoryService tagCategoryService;

    /**
     * 标签分类列表
     */
    @GetMapping("/category/list")
    Result<List<TagCategory>> listTagCategory() {
        return ResultUtils.createSuccessRes(tagCategoryService.list());
    }

    /**
     * 创建标签分类
     */
    @PostMapping("/category")
    Result<Boolean> createTagCategory(@RequestParam String name) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(tagCategoryService.create(name, "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 更新标签分类名称
     */
    @PutMapping("/category/{id}")
    Result<Boolean> updateTagCategoryName(@PathVariable Long id, @RequestParam String name) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(tagCategoryService.updateName(id, name, "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 删除标签分类
     *
     * @param id 分类id
     * @return 删除成功
     */
    @DeleteMapping("/category/{id}")
    Result<Boolean> removeTagCategory(@PathVariable String id) {
        return ResultUtils.createSuccessRes(tagCategoryService.removeById(id));
    }
}
