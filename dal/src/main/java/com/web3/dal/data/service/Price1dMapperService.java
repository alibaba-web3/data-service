package com.web3.dal.data.service;

import java.util.List;

import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.entity.Price1d;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-01-09
 */
public interface Price1dMapperService extends IService<Price1d> {

    /**
     * replace into 批量插入
     *
     * @param list
     */
    void replaceIntoBatch(List<Price1d> list);

}
