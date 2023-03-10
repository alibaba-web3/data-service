package com.web3.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.tunnel.TunnelException;
import com.opencsv.CSVWriter;
import com.web3.dal.data.entity.EthereumErc20UserDay;
import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.service.EthereumErc20UserDayMapperService;
import com.web3.dal.data.service.Price1dMapperService;
import com.web3.dal.data.service.Tvl1dMapperService;
import com.web3.framework.resouce.odps.OdpsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/23
 */
@RestController
@RequestMapping("/api/download")
@Slf4j
public class DownloadController {

    @Resource
    private Price1dMapperService price1dMapperService;

    @Resource
    private Tvl1dMapperService tvl1dMapperService;

    @Resource
    private EthereumErc20UserDayMapperService ethereumErc20UserDayMapperService;

    @Resource
    private OdpsService odpsService;

    @GetMapping("/spot/1d")
    public void exportSpotPrice(HttpServletResponse response) {

        List<Price1d> price1dList = price1dMapperService.list();

        try {
            String[] header = new String[] {"date", "openTime", "closeTime", "symbol", "open", "height", "low", "close", "volume", "turnover", "tradingVolume", "buyingVolume", "buyingTurnover"};

            List<String[]> csv = price1dList.stream()
                .map(price1d -> new String[] {price1d.getDate(), price1d.getOpenTime().toString(), price1d.getCloseTime().toString(),
                    price1d.getSymbol(), price1d.getOpen().toString(), price1d.getHigh().toString(), price1d.getLow().toString(),
                    price1d.getClose().toString(), price1d.getVolume().toString(), price1d.getTurnover().toString(), price1d.getTradingVolume().toString(),
                    price1d.getBuyingVolume().toString(),
                    price1d.getBuyingTurnover().toString()})
                .toList();

            exportCsv("spot_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download spot price 1d error", e);
        }
    }

    @GetMapping("/tvl/1d")
    public void exportTvl(HttpServletResponse response) {

        List<Tvl1d> tvl1dList = tvl1dMapperService.list();

        try {
            String[] header = new String[] {"date", "name", "symbol", "tvl"};

            List<String[]> csv = tvl1dList.stream()
                .map(price1d -> new String[] {price1d.getDate().toString(), price1d.getName(), price1d.getSymbol(), price1d.getTvl().toString()})
                .toList();

            exportCsv("tvl_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download tvl 1d error", e);
        }
    }

    @GetMapping("/erc20/user/1d")
    public void exportUser(HttpServletResponse response) throws TunnelException, IOException {

        List<Record> records = odpsService.getTable("ethereum_erc20_call");

        try {
            String[] header = new String[] {"time", "symbol", "contract_address", "transfer_count", "approval_count"};

            List<String[]> csv = records.stream()
                .map(record -> new String[] {
                    Objects.toString(record.get("time"), ""),
                    Objects.toString(record.get("symbol"), ""),
                    Objects.toString(record.get("contract_address"), ""),
                    Objects.toString(record.get("transfer_count"), ""),
                    Objects.toString(record.get("approval_count"), ""),
                })
                .toList();

            exportCsv("erc20_user_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download erc20_user_1d error", e);
        }
    }

    public void exportCsv(String fileName, String[] header, List<String[]> csv, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");

        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".csv");

        PrintWriter printWriter = response.getWriter();
        CSVWriter writer = new CSVWriter(printWriter, ',');

        writer.writeNext(header);
        writer.writeAll(csv);

        writer.flush();
        writer.close();
    }

}
