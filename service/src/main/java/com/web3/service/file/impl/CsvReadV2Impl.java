package com.web3.service.file.impl;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import com.web3.dal.data.entity.EthereumTraces;
import com.web3.dal.data.service.EthereumTracesMapperService;
import com.web3.framework.utils.DateUtils;
import com.web3.service.file.FileReadService;
import com.web3.service.file.dto.EthereumTrace;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/22
 */
@Slf4j
@Service
public class CsvReadV2Impl implements FileReadService<EthereumTrace> {

    @Resource
    private EthereumTracesMapperService ethTracesMapperService;

    @Override
    public List<EthereumTrace> read(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        EthereumTrace trace = null;
        CsvReader r = null;

        List<EthereumTrace> traces = new ArrayList<>();
        try {
            r = new CsvReader(filePath, ';', StandardCharsets.UTF_8);
            r.readHeaders();
            r.setSafetySwitch(false);

            //逐条读取记录，直至读完
            while (r.readRecord()) {
                trace = new EthereumTrace();
                String[] split = r.getRawRecord().split(";");
                String[] split1 = split[0].split("\\|");

                trace.setAddress(split1[0].substring(10));
                trace.setBlockNumber(split1[1].replaceAll("\"", ""));
                trace.setBlockTime(split1[2]);
                trace.setCallType(split1[3]);
                trace.setError(split1[4]);
                trace.setFrom(split1[5]);
                trace.setGas(split1[6]);
                trace.setGasUsed(split1[7]);
                trace.setInput(split1[8]);
                trace.setOutput(split1[9]);
                trace.setRefundAddress(split1[10]);
                trace.setSubTraces(split1[11]);
                trace.setSuccess(split1[12]);
                trace.setTo(split1[13]);
                trace.setTraceAddress(split1[14]);
                trace.setTxHash(split1[15]);
                trace.setTxIndex(split1[16]);
                trace.setTxSuccess(split1[17]);
                trace.setType(split1[18]);
                trace.setValue(split1[19].replaceAll("\"", ""));
                traces.add(trace);

                for (int i = 1; i < split.length; i++) {
                    trace = new EthereumTrace();
                    String[] strings1 = split[i].split("\\|");
                    trace.setAddress(strings1[0].replaceAll("\"", ""));
                    trace.setBlockNumber(strings1[1].replaceAll("\"", ""));
                    trace.setBlockTime(strings1[2]);
                    trace.setCallType(strings1[3]);
                    trace.setError(strings1[4]);
                    trace.setFrom(strings1[5]);
                    trace.setGas(strings1[6]);
                    trace.setGasUsed(strings1[7]);
                    trace.setInput(strings1[8]);
                    trace.setOutput(strings1[9]);
                    trace.setRefundAddress(strings1[10]);
                    trace.setSubTraces(strings1[11]);
                    trace.setSuccess(strings1[12]);
                    trace.setTo(strings1[13]);
                    trace.setTraceAddress(strings1[14]);
                    trace.setTxHash(strings1[15]);
                    trace.setTxIndex(strings1[16]);
                    trace.setTxSuccess(strings1[17]);
                    trace.setType(strings1[18]);
                    trace.setValue(strings1[19].replaceAll("\"", ""));
                    traces.add(trace);
                }
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return traces;
    }

    @Override
    public void batchWrite(List<EthereumTrace> traces) {
        // 分批, 每批100000条
        List<List<EthereumTrace>> partition = Lists.partition(traces, 100000);
        try {
            if (!CollectionUtils.isEmpty(partition)) {
                partition.forEach(this::batchWriteContents);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void batchWriteContents(List<EthereumTrace> ethereumTraces) {
        List<EthereumTraces> list = new ArrayList<>(ethereumTraces.size());
        try {
            int i = 0;
            for (EthereumTrace item : ethereumTraces) {
                try {
                    EthereumTraces record = new EthereumTraces();
                    record.setTraceAddress(item.getTraceAddress());
                    record.setFrom(item.getFrom());
                    record.setError(item.getError());
                    record.setInput(item.getInput());
                    record.setOutput(item.getOutput());
                    record.setTo(item.getTo());
                    record.setType(item.getType());
                    // 修改类型
                    record.setValue("null".equals(item.getValue()) ? null : new BigDecimal(item.getValue()));
                    record.setBlockNumber("null".equals(item.getBlockNumber()) ? null : Integer.parseInt(item.getBlockNumber()));
                    record.setBlockTimestamp(DateUtils.parse(item.getBlockTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                    record.setGasUsed("null".equals(item.getGasUsed()) ? null : Integer.parseInt(item.getGasUsed()));
                    record.setGasLimit("null".equals(item.getGas()) ? null : Integer.parseInt(item.getGas()));
                    record.setTraceChildrenCount(JSON.parseArray(JSON.toJSONString(item.getTraceAddress()), String.class).size());
                    record.setTraceSuccess(Boolean.parseBoolean(item.getSuccess()));
                    record.setTransactionSuccess(Boolean.parseBoolean(item.getTxSuccess()));
                    record.setTransactionIndex("null".equals(item.getTxIndex()) ? null : Integer.parseInt(item.getTxIndex()));
                    record.setTransactionHash(item.getTxHash());
                    list.add(record);
                    i++;
                } catch (Exception e) {
                    log.info("item: {}", item);
                    throw new RuntimeException(e);
                }
            }
            ethTracesMapperService.batchInsertOrUpdateData(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
