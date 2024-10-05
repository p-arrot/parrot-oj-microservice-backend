package com.tatakai.parrotojbackendcommon.exception;


import com.alibaba.csp.sentinel.Tracer;
import com.tatakai.parrotojbackendcommon.result.BaseResponse;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendcommon.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> handleBusinessException(BusinessException e) {
        log.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        Tracer.trace(e);
        return ResultUtil.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        e.printStackTrace();
        Tracer.trace(e);
        return ResultUtil.failure(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
