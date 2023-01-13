package com.web3.crawler.processors;

import com.web3.crawler.dto.Task;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: smy
 * @Date: 2023/1/12 3:49 PM
 */
public interface IProcessor {

    /**
     * processor线程入口
     *
     * @param doneSignal
     * @param task 任务信息
     */
    default void run(CountDownLatch doneSignal, Task task)  {
        try {
            execute(task);
        } finally {
            doneSignal.countDown();
        }
    }

    /**
     * processor主处理逻辑
     *
     * @param task 任务信息
     */
    void execute(Task task);
}
