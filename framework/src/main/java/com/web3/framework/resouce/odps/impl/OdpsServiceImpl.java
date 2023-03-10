package com.web3.framework.resouce.odps.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import com.aliyun.odps.Tables;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.tunnel.TableTunnel;
import com.web3.framework.resouce.odps.OdpsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/9
 */
@Service
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
    public Object getTable(String table) {
        return null;
    }

    //void download(){
    //    TableTunnel tunnel = new TableTunnel(odps);
    //    tunnel.setEndpoint(tunnelURL);
    //
    //
    //
    //    try {
    //        DownloadSession downloadSession = tunnel.createDownloadSession(project, table1);
    //        long count = downloadSession.getRecordCount();
    //        RecordReader recordReader = downloadSession.openRecordReader(0, count);
    //        Record record;
    //
    //        UploadSession uploadSession = tunnel.createUploadSession(project, table2);
    //        RecordWriter recordWriter = uploadSession.openRecordWriter(0);
    //
    //        while ((record = recordReader.read()) != null) {
    //            recordWriter.write(record);
    //        }
    //
    //        recordReader.close();
    //
    //        recordWriter.close();
    //        uploadSession.commit(new Long[]{0L});
    //    } catch (TunnelException e) {
    //        e.printStackTrace();
    //    } catch (IOException e1) {
    //        e1.printStackTrace();
    //    }
    //}

}
