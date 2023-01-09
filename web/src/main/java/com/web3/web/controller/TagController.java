package com.web3.web.controller;

import java.util.List;

import com.web3.dal.meta.entity.AddressTag;
import com.web3.entity.Tag;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.TagService;
import com.web3.web.entity.ResultUtils;
import com.web3.dal.meta.entity.TagCategory;
import com.web3.web.entity.vo.Result;
import com.web3.service.tag.TagCategoryService;
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

    @Resource
    private TagService tagService;

    @Resource
    private AddressTagService addressTagService;

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

    /**
     * 标签列表
     */
    @GetMapping("/list")
    Result<List<Tag>> listTag() {
        return ResultUtils.createSuccessRes(tagService.list());
    }

    /**
     * 创建标签
     */
    @PostMapping("")
    Result<Boolean> createTag(@RequestParam String name, @RequestParam String categoryId, @RequestParam(required = false) String note) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(tagService.create(name, categoryId, note, "0", "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 地址打上标签
     */
    @PostMapping("/address")
    Result<AddressTag> createAddressTag(@RequestParam String address, @RequestParam String tagId) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(addressTagService.create(address, tagId, "user", "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 地址的标签列表
     */
    @GetMapping("/address")
    Result<List<AddressTag>> listAddressTag(@RequestParam String address) {
        return ResultUtils.createSuccessRes(addressTagService.listByAddress(address));
    }

}
