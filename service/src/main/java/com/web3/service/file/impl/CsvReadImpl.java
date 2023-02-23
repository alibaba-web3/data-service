package com.web3.service.file.impl;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/22
 */
@Slf4j
@Service
public class CsvReadImpl implements FileReadService<EthereumTrace> {

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
                String[] split1 = split[0].split(",");

                trace.setAddress(split1[1].replaceAll("\"", ""));
                trace.setBlockNumber(split1[2].replaceAll("\"", ""));
                trace.setBlockTime(split1[3]);
                trace.setCallType(split1[4]);
                trace.setError(split1[5]);
                trace.setFrom(split1[6]);
                trace.setGas(split1[7]);
                trace.setGasUsed(split1[8]);
                trace.setInput(split1[9]);
                trace.setOutput(split1[10]);
                trace.setRefundAddress(split1[11]);
                trace.setSubTraces(split1[12]);
                trace.setSuccess(split1[13]);
                trace.setTo(split1[14]);
                trace.setTraceAddress(split1[15]);
                trace.setTxHash(split1[16]);
                trace.setTxIndex(split1[17]);
                trace.setTxSuccess(split1[18]);
                trace.setType(split1[19]);
                trace.setValue(split1[20].replaceAll("\"", ""));
                traces.add(trace);

                for (int i = 1; i < split.length; i++) {
                    trace = new EthereumTrace();
                    String[] strings = split[i].split(",");
                    String[] strings1 = convertSplit(split[i]);
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
                    trace.setTxHash(strings1[14]);
                    trace.setTxIndex(strings1[15]);
                    trace.setTxSuccess(strings1[16]);
                    trace.setType(strings1[17]);
                    trace.setValue(strings1[18].replaceAll("\"", ""));

                    trace.setTraceAddress(strings1[19]);
                    traces.add(trace);
                }
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return traces;
    }

    private String[] convertSplit(String s) {
        String[] split = s.split("(\\[|\\])");
        String[] split1 = split[0].concat(split[2]).replaceAll(",,", ",").split(",");

        String[] newCol = new String[split1.length + 1];
        System.arraycopy(split1, 0, newCol, 0, split1.length);
        String concat = "[".concat(split[1]).concat("]");
        newCol[split1.length] = concat;
        return newCol;

    }

    @Override
    public void batchWrite(List<EthereumTrace> traces) {
        try {
            if (!CollectionUtils.isEmpty(traces)) {
                List<EthereumTraces> list = new ArrayList<>(traces.size());
                for (EthereumTrace item : traces) {

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
                        record.setValue("null".equals(item.getValue()) ? null : new BigInteger(item.getValue()));
                        record.setBlockNumber("null".equals(item.getBlockNumber()) ? null : Integer.parseInt(item.getBlockNumber()));
                        record.setBlockTimestamp(DateUtils.parse(item.getBlockTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                        record.setGasUsed("null".equals(item.getGasUsed()) ? null : Integer.parseInt(item.getGasUsed()));
                        record.setGasLimit("null".equals(item.getGas()) ? null : Integer.parseInt(item.getGas()));
                        record.setTraceChildrenCount(JSON.parseArray(JSON.toJSONString(item.getTraceAddress()), String.class).size());
                        record.setTraceSuccess(2 != Integer.parseInt(item.getSuccess()) ? Integer.parseInt(item.getSuccess()) : null);
                        record.setTransactionSuccess(2 != Integer.parseInt(item.getTxSuccess()) ? Integer.parseInt(item.getTxSuccess()) : null);
                        record.setTransactionIndex("null".equals(item.getTxIndex()) ? null : Integer.parseInt(item.getTxIndex()));
                        record.setTransactionHash(item.getTxHash());
                        list.add(record);
                    } catch (Exception e) {
                        log.info("item: {}", item);
                        throw new RuntimeException(e);
                    }
                }
                ethTracesMapperService.batchInsertOrUpdateData(list);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
