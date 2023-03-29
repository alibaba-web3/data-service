package com.web3.service.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Data
@Schema(name = "TagDTO", title = "标签对象")
public class TagDTO {

    /**
     * 主键
     */
    @Schema(title = "主键")
    private Long id;

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

    /**
     * 创建人
     */
    @Schema(title = "创建人")
    private String creator;

    /**
     * 修改人
     */
    @Schema(title = "修改人")
    private String modifier;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @Schema(title = "修改时间")
    private LocalDateTime gmtModified;

}
