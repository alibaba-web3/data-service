package com.web3.service.file;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/22
 */
public interface FileReadService<T> {

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return
     */
    List<T> read(String filePath);

    /**
     * 批量写入
     *
     * @param content 内容
     */
    void batchWrite(List<T> content);

}
