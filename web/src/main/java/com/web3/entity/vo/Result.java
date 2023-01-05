package com.web3.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Result<T> extends BasicResult {

    private T content;

}
