package com.web3.framework.resouce.odps.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TunnelException;
import com.opencsv.CSVWriter;
import com.web3.framework.resouce.odps.OdpsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/9
 */
@Service
@Slf4j
public class OdpsServiceImpl implements OdpsService {

    private Odps odps;

    @Value("${odps.endpoint.api}")
    private String odpsApiEndpoint;

    @Value("${odps.endpoint.tunnel}")
    private String odpsTunnelEndpoint;

    @Value("${odps.access.id}")
    private String odpsAccessId;

    @Value("${odps.access.key}")
    private String odpsAccessKey;

    @Value("${odps.default.project}")
    private String odpsDefaultProject;

    @PostConstruct
    void init() {
        Account account = new AliyunAccount(odpsAccessId, odpsAccessKey);
        odps = new Odps(account);
        odps.setEndpoint(odpsApiEndpoint);
        odps.setDefaultProject(odpsDefaultProject);
    }

    @Override
    public List<Table> listTable() {
        List<Table> list = new ArrayList<>();
        for (Table table : odps.tables()) {
            list.add(table);
        }
        return list;
    }

    @Override
    public List<Table> tableByName(String tableName) {
        return StringUtils.isBlank(tableName) ?
                StreamSupport.stream(odps.tables().spliterator(), false).toList() :
                StreamSupport.stream(odps.tables().spliterator(), false)
                        .filter(table -> tableName.equals(table.getName())).toList();
    }

    @Override
    public List<Table> tableByProject(String projectName) {
        if (StringUtils.isNotBlank(projectName)) {
            odps.setDefaultProject(projectName);
        }

        return StreamSupport.stream(odps.tables().spliterator(), false).toList();
    }

    @Override
    public List<Record> getTable(String tableName) throws TunnelException, IOException {
        TableTunnel tunnel = new TableTunnel(odps);
        tunnel.setEndpoint(odpsTunnelEndpoint);

        try {
            DownloadSession downloadSession = tunnel.createDownloadSession(odpsDefaultProject, tableName);
            long count = downloadSession.getRecordCount();
            RecordReader recordReader = downloadSession.openRecordReader(0, count);
            List<Record> records = new ArrayList<>();
            Record record;

            while ((record = recordReader.read()) != null) {
                records.add(record);
            }

            recordReader.close();
            return records;
        } catch (TunnelException | IOException e) {
            log.error("get odps table data error: ", e);
            throw e;
        }
    }


    @Override
    public void downloadTable2Csv(String project, String tableName, CSVWriter writer) throws TunnelException, IOException {
        TableTunnel tunnel = new TableTunnel(odps);
        tunnel.setEndpoint(odpsTunnelEndpoint);

        try {
            DownloadSession downloadSession = tunnel.createDownloadSession(project, tableName);
            long count = downloadSession.getRecordCount();
            if (count > 1000 * 10000) {
                throw new RuntimeException("table is too large. rows limit is 1000w.");
            }
            writeHeader(downloadSession.getSchema(), writer);
            int start = 0, limit = 50000;
            while (start < count) {
                RecordReader recordReader = downloadSession.openRecordReader(start, limit);
                Record record;
                while ((record = recordReader.read()) != null) {
                    consumeRecord(record, downloadSession.getSchema(), writer);
                }
                recordReader.close();
                start += limit;
            }

        } catch (TunnelException | IOException e) {
            log.error("get odps table data error: ", e);
            throw e;
        }
    }

    private void writeHeader(TableSchema schema, CSVWriter writer) {
        String[] header = new String[schema.getColumns().size()];
        for (int i = 0; i < schema.getColumns().size(); i++) {
            header[i] = schema.getColumn(i).getName();
        }
        writer.writeNext(header);
    }

    private void consumeRecord(Record record, TableSchema schema, CSVWriter writer) {
        String[] line = new String[schema.getColumns().size()];
        for (int i = 0; i < schema.getColumns().size(); i++) {
            Column column = schema.getColumn(i);
            String colValue;
            switch (column.getType()) {
                case DECIMAL: {
                    BigDecimal v = record.getDecimal(i);
                    colValue = v == null ? null : v.toString();
                    break;
                }
                case BIGINT:
                case INT: {
                    Long v = record.getBigint(i);
                    colValue = v == null ? null : v.toString();
                    break;
                }
                case BOOLEAN: {
                    Boolean v = record.getBoolean(i);
                    colValue = v == null ? null : v.toString();
                    break;
                }
                case DATETIME: {
                    Date v = record.getDatetime(i);
                    colValue = v == null ? null : String.valueOf(v.getTime());
                    break;
                }
                case DOUBLE: {
                    Double v = record.getDouble(i);
                    colValue = v == null ? null : new BigDecimal(v).toString();
                    break;
                }
                case STRING: {
                    colValue = record.getString(i);
                    break;
                }
                default:
                    colValue = record.getString(i);
            }
            line[i] = colValue;
        }
        writer.writeNext(line);
    }

}
