package com.tatakai.parrotojbackendquestionservice.job.cycle;


import com.tatakai.parrotojbackendmodel.domain.Question;
import com.tatakai.parrotojbackendquestionservice.es.QuestionESDAO;
import com.tatakai.parrotojbackendquestionservice.es.QuestionESDTO;
import com.tatakai.parrotojbackendquestionservice.mapper.QuestionMapper;
import com.tatakai.parrotojbackendquestionservice.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IncSyncQuestionToES {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionESDAO questionESDAO;


    @Scheduled(fixedRate = 60 * 1000L)
    public void run() {
        Date minUpdateTime = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<Question> questionList = questionMapper.listQuestionWithDelete(minUpdateTime);
        List<QuestionESDTO> questionESDTOList = questionList.stream().map(QuestionESDTO::objToDTO).collect(Collectors.toList());
        int batchSize = 500;
        int size = questionESDTOList.size();
        log.info("IncrSyncQuestionToES start,total size:{}", size);
        for (int start = 0; start < size; start++) {
            int end = Math.min(start + batchSize, size);
            questionESDAO.saveAll(questionESDTOList.subList(start, end));
        }
        log.info("IncrSyncQuestionToES end");
    }
}
