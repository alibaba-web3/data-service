package com.web3.framework.crawler.jobs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.service.Price1dMapperService;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.binance.dto.KLineDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: smy
 * @Date: 2023/1/9 4:10 PM
 */
@Service
public class Price1dJob {


    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Resource
    private Price1dMapperService price1DMapperService;
    @Resource
    private BinanceService binanceService;

    private List<Price1d> addId(List<Price1d> price1dList) {
        QueryWrapper<Price1d> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>(2);
        map.put("symbol", "ETHUSDT");
        map.put("source", "Binance");
        wrapper.allEq(map).in("date", price1dList.stream().map(Price1d::getDate).collect(Collectors.toList()));
        List<Price1d> exsitsList = price1DMapperService.list(wrapper);
        if (CollectionUtils.isEmpty(exsitsList)) {
            price1DMapperService.saveOrUpdateBatch(price1dList);
            return price1dList;
        }
        return price1dList.stream().map(price1d -> {
            int index = exsitsList.indexOf(price1d);
            if (index != -1) {
                price1d.setId(exsitsList.get(index).getId());
            }
            return price1d;
        }).collect(Collectors.toList());
    }

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedDelay = 1000 * 60)
    public void execute() {
        List<KLineDTO> list = binanceService.getKLines("ETHUSDT", "1d", null, System.currentTimeMillis(), 5);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<Price1d> price1dList = new ArrayList<>(list.size());
        list.forEach(dto -> {
            Price1d price1d = new Price1d();
            BeanUtils.copyProperties(dto, price1d);
            price1d.setSymbol("ETHUSDT");
            price1d.setSource("Binance");
            price1d.setDate(price1d.getOpenTime().format(dtf));
            price1dList.add(price1d);
        });
        price1DMapperService.saveOrUpdateBatch(addId(price1dList));
        System.out.println(price1dList);
    }


}
