package com.web3.service.file.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/22
 */
@Data
public class TraceCsvDTO {

    private String date;

    private LocalDateTime blockTime;

    private Integer blockNumber;

    private List<String> fromList;

    private List<String> toList;

}
