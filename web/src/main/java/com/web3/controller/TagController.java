package com.web3.controller;

import java.util.List;

import com.web3.entity.TagCategory;
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
    List<TagCategory> listTagCategory() {
        return tagCategoryService.list();
    }

    /**
     * 创建标签分类
     */
    @PostMapping("/category")
    boolean createTagCategory(@RequestParam String name) {
        // TODO 获取登录用户信息
        return tagCategoryService.create(name, "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5");
    }

    /**
     * 更新标签分类名称
     */
    @PutMapping("/category/{id}")
    boolean updateTagCategoryName(@PathVariable Long id, @RequestParam String name) {
        // TODO 获取登录用户信息
        return tagCategoryService.updateName(id, name, "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5");
    }

    @DeleteMapping("/category/{id}")
    boolean removeTagCategory(@PathVariable String id) {
        return tagCategoryService.removeById(id);
    }
}
