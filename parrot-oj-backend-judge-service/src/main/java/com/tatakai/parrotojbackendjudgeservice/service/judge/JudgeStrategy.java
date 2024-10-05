package com.tatakai.parrotojbackendjudgeservice.service.judge;


import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeContext;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
