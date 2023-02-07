package com.web3.service.tvl.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web3.dal.data.entity.Tvl1d;
import com.web3.dal.data.service.Tvl1dMapperService;
import com.web3.framework.resouce.defillama.DefillamaApi;
import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.utils.DateUtils;
import com.web3.service.tvl.TvlService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/6
 */
@Service
public class TvlServiceImpl implements TvlService {

    @Resource
    private Tvl1dMapperService tvl1dMapperService;

    @Resource
    private DefillamaApi defillamaApi;

    @Override
    public void sync(String protocol) throws IOException {
        //HistoryTvlRes historyTvlRes = defillamaApi.getHistoryTvl(protocol);
        //
        //if (historyTvlRes == null || CollectionUtils.isEmpty(historyTvlRes.getTvl())) {
        //    return;
        //}

        ObjectMapper mapper = new ObjectMapper();

        //InputStream is = HistoryTvlRes.class.getResourceAsStream("/Users/thomsonyang/Desktop/code/opensource/data-service/service/src/main/java/com/web3/service/tvl/impl/crv.json");
        //HistoryTvlRes historyTvlRes = mapper.readValue(is, HistoryTvlRes.class);

        HistoryTvlRes historyTvlRes = mapper.readValue(new File("/Users/thomsonyang/Desktop/code/opensource/data-service/service/src/main/java/com/web3/service/tvl/impl/crv.json"),
            HistoryTvlRes.class);

        List<Tvl1d> tvl1dList = historyTvlRes.getTvl().stream().map(tvl -> {

                Tvl1d tvl1d = new Tvl1d();
                tvl1d.setTvl(BigDecimal.valueOf(tvl.getTotalLiquidityUSD()));
                tvl1d.setDate(DateUtils.ofEpochSecond(tvl.getDate()));
                tvl1d.setName(historyTvlRes.getName());

                return tvl1d;
            })
            // 最新一天会有两条数据
            .filter(tvl1d -> tvl1d.getDate().isBefore(LocalDateTime.now().minusDays(1)))
            .toList();
        //
        tvl1dMapperService.saveBatch(tvl1dList);
    }
}
