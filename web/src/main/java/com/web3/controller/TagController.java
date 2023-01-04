package com.web3.controller;

import java.util.List;

import com.web3.entity.TagCategory;
import com.web3.service.Tag.TagCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/category/list")
    List<TagCategory> listTagCategory() {
        return tagCategoryService.list();
    }
}
