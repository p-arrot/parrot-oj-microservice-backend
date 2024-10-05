package com.tatakai.parrotojbackendmodel.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目用例
 */

@Data
public class JudgeCase implements Serializable {
    private static final long serialVersionUID = 8728187976061864463L;
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;



}
