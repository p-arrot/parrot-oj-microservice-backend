package com.tatakai.parrotojbackendmodel.dto.codeSandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入
     */
    private List<String> inputList;


    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

    /**
     * Main 模板
     */
    private String mainClassTemplate;

    /**
     * Solution 模板
     */
    private String solutionClassTemplate;
}
