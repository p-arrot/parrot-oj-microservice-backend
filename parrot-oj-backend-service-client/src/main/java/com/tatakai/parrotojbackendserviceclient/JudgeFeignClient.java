package com.tatakai.parrotojbackendserviceclient;

import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "parrot-oj-backend-judge-service",path="/api/judge/inner")
public interface JudgeFeignClient {


    /**
     * 对题目提交进行判题，并返回判题结果。
     *
     * @param questionSubmitId 题目提交ID
     * @return 判题结果对象
     */

    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") Long questionSubmitId);

}
