package com.web3.web.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.alibaba.nacos.api.config.annotation.NacosValue;

import com.aliyun.odps.Table;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.tunnel.TunnelException;
import com.web3.crawler.constants.TaskType;
import com.web3.crawler.dto.Task;
import com.web3.crawler.jobs.EthereumV2Job;
import com.web3.crawler.processors.Price1dProcessor;
import com.web3.dal.data.service.Price1dMapperService;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.coinmarketcap.CoinMarketCapApi;
import com.web3.framework.resouce.defillama.DatasetsApi;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.StablecoinApi;
import com.web3.framework.resouce.defillama.dto.AllStableCoinRes;
import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.resouce.defillama.dto.ProtocolRes;
import com.web3.framework.resouce.defillama.dto.StableCoinHistory;
import com.web3.framework.resouce.dingtalk.DingtalkService;
import com.web3.framework.resouce.odps.OdpsService;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import com.web3.service.defi.DefiService;
import com.web3.service.file.FileReadService;
import com.web3.service.file.impl.CsvReadImpl;
import com.web3.service.tag.AddressTagService;
import com.web3.service.tag.TagService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private BinanceService binanceService;

    @Resource
    private StablecoinApi stablecoinApi;

    @Resource
    private CoinMarketCapApi coinMarketCapApi;

    @Resource
    private DefiService defiService;

    @Resource
    private DatasetsApi datasetsApi;

    @Resource
    private Price1dMapperService price1dMapperService;

    @Resource
    private EthereumV2Job ethereumV2Job;

    @Resource
    private OdpsService odpsService;

    @Resource
    private DingtalkService dingtalkService;

    @NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private Integer useLocalCache;

    @Resource
    private TagService tagService;

    @Resource
    private AddressTagService addressTagService;

    @Resource(type = CsvReadImpl.class)
    private FileReadService<?> fileReadService;

    @GetMapping("/executePrice1dJob")
    public void executePrice1dJob() {
        Task task = Task.generate(price1dProcessor, LocalDateTime.now(), TaskType.Manual);
        price1dProcessor.execute(task);
    }

    @GetMapping("/fillAllKline")
    public void fillKline() {
        List<String> symbols = binanceService.getAllUsdtSymbol();
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            price1dProcessor.fillAllKline(symbol);
            log.info("{} {}/{}", symbol, i, symbols.size());
        }
    }

    @GetMapping("/fillHistoryRecord")
    public void fillHistoryRecord() {
        balanceService.fillHistoryRecord();
    }

    @GetMapping("/executeAddBalanceRecord")
    public void executeAddBalanceRecord(@RequestParam String start, @RequestParam String end) throws InterruptedException, ExecutionException, TimeoutException {

        balanceService.addBalanceRecord(LocalDateTime.parse(start), LocalDateTime.parse(end));
    }

    @GetMapping("/historyTvl")
    public HistoryTvlRes getHistoryTvl(@RequestParam String symbol) {
        if (Objects.equals(symbol, "curve")) {
            return datasetsApi.getCurveTvlHistory();
        } else if (Objects.equals(symbol, "uniswap")) {
            return datasetsApi.getUniswapTvlHistory();
        } else {
            return defillamaApi.getHistoryTvl(symbol);
        }

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

    @GetMapping("/trace")
    public Set<String> getTraceAddressList(@RequestParam String start) {
        return balanceService.getTraceAddressList(LocalDateTime.parse(start));
    }

    @GetMapping("/traceSync")
    public void csvTest(@RequestParam(value = "filePath") String filePath) {
        try {
            log.info("trace sync start...");
            long start = System.currentTimeMillis();
            List read = fileReadService.read(filePath);
            fileReadService.batchWrite(read);
            log.info("trace sync end, cost: {}", System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cmc/info")
    public Object getCryptoCurrencyInfo(@RequestParam String id) {
        return coinMarketCapApi.getCryptoCurrencyInfo(id, null, null, null, null, null);
    }

    @GetMapping("/defi/protocolProfit")
    public void syncProtocolProfit(@RequestParam(value = "dataType") String dataType) {
        defiService.syncProtocolProfit(dataType);
        //        defiService.syncProtocolProfit("dailyFees");
        //        defiService.syncProtocolProfit("dailyRevenue");
    }

    @GetMapping("/odps/tables")
    public List<String> getOdpsTables() {
        List<Table> result = odpsService.listTable();
        return result.stream().map(Table::getName).toList();
    }

    @GetMapping("/odps/table/data")
    public List<Record> getOdpsTable(@RequestParam String tableName) throws TunnelException, IOException {
        return odpsService.getTable(tableName);
    }

    @GetMapping("/dingtalk/message")
    public void sendDingtalkMessage(@RequestParam String message) {
        dingtalkService.send(message);
    }

    @PostMapping("/tag/ethersScan")
    public void importEtherScanTag(@RequestParam String path, @RequestParam String categoryId) {
        addressTagService.importEtherScanTags(path, categoryId);
    }

    @GetMapping("/tag/category")
    public List<String> listTagByCategory(@RequestParam String categoryId) {
        return addressTagService.listAddressByTagCategory(categoryId);
    }

}
