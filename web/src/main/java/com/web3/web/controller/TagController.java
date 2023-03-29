package com.web3.web.controller;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.web3.dal.meta.entity.AddressTag;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.TagService;
import com.web3.service.tag.dto.AddressTagDTO;
import com.web3.service.tag.dto.TagDTO;
import com.web3.web.entity.ResultUtils;
import com.web3.dal.meta.entity.TagCategory;
import com.web3.web.entity.vo.PageResult;
import com.web3.web.entity.vo.Result;
import com.web3.service.tag.TagCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
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
@Tag(name = "TagController", description = "标签相关接口")
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
    @Operation(summary = "获取标签分类列表", description = "返回 Category List")
    @ApiResponse(responseCode = "200", description = "标签分类列表")
    @GetMapping("/category/list")
    Result<List<TagCategory>> listTagCategory() {
        return ResultUtils.createSuccessRes(tagCategoryService.list());
    }

    /**
     * 创建标签分类
     */
    @PostMapping("/category")
    @Operation(summary = "创建标签分类", description = "创建标签分类")
    @ApiResponse(responseCode = "200", description = "创建标签分类结果")
    Result<Boolean> createTagCategory(@RequestParam String name) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(tagCategoryService.create(name, "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 更新标签分类名称
     */
    @PutMapping("/category/{id}")
    @Operation(summary = "更新标签分类名称", description = "更新标签分类名称")
    @ApiResponse(responseCode = "200", description = "更新标签分类名称结果")
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
    @Operation(summary = "删除标签分类", description = "删除标签分类")
    @ApiResponse(responseCode = "200", description = "删除标签分类结果")
    Result<Boolean> removeTagCategory(@PathVariable String id) {
        return ResultUtils.createSuccessRes(tagCategoryService.removeById(id));
    }

    /**
     * 标签列表
     */
    @GetMapping("/list")
    @Operation(summary = "标签列表", description = "查询标签列表")
    @ApiResponse(responseCode = "200", description = "标签列表")
    Result<List<TagDTO>> listTag() {
        return ResultUtils.createSuccessRes(tagService.list());
    }

    /**
     * 创建标签
     */
    @PostMapping("")
    @Operation(summary = "创建标签", description = "创建标签")
    @ApiResponse(responseCode = "200", description = "创建标签结果")
    Result<Boolean> createTag(@RequestParam String name, @RequestParam String categoryId, @RequestParam(required = false) String note
            , @RequestParam String creator) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(tagService.create(name, categoryId, note, "0", creator));
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    Result<Boolean> removeTag(@PathVariable("id") String id) {
        return ResultUtils.createSuccessRes(tagService.removeById(id));
    }

    /**
     * 地址打上标签
     */
    @PostMapping("/address")
    @Operation(summary = "地址打上标签", description = "地址上标")
    @ApiResponse(responseCode = "200", description = "地址打标记录")
    Result<AddressTag> createAddressTag(@RequestParam String address, @RequestParam String tagId) {
        // TODO 获取登录用户信息
        return ResultUtils.createSuccessRes(addressTagService.create(address, tagId, "user", "0xfe731b7ed91C7655B87c062a2A27625AeCB0ddD5"));
    }

    /**
     * 地址的标签列表
     */
    @GetMapping("/address")
    @Operation(summary = "地址的标签列表", description = "返回 Tag-Address List")
    @ApiResponse(responseCode = "200", description = "地址的标签列表")
    Result<List<AddressTagDTO>> listAddressTag(@RequestParam String address) {
        return ResultUtils.createSuccessRes(addressTagService.listByAddress(address));
    }

    /**
     * 分页获取标签列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页获取标签列表", description = "返回 Tag List")
    @ApiResponse(responseCode = "200", description = "标签列表")
    Result<PageResult<TagDTO>> listPageTag(@RequestParam(required = false, value = "categoryId") String categoryId,
        @RequestParam(required = false, value = "pageNum") Integer pageNum,
        @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        if (ObjectUtils.anyNull(pageNum, pageSize)) {
            pageNum = 1;
            pageSize = 10;
        }
        PageInfo<TagDTO> pageTag = tagService.listPageTag(categoryId, pageNum, pageSize);
        return ResultUtils.createSuccessPageRes(pageTag);
    }

    /**
     * 根据标签分页查询地址列表
     */
    @GetMapping("/address/page")
    @Operation(summary = "根据标签分页查询地址列表", description = "返回 Tag-Address List")
    @ApiResponse(responseCode = "200", description = "标签地址列表")
    Result<PageResult<AddressTagDTO>> listPageAddress(@RequestParam(required = false, value = "tagId") String tagId,
        @RequestParam(required = false, value = "creator") String creator,
                                                      @RequestParam(required = false, value = "pageNum") Integer pageNum,
        @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        return ResultUtils.createSuccessPageRes(addressTagService.listPageAddressByTag(tagId, creator, pageNum, pageSize));
    }

    /**
     * 删除地址标签记录
     */
    @DeleteMapping("/address")
    @Operation(summary = "删除地址标签记录", description = "删除地址-标签记录")
    @ApiResponse(responseCode = "200", description = "删除地址-标签记录结果")
    Result<Boolean> deleteAddressTag(@RequestParam(value = "addressTagId") Long addressTagId) {
        if (addressTagId == null) {
            return ResultUtils.createSuccessRes(Boolean.TRUE);
        }
        return ResultUtils.createSuccessRes(addressTagService.delete(addressTagId));
    }

}
