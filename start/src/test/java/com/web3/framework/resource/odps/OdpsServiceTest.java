package com.web3.framework.resource.odps;

import com.alibaba.fastjson.JSON;
import com.aliyun.odps.Table;
import com.web3.framework.resouce.odps.OdpsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/3/29
 */
@Slf4j
@SpringBootTest
public class OdpsServiceTest {

    @Resource
    private OdpsService odpsService;

    @Test
    public void tableByName() {
        List<Table> tables = odpsService.tableByName("");
        log.info("odps tables: {}", JSON.toJSONString(tables));
    }

}
