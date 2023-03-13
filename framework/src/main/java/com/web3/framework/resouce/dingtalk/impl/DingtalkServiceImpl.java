package com.web3.framework.resouce.dingtalk.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import com.web3.framework.resouce.dingtalk.DingtalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/13
 */
@Service
@Slf4j
public class DingtalkServiceImpl implements DingtalkService {

    @Value("${dingtalk.robot.token}")
    private String dingtalkRobotToken;

    @Value("${dingtalk.robot.url}")
    private String dingtalkRobotUrl;

    @Override
    public void send(String message) {

        String url = dingtalkRobotUrl + "?access_token=" + dingtalkRobotToken;

        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(message);
        request.setText(text);

        try {
            client.execute(request);
        } catch (ApiException e) {
            log.error("send dingtalk message error: ", e);
        }

    }
}
