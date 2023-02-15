package com.web3.service.address;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/13
 */
public interface BalanceService {

    /**
     * 添加余额有变化的地址记录
     *
     * @param start 开始时间
     * @param end   结束时间
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void addBalanceRecord(LocalDateTime start, LocalDateTime end) throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * 补充历史数据
     */
    void fillHistoryRecord();

}
