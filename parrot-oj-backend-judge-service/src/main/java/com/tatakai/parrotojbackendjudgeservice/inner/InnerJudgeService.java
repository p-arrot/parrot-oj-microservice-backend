package com.tatakai.parrotojbackendjudgeservice.inner;

import com.tatakai.parrotojbackendjudgeservice.service.JudgeService;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendserviceclient.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class InnerJudgeService implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;
    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") Long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
