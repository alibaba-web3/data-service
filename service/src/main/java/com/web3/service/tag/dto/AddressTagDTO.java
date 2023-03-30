package com.web3.service.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/3
 */
@Data
@Schema(name = "AddressTagDTO", title = "标签-地址对象")
public class AddressTagDTO {

    /**
     * 主键
     */
    @Schema(title = "主键")
    private Long id;

    /**
     * 打上标签的地址
     */
    @Schema(title = "地址")
    private String address;

    /**
     * 标签 id
     */
    @Schema(title = "标签 Id")
    private String tagId;

    /**
     * 标签分类 id
     */
    @Schema(title = "标签分类 Id")
    private String categoryId;

    /**
     * 标签来源：  user 用户打标 system 系统打标 spider 爬虫 nansen (具体外部平台名称)
     */
    @Schema(title = "标签来源: user 用户打标 system 系统打标 spider 爬虫 nansen (具体外部平台名称)")
    private String origin;

    /**
     * 标签名称
     */
    @Schema(title = "标签名称")
    private String name;

    /**
     * 是否官方 0,1
     */
    @Schema(title = "是否官方 0, 1")
    private String official;

}
