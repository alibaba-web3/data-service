package com.web3.service.tvl.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.service.Tvl1dMapperService;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.utils.DateUtils;
import com.web3.service.tvl.TvlService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/6
 */
@Service
@Slf4j
public class TvlServiceImpl implements TvlService {

    @Resource
    private Tvl1dMapperService tvl1dMapperService;

    @Resource
    private DefillamaApi defillamaApi;

    @Override
    public void sync(String protocol) {
        HistoryTvlRes historyTvlRes = defillamaApi.getHistoryTvl(protocol);

        if (historyTvlRes == null || CollectionUtils.isEmpty(historyTvlRes.getTvl())) {
            return;
        }

        Tvl1d latestTvl = tvl1dMapperService.getLatestTvl(protocol);

        //ObjectMapper mapper = new ObjectMapper();
        //
        //HistoryTvlRes historyTvlRes = mapper.readValue(new File("/Users/thomsonyang/Desktop/code/opensource/data-service/service/src/main/java/com/web3/service/tvl/impl/crv.json"),
        //    HistoryTvlRes.class);

        List<Tvl1d> tvl1dList = historyTvlRes.getTvl().stream().map(tvl -> {

                Tvl1d tvl1d = new Tvl1d();
                tvl1d.setTvl(BigDecimal.valueOf(tvl.getTotalLiquidityUSD()));
                tvl1d.setDate(DateUtils.ofEpochSecond(tvl.getDate()));
                tvl1d.setName(historyTvlRes.getName());

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
            log.info("sync {} tvl data", protocol);
            tvl1dMapperService.saveBatch(tvl1dList);
        }

    }
}
