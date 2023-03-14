package com.web3.framework.resouce.dingtalk.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

    @Value("${dingtalk.robot.secret}")
    private String dingtalkRobotSecret;

    @Override
    public void send(String message) {

        Long timestamp = System.currentTimeMillis();
        String sign = getSign(timestamp, dingtalkRobotSecret);
        String url = String.format("%s?access_token=%s&sign=%s&timestamp=%s", dingtalkRobotUrl, dingtalkRobotToken, sign, timestamp);

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

    String getSign(Long timestamp, String secret) {

        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("sign dingtalk error", e);
            return null;
        }
    }
}
