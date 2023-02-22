package com.web3.service.address;

import com.web3.service.address.param.TransformBalanceReq;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    /**
     * 余额转换
     *
     * @param req 交易对 & 数量
     * @return 目标价值
     */
    BigDecimal transformBalance(TransformBalanceReq req);

    /**
     * 批量余额转换
     *
     * @param req 多个交易对 & 数量
     * @return 目标价值: key: 资产类型  value: 换算的目标价值
     */
    Map<String, BigDecimal> transformBalance(List<TransformBalanceReq> req);

    /**
     * 余额价值涨跌
     *
     * @param address      地址
     * @param assetsType   资产类型
     * @param balanceValue 余额价值
     * @return
     */
    BigDecimal balanceValueTrend(String address, String assetsType, BigDecimal balanceValue);

    Set<String> getTraceAddressList(LocalDateTime start);

}
