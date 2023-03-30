package com.web3.dal.meta.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 地址标签
 * </p>
 *
 * @author mianyun
 * @since 2023-01-06
 */
@Getter
@Setter
@TableName("address_tag")
@Schema(name = "AddressTag", title = "标签-地址对象")
public class AddressTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "主键")
    private Long id;

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

    /**
     * 创建者
     */
    @Schema(title = "创建者")
    private String creator;

    /**
     * 修改者
     */
    @Schema(title = "修改者")
    private String modifier;

    /**
     * 逻辑删除 0/1
     */
    @Schema(title = "逻辑删除 0/1")
    @TableLogic
    private String deleted;

    /**
     * 打上标签的地址
     */
    @Schema(title = "打上标签的地址")
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
    @Schema(title = "标签来源：  user 用户打标 system 系统打标 spider 爬虫 nansen (具体外部平台名称)")
    private String origin;

}
