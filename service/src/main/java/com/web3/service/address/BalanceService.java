package com.web3.service.address;

import java.time.LocalDateTime;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/13
 */
public interface BalanceService {

    void addBalanceRecord(LocalDateTime start, LocalDateTime end);

}
