package com.web3.service.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Data
@Schema(name = "TagDTO", title = "标签对象")
public class TagDTO {

    /**
     * 标签名称
     */
    @Schema(title = "标签名称")
    private String name;

    /**
     * 标签分类 id
     */
    @Schema(title = "标签分类 Id")
    private String categoryId;

    /**
     * 是否官方 0,1
     */
    @Schema(title = "是否官方 0, 1")
    private String official;

    /**
     * 标签描述
     */
    @Schema(title = "标签描述")
    private String note;

}
