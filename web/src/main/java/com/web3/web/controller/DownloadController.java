package com.web3.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.opencsv.CSVWriter;
import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.service.Price1dMapperService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/spot/1d")
    public void exportSpotPrice(HttpServletResponse response) {

        List<Price1d> price1dList = price1dMapperService.list();

        try {
            String[] header = new String[] {"date", "openTime", "closeTime", "symbol", "open", "height", "low", "close", "volume", "turnover", "tradingVolume", "buyingVolume", "buyingTurnover"};

            List<String[]> csv = price1dList.stream()
                .map(price1d -> new String[] {price1d.getDate(), price1d.getOpenTime().toString(), price1d.getCloseTime().toString(),
                    price1d.getSymbol(), price1d.getOpen().toString(), price1d.getHeight().toString(), price1d.getLow().toString(),
                    price1d.getClose().toString(), price1d.getVolume().toString(), price1d.getTurnover().toString(), price1d.getTradingVolume().toString(),
                    price1d.getBuyingVolume().toString(),
                    price1d.getBuyingTurnover().toString()})
                .toList();

            exportCsv(response,csv,header);
        } catch (Exception e) {
            log.error("download spot price 1d error", e);
        }

    }

    public void exportCsv(HttpServletResponse response, List<String[]> csv, String[] header) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");

        String fileName = "spot_1d";
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".csv");

        PrintWriter printWriter = response.getWriter();
        CSVWriter writer = new CSVWriter(printWriter, ',');

        writer.writeNext(header);
        writer.writeAll(csv);

        writer.flush();
        writer.close();
    }

}
