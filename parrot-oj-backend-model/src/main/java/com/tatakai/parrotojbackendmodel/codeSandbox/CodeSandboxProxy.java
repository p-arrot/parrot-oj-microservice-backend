package com.tatakai.parrotojbackendmodel.codeSandbox;

import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeRequest;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy {
    private final CodeSandbox codeSandbox;
    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest){
        log.info("调用代码沙箱执行代码,开始，请求："+executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = this.codeSandbox.executeCode(executeCodeRequest);
        log.info("调用代码沙箱执行代码,结束，响应："+executeCodeResponse);
        return executeCodeResponse;
    }
}
