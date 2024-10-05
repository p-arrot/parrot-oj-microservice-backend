package com.tatakai.parrotojbackendmodel.codeSandbox.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendcommon.utils.ThrowUtil;
import com.tatakai.parrotojbackendmodel.codeSandbox.CodeSandbox;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeRequest;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class RemoteCodesandbox implements CodeSandbox {
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("调用远程代码沙箱。。。。");
        String url = "http://111.229.65.33:8281/executeCode";
        HttpResponse response = HttpUtil.createPost(url).header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET).body(JSONUtil.toJsonStr(executeCodeRequest)).execute();
        String body = response.body();
        ThrowUtil.throwIf(StringUtils.isEmpty(body), ErrorCode.OPERATION_ERROR, "远程代码沙箱执行失败");
        return JSONUtil.toBean(body, ExecuteCodeResponse.class);
    }
}
