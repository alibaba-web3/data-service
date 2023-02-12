package com.web3.service.tag.dto;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/3
 */
@Data
public class AddressTagDTO {

    /**
     * 打上标签的地址
     */
    private String address;

    /**
     * 标签 id
     */
    private String tagId;

    /**
     * 标签分类 id
     */
    private String categoryId;

    /**
     * 标签来源：  user 用户打标 system 系统打标 spider 爬虫 nansen (具体外部平台名称)
     */
    private String origin;

    /**
     * 标签名称
     */
    private String name;

}
