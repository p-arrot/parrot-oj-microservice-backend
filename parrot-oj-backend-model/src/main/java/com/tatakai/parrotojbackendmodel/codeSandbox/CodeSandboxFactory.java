package com.tatakai.parrotojbackendmodel.codeSandbox;


import com.tatakai.parrotojbackendmodel.codeSandbox.impl.RemoteCodesandbox;

public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "remote":
                return new RemoteCodesandbox();
            default:
                return null;
        }

    }
}
