package com.web3.service.file.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author fuxian
 * @Date 2023/2/22
 */
@Data
public class EthereumTrace implements Serializable {
    private String address;
    private String blockNumber;
    private String blockTime;
    private String callType;
    private String error;
    private String from;
    private String gas;
    private String gasUsed;
    private String input;
    private String output;
    private String refundAddress;
    private String subTraces;
    private String success;
    private String to;
    private String traceAddress;
    private String txHash;
    private String txIndex;
    private String txSuccess;
    private String type;
    private String value;
}
