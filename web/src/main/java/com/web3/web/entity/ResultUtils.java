package com.web3.web.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.web3.web.entity.vo.PageResult;
import com.web3.web.entity.vo.Result;

/**
 * 封装 api 结果方法
 *
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
public class ResultUtils {

    /**
     * 调用成功
     */
    public static <T> Result<T> createSuccessRes(T data) {
        Result<T> result = new Result<>();

        result.setData(data);
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("success");

        return result;
    }

    /**
     * 服务端原因导致的异常
     */
    public static Result<Boolean> createFailRes(Integer code, String message) {
        Result<Boolean> result = new Result<>();

        result.setData(false);
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    public static <T> Result<T> createFailRes(Integer code, String message, T data) {
        Result<T> result = new Result<>();

        result.setData(data);
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    /**
     * 客户端原因导致的异常
     */
    public static Result<Boolean> createClientFailRes(String message) {
        Result<Boolean> result = new Result<>();

        result.setData(false);
        result.setSuccess(false);
        result.setCode(400);
        result.setMessage(message);

        return result;
    }

    /**
     * 分页查询结果封装
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<PageResult<T>> createSuccessPageRes(PageInfo<T> data) {
        Result<PageResult<T>> result = new Result<PageResult<T>>();

        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalPage(data.getPages());
        pageResult.setTotal(data.getTotal());
        pageResult.setList(data.getList());

        result.setCode(200);
        result.setMessage("success");
        result.setSuccess(Boolean.TRUE);
        result.setData(pageResult);

        return result;
    }

}
