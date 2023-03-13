package com.web3.framework.resouce.dingtalk;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/13
 */
public interface DingtalkService {

    /**
     * 发送简单消息
     *
     * @param message 消息内容
     */
    void send(String message);

}
