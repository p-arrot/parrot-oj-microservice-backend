package com.tatakai.parrotojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tatakai.parrotojbackendcommon.enums.QuestionSubmitLanguageEnum;
import com.tatakai.parrotojbackendcommon.enums.QuestionSubmitStatusEnum;
import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendmodel.domain.User;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.tatakai.parrotojbackendmodel.vo.questionSubmit.QuestionSubmitVO;
import com.tatakai.parrotojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.tatakai.parrotojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.tatakai.parrotojbackendquestionservice.service.QuestionService;
import com.tatakai.parrotojbackendquestionservice.service.QuestionSubmitService;
import com.tatakai.parrotojbackendserviceclient.JudgeFeignClient;
import com.tatakai.parrotojbackendserviceclient.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 30215
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2024-09-12 12:15:32
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;


    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        QuestionSubmit questionSubmit=new QuestionSubmit();

        String language=questionSubmitAddRequest.getLanguage();
        String code = questionSubmitAddRequest.getCode();
        Long questionId = questionSubmitAddRequest.getQuestionId();

        QuestionSubmitLanguageEnum languageEnum= QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        Question question=questionService.getById(questionId);
        if (question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"未找到该题目");
        }
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(code);
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(loginUser.getId());
        boolean save = save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"提交失败");
        }

        Long questionSubmitId = questionSubmit.getId();
        // 执行判题服务
//        CompletableFuture.runAsync(()->{
//            judgeFeignClient.doJudge(questionSubmitId);
//        });
        myMessageProducer.sendMessage(String.valueOf(questionSubmitId),"my_exchange","my_routingKey");


        return questionSubmitId;
    }


    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        int current = questionSubmitQueryRequest.getCurrent();
        int pageSize = questionSubmitQueryRequest.getPageSize();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField),sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);


        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        if (!loginUser.getId().equals(questionSubmit.getUserId()) && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getPages(), questionSubmitPage.getTotal());
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser)).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}




