package com.tatakai.parrotojbackendjudgeservice.service.judge;

import cn.hutool.json.JSONUtil;

import com.tatakai.parrotojbackendcommon.enums.JudgeInfoMessageEnum;
import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;
import com.tatakai.parrotojbackendmodel.dto.question.JudgeCase;
import com.tatakai.parrotojbackendmodel.dto.question.JudgeConfig;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeContext;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeInfo;

import java.util.List;

public class JavaJudge implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        ExecuteCodeResponse executeCodeResponse = judgeContext.getExecuteCodeResponse();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        List<String> outputList = executeCodeResponse.getOutputList();
        String message = executeCodeResponse.getMessage();
        Integer status = executeCodeResponse.getStatus();

        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        JudgeInfo res = new JudgeInfo();


        // 判断输出数目与用例数目是否一致
        if (outputList.size() != judgeCaseList.size()) {
            res.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getText());
            return res;
        }
        // 判断输出结果
        for (int i = 0; i < outputList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                res.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getText());
                return res;
            }
        }
        // 判断题目限制
        res.setTime(judgeInfo.getTime());
        res.setMemory(judgeInfo.getMemory());
        if (judgeInfo.getMemory() > judgeConfig.getMemoryLimit()) {
            res.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getText());
            return res;
        }
        if (judgeInfo.getTime() > judgeConfig.getTimeLimit()) {
            res.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getText());
            return res;
        }

        res.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        return res;
    }
}