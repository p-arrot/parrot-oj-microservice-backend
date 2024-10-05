package com.tatakai.parrotojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.dto.question.QuestionQueryRequest;
import com.tatakai.parrotojbackendmodel.vo.question.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 30215
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-09-12 12:15:31
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);



    /**
     * 获取帖子封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    Page<Question> searchQuestionFromES(QuestionQueryRequest questionQueryRequest);
}
