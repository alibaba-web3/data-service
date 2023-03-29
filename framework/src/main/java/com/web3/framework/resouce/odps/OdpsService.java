package com.web3.framework.resouce.odps;

import java.io.IOException;
import java.util.List;

import com.aliyun.odps.Table;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.tunnel.TunnelException;
import com.opencsv.CSVWriter;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/9
 */
public interface OdpsService {

    /**
     * 查询 odps 表
     *
     * @return 所有表信息
     */
    List<Table> listTable();

    /**
     * 查询指定的 odps 表
     *
     * @return 指定 ODPS 表信息
     */
    List<Table> tableByName(String tableName);

    /**
     * 查询表所有的数据
     *
     * @param tableName 表名
     * @return 表数据
     */
    List<Record> getTable(String tableName) throws TunnelException, IOException;

    /**
     * 下载表数据到csv
     *
     * @param tableName
     * @param writer
     * @throws TunnelException
     * @throws IOException
     */
    void downloadTable2Csv(String tableName, CSVWriter writer) throws TunnelException, IOException;

}
