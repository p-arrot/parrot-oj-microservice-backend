package com.tatakai.parrotojbackendserviceclient;


import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.tatakai.parrotojbackendmodel.domain.Question;

/**
 * @author 30215
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2024-04-14 12:40:53
 */
@FeignClient(name="parrot-oj-backend-question-service",path = "/api/question/inner")
public interface QuestionFeignClient{
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);


    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}