package com.web3.dal.data.service;

import com.web3.dal.data.entity.AddressChangeTemp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 地址余额变化中间表 服务类
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
public interface AddressChangeTempMapperService extends IService<AddressChangeTemp> {

    /**
     * 查询最新的数据
     *
     * @return 地址
     */
    AddressChangeTemp getLatest();

}
