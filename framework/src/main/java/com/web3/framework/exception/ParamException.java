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
public class ParamException extends BaseBusinessException {

    private static final long serialVersionUID = 1935127744629042787L;

    public ParamException(String errorCode) {
        super(errorCode);
    }

    public ParamException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ParamException(String errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

}
