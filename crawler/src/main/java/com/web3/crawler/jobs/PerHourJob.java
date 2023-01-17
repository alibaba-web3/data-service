package com.web3.crawler.jobs;

import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.constants.Interval;
import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.constants.TaskType;
import com.web3.crawler.dto.Task;
import com.web3.crawler.processors.IProcessor;
import com.web3.crawler.service.TaskService;
import com.web3.framework.utils.DateUtils;
import com.web3.framework.utils.SpringContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: smy
 * @Date: 2023/1/12 3:13 PM
 */
@Service
@Slf4j
public class PerHourJob {

    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 10, 10L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(8), new DefaultThreadFactory(PerHourJob.class));

    private final static Set<IProcessor> processors = new HashSet<>(8);
    private final List<Future> futureList = new ArrayList<>(8);

    @Resource
    protected TaskService taskService;


    /**
     * 初始化processor列表
     */
    @PostConstruct
    public void init() {
        Map<String, Object> map =SpringContext.getBeansWithAnnotation(ProcessorConfig.class);
        if (map == null || map.isEmpty()) {
            return;
        }
        map.values().forEach(o -> {
            if (o instanceof IProcessor) {
                ProcessorConfig config = o.getClass().getAnnotation(ProcessorConfig.class);
                if (Interval.Hour.equals(config.value())) {
                    processors.add((IProcessor) o);
                }
            }
        });
    }

    /**
     * 每小时执行一次,晚一分钟减少block数据不全情况
     */
    @Scheduled(cron = "0 1 * * * ?", zone = DateUtils.ZERO_TIMEZONE)
    public void execute1() {
        log.info("---start per hour job---");
        if (CollectionUtils.isEmpty(processors)) {
            return;
        }
        futureList.clear();
        CountDownLatch downLatch = new CountDownLatch(processors.size());
        processors.forEach(processor -> {
            Task task = generateTask(processor);
            taskService.record(task);
            poolExecutor.submit(() -> processor.run(downLatch, task));
        });
        try {
            if (downLatch.await(45, TimeUnit.MINUTES)) {
                return;
            }
            /**
             * cancel掉未完成的任务，防止长期占用
             */
            futureList.forEach(future -> {
                if (future.isDone()) {
                    return;
                }
                future.cancel(true);
            });
        } catch (InterruptedException ignore) {}
        finally {
            futureList.clear();
        }
        log.info("---end per hour job---");
    }

    private Task generateTask(IProcessor processor) {
        ProcessorConfig processorConfig = processor.getClass().getAnnotation(ProcessorConfig.class);
        LocalDateTime scheduleTime = LocalDateTime.now(DateUtils.getZeroZoneId()).truncatedTo(ChronoUnit.SECONDS);
        Task task = new Task();
        task.setScheduleTime(scheduleTime);
        task.setProcessor(processorConfig.name());
        task.setTaskType(TaskType.Auto);
        task.setStatus(TaskStatus.Start);
        return task;
    }
}
