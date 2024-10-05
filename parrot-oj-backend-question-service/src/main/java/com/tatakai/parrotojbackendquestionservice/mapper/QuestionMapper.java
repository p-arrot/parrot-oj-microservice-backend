package com.tatakai.parrotojbackendquestionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tatakai.parrotojbackendmodel.domain.Question;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author 30215
 * @description 针对表【question(题目)】的数据库操作Mapper
 * @createDate 2024-09-12 12:15:31
 * @Entity com.tatakai.parrotojbackend.model.domain.Question
 */
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("select * from question where updateTime > #{recentUpdateTime}")
    List<Question> listQuestionWithDelete(Date recentUpdateTime);

}




