package com.web3.crawler.dto;

import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.constants.TaskType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: smy
 * @Date: 2023/1/12 8:23 PM
 */
@Data
public class Task {
    /**
     * 任务id
     */
    private Long id;
    /**
     * 调度时间
     */
    private LocalDateTime scheduleTime;
    /**
     * 处理器名称
     */
    private String processor;
    /**
     * 任务类型
     */
    private TaskType taskType = TaskType.Auto;
    /**
     * 任务状态
     */
    private TaskStatus status = TaskStatus.Start;
    private Object extraInfo;
}
