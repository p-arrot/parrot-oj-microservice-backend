package com.tatakai.parrotojbackendjudgeservice.service.judge;



import com.tatakai.parrotojbackendcommon.enums.QuestionSubmitLanguageEnum;
import com.tatakai.parrotojbackendmodel.domain.QuestionSubmit;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeContext;
import com.tatakai.parrotojbackendmodel.dto.questionsubmit.JudgeInfo;

public class JudgeManager implements JudgeStrategy{

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        String java = QuestionSubmitLanguageEnum.JAVA.getValue();
        JudgeStrategy judgeStrategy = null;
        if(java.equals(language)){
            judgeStrategy = new JavaJudge();
        }else{
            judgeStrategy = new DefaultJudge();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
