package com.web3.dal.meta.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 数据爬虫任务相关状态
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Getter
@Setter
@TableName("crawler_task")
public class CrawlerTask {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 是否删除
     */
    @TableLogic
    private String deleted;

    /**
     * 任务状态：Start|Running|Success|Failed
     */
    private String status;

    /**
     * 调度时间
     */
    private LocalDateTime scheduleTime;

    /**
     * 任务处理器名称
     */
    private String processor;

    /**
     * 任务相关信息
     */
    private String taskInfo;
}
