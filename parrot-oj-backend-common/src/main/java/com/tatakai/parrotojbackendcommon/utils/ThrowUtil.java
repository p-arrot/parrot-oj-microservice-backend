package com.tatakai.parrotojbackendcommon.utils;


import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;

public class ThrowUtil {
    public static void throwIf(boolean flag, ErrorCode errorCode) {
        if(flag) throw new BusinessException(errorCode);
    }
    public static void throwIf(boolean flag, ErrorCode errorCode,String message) {
        if(flag) throw new BusinessException(errorCode,message);
    }
}
