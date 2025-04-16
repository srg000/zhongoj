package com.srgstart.zhongojbackendquestionservice.controller.inner;

import com.srgstart.zhongojbackendmodel.model.entity.Question;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import com.srgstart.zhongojbackendquestionservice.service.QuestionService;
import com.srgstart.zhongojbackendquestionservice.service.QuestionSubmitService;
import com.srgstart.zhongojbackendserviceclient.service.QuestionFeignClient;
import com.srgstart.zhongojbackendserviceclient.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 题目表接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;


    /**
     * 根据id 获取题目信息
     *
     * @param questionId 题目id
     * @return
     */
    @GetMapping("/question/get/id")
    @Override
    public Question getQuestionById(long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * 根据id 获取题目提交信息
     *
     * @param questionSubmitId 题目提交id
     * @return
     */
    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(Long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 根据id 更新题目提交信息
     *
     * @param questionSubmitUpdate
     * @return
     */
    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(QuestionSubmit questionSubmitUpdate) {
        return questionSubmitService.updateById(questionSubmitUpdate);
    }
}
