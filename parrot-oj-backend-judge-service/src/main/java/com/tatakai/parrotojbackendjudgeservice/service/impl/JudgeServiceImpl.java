package com.tatakai.parrotojbackendjudgeservice.service.impl;

import cn.hutool.json.JSONUtil;

import com.tatakai.parrotojbackendcommon.enums.QuestionSubmitLanguageEnum;
import com.tatakai.parrotojbackendcommon.enums.QuestionSubmitStatusEnum;
import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendcommon.utils.ThrowUtil;
import com.tatakai.parrotojbackendjudgeservice.service.JudgeService;
import com.tatakai.parrotojbackendjudgeservice.service.judge.JudgeManager;
import com.tatakai.parrotojbackendmodel.codeSandbox.CodeSandboxFactory;
import com.tatakai.parrotojbackendmodel.codeSandbox.CodeSandboxProxy;
import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeRequest;
import com.tatakai.parrotojbackendmodel.dto.codeSandbox.ExecuteCodeResponse;
import com.tatakai.parrotojbackendmodel.dto.question.JudgeCase;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeContext;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeInfo;
import com.tatakai.parrotojbackendserviceclient.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Value("${code-sandbox.type}")
    private String type;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 从数据库中获取题目提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        // 2. 校验参数
        ThrowUtil.throwIf(questionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeInfo = questionSubmit.getJudgeInfo();
        Integer status = questionSubmit.getStatus();
        Long questionId = questionSubmit.getQuestionId();
        Long userId = questionSubmit.getUserId();
        Date createTime = questionSubmit.getCreateTime();
        Date updateTime = questionSubmit.getUpdateTime();
        Integer isDelete = questionSubmit.getIsDelete();
        if (QuestionSubmitLanguageEnum.getEnumByValue(language) == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "语言参数错误");
        }
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到题目信息！");
        }
        if (!QuestionSubmitStatusEnum.WAITING.getValue().equals(status)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已在判题中或已判题结束，无需重复提交！");
        }

        // 3. 更新题目状态
        QuestionSubmit updateSubmit = new QuestionSubmit();
        updateSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        updateSubmit.setId(questionSubmitId);
        boolean res = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if (!res) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目状态失败！");
        }
        // 4. 调用代码沙箱
        List<JudgeCase> judgeCaseList = JSONUtil.toList(JSONUtil.parseArray(question.getJudgeCase()), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().code(code).inputList(inputList).language(language).mainClassTemplate(question.getMainClassTemplate()).solutionClassTemplate(question.getSolutionClassTemplate()).build();
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        // 5. 判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setExecuteCodeResponse(executeCodeResponse);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeManager judgeManager = new JudgeManager();
        JudgeInfo judgeRes = judgeManager.doJudge(judgeContext);

        // 6. 更新数据库中提交表
        QuestionSubmit result=new QuestionSubmit();
        result.setId(questionSubmitId);
        result.setJudgeInfo(JSONUtil.toJsonStr(judgeRes));
        result.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionFeignClient.updateQuestionSubmitById(result);

        return result;
    }
}
