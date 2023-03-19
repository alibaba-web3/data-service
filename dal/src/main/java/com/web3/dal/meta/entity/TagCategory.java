package com.web3.dal.meta.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 标签分类
 * </p>
 *
 * @author mianyun
 * @since 2023-01-04
 */
@Getter
@Setter
@TableName("tag_category")
@Schema(name = "TagCategory", title = "标签分类对象")
public class TagCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "标签分类id")
    private Long id;

    /**
     * 创建时间
     */
    @Schema(title = "标签分类创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @Schema(title = "标签分类修改时间")
    private LocalDateTime gmtModified;

    /**
     * 创建人
     */
    @Schema(title = "标签分类创建人")
    private String creator;

    /**
     * 修改人
     */
    @Schema(title = "标签分类修改人")
    private String modifier;

    /**
     * 逻辑删除 0/1
     */
    @Schema(title = "标签分类是否 (逻辑) 删除 0/1")
    @TableLogic
    private String deleted;

    /**
     * 分类名称
     */
    @Schema(title = "标签分类名称")
    private String name;
}
