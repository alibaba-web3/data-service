package com.web3.service.defi.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.service.Tvl1dMapperService;
import com.web3.framework.resouce.coinmarketcap.CoinMarketCapService;
import com.web3.framework.resouce.coinmarketcap.dto.CmcInfo;
import com.web3.framework.resouce.defillama.DatasetsApi;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.resouce.defillama.dto.ProtocolRes;
import com.web3.framework.utils.DateUtils;
import com.web3.service.defi.DefiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/6
 */
@Service
@Slf4j
public class DefiServiceImpl implements DefiService {

    @Resource
    private Tvl1dMapperService tvl1dMapperService;

    @Resource
    private DefillamaApi defillamaApi;

    @Resource
    private DatasetsApi datasetsApi;

    @Resource
    private CoinMarketCapService coinMarketCapService;

    @Value("${sync.tvl.list}")
    private List<String> tvlSyncSymbolList;

    @Override
    public void syncTvl(String protocol) throws InterruptedException {
        HistoryTvlRes historyTvlRes;
        try {
            historyTvlRes = getHistoryTvl(protocol);
        } catch (Exception e) {
            log.error("get {} tvl error and retry", protocol);
            Thread.sleep(3 * 1000);
            historyTvlRes = getHistoryTvl(protocol);
        }

        if (historyTvlRes == null || CollectionUtils.isEmpty(historyTvlRes.getTvl())) {
            return;
        }

        Tvl1d latestTvl = tvl1dMapperService.getLatestTvl(protocol);

        //ObjectMapper mapper = new ObjectMapper();
        //
        //HistoryTvlRes historyTvlRes = mapper.readValue(new File("/Users/thomsonyang/Desktop/code/opensource/data-service/service/src/main/java/com/web3/service/tvl/impl/crv.json"),
        //    HistoryTvlRes.class);

        String protocolName = historyTvlRes.getName();
        String symbol = historyTvlRes.getSymbol();
        List<Tvl1d> tvl1dList = historyTvlRes.getTvl().stream().map(tvl -> {

                Tvl1d tvl1d = new Tvl1d();
                tvl1d.setTvl(BigDecimal.valueOf(tvl.getTotalLiquidityUSD()));
                tvl1d.setDate(DateUtils.ofEpochSecond(tvl.getDate()));
                tvl1d.setName(protocolName);
                tvl1d.setSymbol(symbol);

                return tvl1d;
            })
            // 最新一天会有两条数据
            .filter(tvl1d -> {
                boolean isNotDuplicate = tvl1d.getDate().isBefore(LocalDateTime.now().minusDays(1));
                if (latestTvl != null) {
                    return isNotDuplicate && tvl1d.getDate().isAfter(latestTvl.getDate());
                } else {
                    return isNotDuplicate;
                }
            })
            .toList();

        if (!CollectionUtils.isEmpty(tvl1dList)) {
            log.info("sync {} tvl data, {}", protocol, tvl1dList.size());
            tvl1dMapperService.replaceIntoBatch(tvl1dList);
        }

    }

    @Override
    public void syncAllProtocolTvl() {
        tvlSyncSymbolList.forEach(protocol -> {
            try {
                syncTvl(protocol);
            } catch (Exception e) {
                log.error("{} tvl sync error", protocol, e);
            }
        });
    }

    public List<ProtocolRes> getTopProtocol(Integer topNumber) {
        List<ProtocolRes> protocolList = defillamaApi.getProtocols();
        protocolList.sort((a, b) -> {
            if (a.getTvl().equals(b.getTvl())) {
                return 0;
            }
            return b.getTvl() - a.getTvl() > 0 ? 1 : -1;
        });

        return protocolList.subList(0, topNumber);
    }

    @Override
    public HistoryTvlRes getHistoryTvl(String protocol) {

        HistoryTvlRes res = switch (protocol) {
            case "curve" -> datasetsApi.getCurveTvlHistory();
            case "uniswap" -> datasetsApi.getUniswapTvlHistory();
            default -> defillamaApi.getHistoryTvl(protocol);
        };

        // defillama 部分数据缺少 symbol
        if (!StringUtils.hasText(res.getSymbol())) {
            CmcInfo cmcInfo = coinMarketCapService.getInfoById(res.getCmcId());
            // TODO defillama 关联的 cmcID 是错误的
            if (Objects.equals(res.getId(), "PancakeSwap")) {
                res.setSymbol("CAKE");
            } else {
                res.setSymbol(cmcInfo.getSymbol());
            }

        }

        return res;
    }
}
