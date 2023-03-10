package com.web3.framework.resouce.odps;

import java.util.List;

import com.aliyun.odps.Table;

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
     * 查询表所有的数据
     *
     * @param table 表名
     * @return 表数据
     */
    Object getTable(String table);

}
