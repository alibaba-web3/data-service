package com.web3.web.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.tunnel.TunnelException;
import com.opencsv.CSVWriter;
import com.web3.dal.data.entity.EthereumErc20UserDay;
import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.entity.ProtocolProfit;
import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.service.EthereumErc20UserDayMapperService;
import com.web3.dal.data.service.Price1dMapperService;
import com.web3.dal.data.service.ProtocolProfitMapperService;
import com.web3.dal.data.service.Tvl1dMapperService;
import com.web3.framework.utils.DateUtils;
import com.web3.framework.resouce.odps.OdpsService;
import com.web3.web.entity.ResultUtils;
import com.web3.web.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/23
 */
@Slf4j
@RestController
@RequestMapping("/api/download")
@Tag(name = "DownloadController", description = "下载相关接口")
public class DownloadController {

    @Resource
    private Price1dMapperService price1dMapperService;

    @Resource
    private Tvl1dMapperService tvl1dMapperService;

    @Resource
    private EthereumErc20UserDayMapperService ethereumErc20UserDayMapperService;

    @Resource
    private ProtocolProfitMapperService protocolProfitMapperService;

    @Resource
    private OdpsService odpsService;

    @GetMapping("/spot/1d")
    public void exportSpotPrice(HttpServletResponse response) {

        List<Price1d> price1dList = price1dMapperService.list();

        try {
            String[] header = new String[] {"date", "openTime", "closeTime", "symbol", "open", "height", "low", "close", "volume", "turnover", "tradingVolume", "buyingVolume", "buyingTurnover"};

            List<String[]> csv = price1dList.stream()
                .map(price1d -> new String[] {price1d.getDate(), String.valueOf(price1d.getOpenTime()), String.valueOf(price1d.getCloseTime()),
                    price1d.getSymbol(), String.valueOf(price1d.getOpen()), String.valueOf(price1d.getHigh()), String.valueOf(price1d.getLow()),
                    String.valueOf(price1d.getClose()), String.valueOf(price1d.getVolume()), String.valueOf(price1d.getTurnover()), String.valueOf(price1d.getTradingVolume()),
                    String.valueOf(price1d.getBuyingVolume()),
                    String.valueOf(price1d.getBuyingTurnover())})
                .toList();

            exportCsv("spot_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download spot price 1d error", e);
        }
    }

    @GetMapping("/profit/1d")
    public void exportProfit1d(HttpServletResponse response) {

        List<ProtocolProfit> list = protocolProfitMapperService.list();

        try {
            String[] header = new String[] {"protocol", "category", "symbol", "protocol_revenue", "total_fees", "date"};

            List<String[]> csv = list.stream()
                    .map(item -> new String[] {String.valueOf(item.getProtocol()), String.valueOf(item.getCategory()), String.valueOf(item.getSymbol()),
                    String.valueOf(item.getProtocolRevenue()), String.valueOf(item.getTotalFees()), DateUtils.format(item.getDate(), DateUtils.YYYY_MM_DD_HH_MM_SS)})
                    .toList();

            exportCsv("profit_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download profit 1d error", e);
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

        List<Record> records = odpsService.getTable("ethereum_erc20_call_download");

        try {
            String[] header = new String[] {"time", "symbol", "contract_address", "transfer_count", "approval_count"};

            List<String[]> csv = records.stream()
                .map(record -> new String[] {
                    Objects.toString(record.get("time"), ""),
                    Objects.toString(record.getString("symbol"), ""),
                    Objects.toString(record.getString("contract_address"), ""),
                    Objects.toString(record.get("transfer_count"), ""),
                    Objects.toString(record.get("approval_count"), ""),
                })
                .toList();

            exportCsv("erc20_user_1d", header, csv, response);
        } catch (Exception e) {
            log.error("download erc20_user_1d error", e);
        }
    }

    @GetMapping("/{tableName}")
    @Operation(summary = "下载指定 MaxCompute 表的文件", description = "返回 Category List")
    @ApiResponse(responseCode = "200", description = "MaxCompute 表数据")
    public ResponseEntity<StreamingResponseBody> export(@PathVariable String tableName, HttpServletResponse response) {
        StreamingResponseBody responseBody = outputStream -> {
            try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream), ',')) {
                odpsService.downloadTable2Csv(tableName, writer);
            } catch (IOException | TunnelException e) {
                throw new RuntimeException(e);
            }
        };
        response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tableName + ".csv");
        return ResponseEntity.ok()
                .body(responseBody);
    }

    @GetMapping("/table/list")
    @Operation(summary = "MaxCompute 数据列表", description = "返回 MaxCompute 表名列表 List")
    @ApiResponse(responseCode = "200", description = "MaxCompute 表数据")
    public Result<List<String>> tableList() {
        return ResultUtils.createSuccessRes(new ArrayList<>());
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
