package com.web3.service.file.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.web3.service.file.FileService;
import com.web3.service.file.dto.TraceCsvDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/22
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public CSVReader readCsv(String path) {
        try {
            return new CSVReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new Error();
        }
    }

    @Override
    public List<String[]> readAllCsv(String path) throws IOException {
        try (CSVReader reader = readCsv(path)) {
            return reader.readAll();
        }
    }

    @Override
    public List<TraceCsvDTO> readTraceCsv() {
        String csvFile = "/Users/thomsonyang/Desktop/code/opensource/data-service/service/src/main/java/com/web3/service/address/trace.csv";

        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            List<String[]> allElements = reader.readAll();
            allElements.remove(0);

            return allElements.stream().map(element -> {
                TraceCsvDTO result = new TraceCsvDTO();

                String fromListStr = element[3];
                List<String> fromList = Arrays.stream(fromListStr.split(",")).toList();
                result.setFromList(fromList);

                String toListStr = element[4];
                List<String> toList = Arrays.stream(toListStr.split(",")).toList();
                result.setToList(toList);

                result.setDate(element[0]);

                result.setBlockTime(ZonedDateTime.parse(element[1]).toLocalDateTime());
                result.setBlockNumber(Integer.parseInt(element[2]));

                return result;
            }).toList();

        } catch (Exception e) {
            log.error("parse csv error", e);
            return null;
        }
    }
}
