package com.tatakai.parrotojbackendmodel.dto.questionsubmit;

import lombok.Data;

@Data
public class QuestionSubmitAddRequest {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;


    /**
     * 题目id
     */
    private Long questionId;





}
