package com.web3.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.web3.crawler.dto.Task;
import com.web3.crawler.jobs.EthereumV2Job;
import com.web3.crawler.processors.Price1dProcessor;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.StablecoinApi;
import com.web3.framework.resouce.defillama.dto.AllStableCoinRes;
import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.resouce.defillama.dto.ProtocolRes;
import com.web3.framework.resouce.defillama.dto.StableCoinHistory;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import com.web3.service.defi.DefiService;
import com.web3.service.pos.EthereumV2Service;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: smy
 * @Date: 2023/1/9 5:08 PM
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    @Resource
    private Price1dProcessor price1dProcessor;

    @Resource
    private AddressService addressService;

    @Resource
    private BalanceService balanceService;

    @Resource
    private DefillamaApi defillamaApi;

    @Resource
    private StablecoinApi stablecoinApi;

    @Resource
    private DefiService defiService;

    @Value(value = "${field}")
    @Resource
    private EthereumV2Job ethereumV2Job;

    @NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private Integer useLocalCache;

    @Resource
    private EthereumV2Service ethereumV2Service;

    @GetMapping("/executePrice1dJob")
    public void executePrice1dJob() {
        Task task = new Task();
        task.setScheduleTime(LocalDateTime.now());
        price1dProcessor.execute(task);
    }

    @GetMapping("/executeFillBalance")
    public void executeFillBalance() {
        addressService.updateLatestBalanceBatch();
    }

    @GetMapping("/executeAddBalanceRecord")
    public void executeAddBalanceRecord(@RequestParam String start, @RequestParam String end) throws InterruptedException, ExecutionException, TimeoutException {

        balanceService.addBalanceRecord(LocalDateTime.parse(start), LocalDateTime.parse(end));
    }

    @GetMapping("/historyTvl")
    public HistoryTvlRes getHistoryTvl(@RequestParam String symbol) {
        return defillamaApi.getHistoryTvl(symbol);
    }

    @GetMapping("/protocols")
    public List<ProtocolRes> getProtocols() {
        return defillamaApi.getProtocols();
    }

    @PostMapping("/tvl/sync")
    public Boolean syncTvl(@RequestParam String protocol) throws InterruptedException {
        defiService.syncTvl(protocol);
        return true;
    }

    @PostMapping("/tvl/syncAllProtocol")
    public Boolean syncAllProtocol() {
        defiService.syncAllProtocolTvl();
        return true;
    }

    @GetMapping("/allStablecoin")
    public AllStableCoinRes getAllStablecoin() {
        return stablecoinApi.getAllStablecoin();
    }

    @GetMapping("/stablecoinHistory")
    public List<StableCoinHistory> getAllStablecoin(@RequestParam Integer stablecoin) {
        return stablecoinApi.getStablecoinHistory(stablecoin);
    }

    @GetMapping("/get")
    public Integer get() {
        return useLocalCache;
    }

    @GetMapping("/ethereumV2Job")
    public void ethereumV2Job() throws InterruptedException, NoSuchMethodException {
        ethereumV2Job.execute();
    }

}
