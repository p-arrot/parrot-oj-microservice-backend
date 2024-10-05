package com.tatakai.parrotojbackendquestionservice.job.once;

import cn.hutool.core.collection.CollUtil;

import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendquestionservice.es.QuestionESDAO;
import com.tatakai.parrotojbackendquestionservice.es.QuestionESDTO;
import com.tatakai.parrotojbackendquestionservice.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FullSyncQuestionToES implements CommandLineRunner {
    @Resource
    private QuestionESDAO questionESDAO;
    @Resource
    private QuestionService questionService;

    @Override
    public void run(String... args) {
        List<Question> questionList = questionService.list();
        if (CollUtil.isEmpty(questionList)) return;
        List<QuestionESDTO> questionESDTOList = questionList.stream().map(QuestionESDTO::objToDTO).collect(Collectors.toList());
        int batchSize = 500;
        int size = questionESDTOList.size();
        log.info("FullSyncQuestionToES start,total size:{}", size);
        for (int start = 0; start < size; start++) {
            int end = Math.min(start + batchSize, size);
            questionESDAO.saveAll(questionESDTOList.subList(start, end));
        }
        log.info("FullSyncQuestionToES end");
    }

}
