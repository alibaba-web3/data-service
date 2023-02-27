package com.web3.service.file;

import java.util.List;

import com.opencsv.CSVReader;
import com.web3.service.file.dto.TraceCsvDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/22
 */
public interface FileService {

    /**
     * 读取 csv 文件
     *
     * @param path 文件路径
     * @return csv
     */
    CSVReader readCsv(String path);

    /**
     * 读取 trace 数据
     *
     * @return trace
     */
    List<TraceCsvDTO> readTraceCsv();

}
