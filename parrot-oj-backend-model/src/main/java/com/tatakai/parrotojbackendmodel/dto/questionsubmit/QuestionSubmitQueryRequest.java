package com.tatakai.parrotojbackendmodel.dto.questionsubmit;

import com.tatakai.parrotojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = false)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 状态
     */
    private Integer status;


    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;





    private static final long serialVersionUID = 1L;
}
