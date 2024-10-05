package com.tatakai.parrotojbackendmodel.codeSandbox;


import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeRequest;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
