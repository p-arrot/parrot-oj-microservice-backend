package com.tatakai.parrotojbackendjudgeservice.service;


import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
