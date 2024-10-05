package com.tatakai.parrotojbackendcommon.result;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private final int code;
    private T data;
    private String msg;

    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, T data) {
         this(code, data, "");
    }

    public BaseResponse(int code, String msg) {
        this(code, null, msg);
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
