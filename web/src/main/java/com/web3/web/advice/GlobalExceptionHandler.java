package com.web3.web.advice;

import com.web3.web.entity.ResultUtils;
import com.web3.web.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/1
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未捕获的异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Boolean> globalException(Exception e) {
        log.error("globalException: ", e);
        return ResultUtils.createFailRes(500, e.getMessage());
    }

}
