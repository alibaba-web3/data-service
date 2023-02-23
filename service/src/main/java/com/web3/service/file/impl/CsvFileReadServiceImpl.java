//package com.web3.service.file.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//import com.web3.dal.data.entity.EthereumTraces;
//import com.web3.dal.data.mapper.EthereumTracesMapper;
//import com.web3.service.file.FileReadService;
//import com.web3.service.file.dto.EthereumTrace;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
///**
// * @Author fuxian
// * @Date 2023/2/22
// */
//@Slf4j
//@Service
//public class CsvFileReadServiceImpl implements FileReadService<EthereumTrace> {
//
//    @Resource
//    private EthereumTracesMapper ethereumTracesMapper;
//
//    @Override
//    public List<EthereumTrace> read(String filePath) {
//        if (StringUtils.isBlank(filePath)) {
//            return null;
//        }
//        List<EthereumTrace> traces = new ArrayList<>();
//
//        File csv = new File(filePath);
//        csv.setWritable(true);
//        csv.setReadable(true);
//
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8);
//            bufferedReader = new BufferedReader(inputStreamReader);
//        } catch (Exception e) {
//            log.error("CsvFileReadServiceImpl#error: {}", e.getMessage(), e);
//        }
//
//        String line = "";
//        try {
//            while ((line = bufferedReader.readLine()) != null) {
//                EthereumTrace trace = new EthereumTrace();
//                String[] split = line.split(",");
//                if (split.length <= 2) {
//                    continue;
//                }
//
//
//                List<List<String>> list = new ArrayList<>();
//                int index = 0;
//
////                String s1 = split[1];
////                String[] split2 = s1.split(";");
//
////                for (int i = 1; i < split.length; i++) {
////                    if (!split[i].contains(";")) {
////                        List<String> stringList = new ArrayList<>();
////                        for (int j = index; j < split.length; j++) {
////                            stringList.add(split[j]);
////                        }
////                    } else {
////                        index = i;
////                    }
////
////
////                }
//                String s = split[1];
//                String[] split1 = s.split(";");
//                trace.setAddress(split1[0].replaceAll("\"", ""));
//                trace.setBlockNumber(split1[1]);
//                trace.setBlockTime(split1[2]);
//                trace.setCallType(split1[3]);
//                trace.setError(split1[4]);
//                trace.setFrom(split1[5]);
//                trace.setGas(split1[6]);
//                trace.setGasUsed(split1[7]);
//                trace.setInput(split1[8]);
//                trace.setOutput(split1[9]);
//                trace.setRefundAddress(split1[10]);
//                trace.setSubTraces(split1[11]);
//                trace.setSuccess(split1[12]);
//                trace.setTo(split1[13]);
//                trace.setTraceAddress(split1[14]);
//                trace.setTxHash(split1[15]);
//                trace.setTxIndex(split1[16]);
//                trace.setTxSuccess(split1[17]);
//                trace.setType(split1[18]);
//                trace.setValue(split1[19].replaceAll("\"", ""));
//                traces.add(trace);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return traces;
//    }
//
//    @Override
//    public void batchWrite(List<EthereumTrace> traces) {
//        try {
//            if (!CollectionUtils.isEmpty(traces)) {
//                List<EthereumTraces> list = new ArrayList<>(traces.size());
//
//                traces.forEach(item -> {
//                    EthereumTraces record = new EthereumTraces();
//                    record.setTraceAddress(item.getTraceAddress());
//                    record.setFrom(item.getFrom());
//                    record.setError(item.getError());
//                    record.setInput(item.getInput());
//                    record.setOutput(item.getOutput());
//                    record.setTo(item.getTo());
//                    record.setType(item.getType());
//                    record.setValue(Long.parseLong(item.getValue()));
//                    record.setBlockNumber(Integer.parseInt(item.getBlockNumber()));
//                    record.setBlockTimestamp(LocalDateTime.parse(item.getBlockTime()));
//                    record.setGasUsed(Integer.parseInt(item.getGasUsed()));
//                    record.setGasLimit(Integer.parseInt(item.getGas()));
//                    record.setTraceChildrenCount(JSON.parseArray(item.getSubTraces()).size());
//                    record.setTraceSuccess(2 != Byte.parseByte(item.getSuccess()) ? Byte.parseByte(item.getSuccess()) : null);
//                    record.setTransactionSuccess(2 != Byte.parseByte(item.getTxSuccess()) ? Byte.parseByte(item.getTxSuccess()) : null);
//                    record.setTransactionIndex(Integer.parseInt(item.getTxIndex()));
//                    record.setTransactionHash(item.getTxHash());
//                    list.add(record);
//                });
//                log.info(JSON.toJSONString(list));
//                ethereumTracesMapper.batchInsertOrUpdate(list);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
