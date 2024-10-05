package com.tatakai.parrotojbackendmodel.dto.questionsubmit;



import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;
import com.tatakai.parrotojbackendmodel.dto.question.JudgeCase;
import lombok.Data;

import java.util.List;
@Data
public class JudgeContext {
    private ExecuteCodeResponse executeCodeResponse;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
