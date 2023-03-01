package com.web3.framework.resouce;

import java.time.Duration;

import com.web3.framework.resouce.binance.BinanceApi;
import com.web3.framework.resouce.defillama.DatasetsApi;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.StablecoinApi;
import com.web3.framework.resouce.glassnode.GlassNodeApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@Configuration
@Slf4j
public class ApiConfiguration {

    @Value("${binance.api.url}")
    private String binanceApiUrl;

    @Value("${defillama.api.url}")
    private String defillamaApiUrl;

    @Value("${defillama.stablecoins.url}")
    private String stablecoinApiUrl;

    @Value("${defillama.dataset.url}")
    private String datasetApiUrl;

    @Value("${glassnode.api.url}")
    private String glassNodeApiUrl;

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
        final ExchangeStrategies strategies = getDefillamaStrategies();
        //// 重定向处理
        //HttpClient httpClient = HttpClient.create().followRedirect(true, (clientRequest) -> {
        //    clientRequest.resourceUrl();
        //    log.info(clientRequest.uri());
        //});

        //ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);

        WebClient client = WebClient
            .builder()
            .baseUrl(defillamaApiUrl)
            .exchangeStrategies(strategies)
            //.clientConnector(clientHttpConnector)
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(client))
            .blockTimeout(Duration.ofSeconds(60))
            .build();

        return factory.createClient(DefillamaApi.class);
    }

    @Bean
    DatasetsApi datasetsApi() {
        final ExchangeStrategies strategies = getDefillamaStrategies();

        WebClient client = WebClient
            .builder()
            .baseUrl(datasetApiUrl)
            .exchangeStrategies(strategies)
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(client))
            .blockTimeout(Duration.ofSeconds(60))
            .build();

        return factory.createClient(DatasetsApi.class);
    }

    @Bean
    StablecoinApi stablecoinApi() {

        final ExchangeStrategies strategies = getDefillamaStrategies();

        WebClient client = WebClient.builder().baseUrl(stablecoinApiUrl).exchangeStrategies(strategies).build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(client))
            .blockTimeout(Duration.ofSeconds(30))
            .build();

        return factory.createClient(StablecoinApi.class);
    }

    @Bean
    GlassNodeApi glassNodeApi() {
        WebClient webClient = WebClient.builder().baseUrl(glassNodeApiUrl).build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient))
            .blockTimeout(Duration.ofSeconds(30))
            .build();

        return factory.createClient(GlassNodeApi.class);
    }

    ExchangeStrategies getDefillamaStrategies() {
        final int size = 16 * 1024 * 1024;
        return ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
    }

}
