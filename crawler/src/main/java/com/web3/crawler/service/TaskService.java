package com.web3.crawler.service;

import com.alibaba.fastjson2.JSON;
import com.web3.crawler.dto.Task;
import com.web3.dal.meta.entity.CrawlerTask;
import com.web3.dal.meta.service.CrawlerTaskMapperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: smy
 * @Date: 2023/1/13 5:08 PM
 */
@Service
public class TaskService {

    @Resource
    private CrawlerTaskMapperService crawlerTaskMapperService;

    public void record(Task task) {
        CrawlerTask crawlerTask;
        if (task.getId() == null) {
            crawlerTask = new CrawlerTask();
        } else {
            crawlerTask = crawlerTaskMapperService.getById(task.getId());
        }
        crawlerTask.setStatus(task.getStatus().name());
        crawlerTask.setProcessor(task.getProcessor());
        crawlerTask.setScheduleTime(task.getScheduleTime());
        crawlerTask.setId(task.getId());
        crawlerTask.setTaskInfo(JSON.toJSONString(task.getExtraInfo()));
        if (task.getId() == null) {
            crawlerTaskMapperService.save(crawlerTask);
            task.setId(crawlerTask.getId());
        } else {
            crawlerTaskMapperService.updateById(crawlerTask);
        }
    }

}
