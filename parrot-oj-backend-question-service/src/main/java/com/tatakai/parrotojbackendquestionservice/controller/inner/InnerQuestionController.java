package com.tatakai.parrotojbackendquestionservice.controller.inner;

import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendquestionservice.service.QuestionService;
import com.tatakai.parrotojbackendquestionservice.service.QuestionSubmitService;
import com.tatakai.parrotojbackendserviceclient.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class InnerQuestionController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
