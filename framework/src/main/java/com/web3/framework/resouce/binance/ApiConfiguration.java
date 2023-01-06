package com.web3.framework.resouce.binance;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@Configuration
public class ApiConfiguration {

    @Value("${binance.api.url}")
    private String apiUrl;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @Bean
    BinanceApi binanceApi() {
        WebClient client;

        if (proxyPort != null && StringUtils.isNotEmpty(proxyHost)) {
            // 本地访问 binance 需要代理
            HttpClient httpClient =
                HttpClient.create()
                    .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                        .host(proxyHost)
                        .port(proxyPort));

            ReactorClientHttpConnector conn = new ReactorClientHttpConnector(httpClient);

            client = WebClient.builder().baseUrl(apiUrl).clientConnector(conn).build();
        } else {
            client = WebClient.builder().baseUrl(apiUrl).build();
        }

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(BinanceApi.class);
    }

}
