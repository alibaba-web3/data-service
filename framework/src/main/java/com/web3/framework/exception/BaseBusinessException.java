package com.web3.framework.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseBusinessException  extends RuntimeException {

    private static final long serialVersionUID = 1935127744629042787L;

    private String errorCode;

    public BaseBusinessException(String errorCode) {
        this.errorCode = errorCode;
    }

    public BaseBusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseBusinessException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

}
