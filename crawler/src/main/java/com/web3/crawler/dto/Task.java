package com.web3.crawler.dto;

import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.constants.TaskType;
import com.web3.crawler.processors.IProcessor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    private Map<String, Object> extraInfo;

    public void addExtraInfo(String key, Object info) {
        if (extraInfo == null) {
            extraInfo = new HashMap<>(8);
        }
        extraInfo.put(key, info);
    }

    public static Task generate(IProcessor processor, LocalDateTime scheduleTime,TaskType taskType) {
        ProcessorConfig processorConfig = processor.getClass().getAnnotation(ProcessorConfig.class);
        assert processorConfig != null;
        Task task = new Task();
        task.setScheduleTime(scheduleTime);
        task.setProcessor(processorConfig.name());
        task.setTaskType(taskType);
        task.setStatus(TaskStatus.Start);
        return task;
    }
}
