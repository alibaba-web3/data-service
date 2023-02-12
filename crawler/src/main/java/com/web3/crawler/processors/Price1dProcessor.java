package com.web3.crawler.processors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.service.TaskService;
import com.web3.dal.data.entity.Price1d;
import com.web3.dal.data.service.Price1dMapperService;
import com.web3.crawler.dto.Task;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.binance.dto.KLineDTO;
import com.web3.framework.utils.DateUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: smy
 * @Date: 2023/1/12 4:02 PM
 */
@Component("price1dProcessor")
@Slf4j
@ProcessorConfig(name = "Price1dProcessor")
public class Price1dProcessor implements IProcessor {

    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Resource
    private Price1dMapperService price1DMapperService;
    @Resource
    private BinanceService binanceService;
    @Resource
    private TaskService taskService;

    @Override
    public void execute(Task task) {
        task.setStatus(TaskStatus.Running);
        taskService.record(task);
        try {
            log.info("start Price1dProcessor task {} ", task);
            _do(task);
            task.setStatus(TaskStatus.Success);
        } catch (Throwable e) {
            task.setStatus(TaskStatus.Failed);
            task.addExtraInfo("error_msg", e.getMessage());
            log.error("Price1dProcessor failed, task {}, {}", task, e);
        }
        taskService.record(task);
    }

    public void _do(Task task) {
        List<KLineDTO> list = binanceService.getKLines("ETHUSDT", "1d", null, DateUtils.convert2Timestamp(task.getScheduleTime()), 5);
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
    }

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
}
