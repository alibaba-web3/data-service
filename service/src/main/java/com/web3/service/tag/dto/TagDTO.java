package com.web3.service.tag.dto;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Data
public class TagDTO {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签分类 id
     */
    private String categoryId;

    /**
     * 是否官方 0,1
     */
    private String official;

    /**
     * 标签描述
     */
    private String note;

}
