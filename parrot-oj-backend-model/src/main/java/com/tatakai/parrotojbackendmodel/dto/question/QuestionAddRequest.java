package com.tatakai.parrotojbackendmodel.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 *
 */
@Data
public class QuestionAddRequest implements Serializable {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json数组）
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;


    /**
     * 判题用例（JSON数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json)
     */
    private JudgeConfig judgeConfig;

    /**
     * 主类模板
     */
    private String mainClassTemplate;

    /**
     * 解题类模板
     */
    private String solutionClassTemplate;



    private static final long serialVersionUID = 1L;
}