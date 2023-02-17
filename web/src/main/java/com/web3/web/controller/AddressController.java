package com.web3.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.framework.utils.DateUtils;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import com.web3.service.address.dto.AddressAssetsInfoDTO;
import com.web3.service.address.dto.AddressProfileDTO;
import com.web3.service.address.dto.AddressSearchDTO;
import com.web3.service.address.param.TransformBalanceReq;
import com.web3.web.entity.ResultUtils;
import com.web3.web.entity.param.AddressCurrentValueReq;
import com.web3.web.entity.vo.AddressAssetsValueVO;
import com.web3.web.entity.vo.AddressProfileVO;
import com.web3.web.entity.vo.AddressSearchVO;
import com.web3.web.entity.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: mianyun.yt
 * @Date: 2022/12/23
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private EthereumService ethereumService;

    @Resource
    private BalanceService balanceService;

    @GetMapping("/profile")
    Result<AddressProfileVO> getProfile(@RequestParam String address) {
        AddressProfileDTO dto = addressService.getProfile(address);
        if (dto == null) {
            return ResultUtils.createSuccessRes(null);
        }
        AddressProfileVO result = new AddressProfileVO();
        BeanUtils.copyProperties(dto, result);
        return ResultUtils.createSuccessRes(result);
    }

    @GetMapping("/version")
    Result<String> getVersion() throws IOException {
        return ResultUtils.createSuccessRes(ethereumService.getWeb3ClientVersion());
    }

    @GetMapping("/balance")
    Result<BigDecimal> getBalance(@RequestParam String address) throws IOException {
        return ResultUtils.createSuccessRes(ethereumService.getEthBalance(address));
    }

    @GetMapping("/search")
    Result<List<AddressSearchVO>> search(@RequestParam String searchKey) {

        List<AddressSearchDTO> addressSearchDTOList = addressService.search(searchKey);

        if (CollectionUtils.isEmpty(addressSearchDTOList)) {
            return ResultUtils.createSuccessRes(null);
        }

        List<AddressSearchVO> result = addressSearchDTOList.stream().map(dto -> {
            AddressSearchVO vo = new AddressSearchVO();
            BeanUtils.copyProperties(dto, vo);
            return vo;
        }).toList();

        return ResultUtils.createSuccessRes(result);
    }

    @GetMapping("/currentTotalValue")
    Result<AddressAssetsValueVO> currentTotalValue(@RequestBody AddressCurrentValueReq req) {
        AddressAssetsValueVO valueVO = new AddressAssetsValueVO();
        // 根据 address 查询所有币种及其数量
        List<AddressAssetsInfoDTO> assets = addressService.getAllAssetsInfo(req.getAddress());
        if (CollectionUtils.isEmpty(assets)) {
            return new Result<AddressAssetsValueVO>();
        }

        // 查询币种的最新余额总和, 按照targetCurrency转换
        List<TransformBalanceReq> reqList = new ArrayList<>();
        assets.forEach(item -> {
            TransformBalanceReq transformBalanceReq = new TransformBalanceReq();
            transformBalanceReq.setFromAsset(item.getSymbol());
            transformBalanceReq.setToAsset(req.getTargetCurrency());
            transformBalanceReq.setCount(item.getCount());
            reqList.add(transformBalanceReq);
        });
        Map<String, BigDecimal> map = balanceService.transformBalance(reqList);
        BigDecimal totalValue = map.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valueChange = balanceService.balanceValueTrend(req.getAddress(), "ALL", totalValue);

        valueVO.setAddress(req.getAddress());
        valueVO.setTargetCurrency(req.getTargetCurrency());
        valueVO.setValue(totalValue);
        valueVO.setValueChange(valueChange);
        valueVO.setDate(DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));

        return ResultUtils.createSuccessRes(valueVO);
    }

}
