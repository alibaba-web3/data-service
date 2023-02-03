package com.web3.framework.resouce;

import java.time.Duration;

import com.web3.framework.resouce.binance.BinanceApi;
import com.web3.framework.resouce.defillama.DefillamaApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
    private String binanceApiUrl;

    @Value("${defillama.api.url}")
    private String defillamaApiUrl;

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

            client = WebClient.builder().baseUrl(binanceApiUrl).clientConnector(conn).build();
        } else {
            client = WebClient.builder().baseUrl(binanceApiUrl).build();
        }

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(BinanceApi.class);
    }

    @Bean
    DefillamaApi defillamaApi() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

        WebClient client = WebClient.builder().baseUrl(defillamaApiUrl).exchangeStrategies(strategies).build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(client))
            .blockTimeout(Duration.ofSeconds(20))
            .build();

        return factory.createClient(DefillamaApi.class);
    }

}
