package com.web3.framework.resouce.odps.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TunnelException;
import com.web3.framework.resouce.odps.OdpsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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

}
