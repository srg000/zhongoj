package com.srgstart.zhongojbackendserviceclient.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.zhongojbackendmodel.model.entity.Question;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 题目表服务
 *
 * @author Administrator
 */
@FeignClient(name = "zhongoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    /**
     * 根据id 获取题目信息
     *
     * @param questionId 题目id
     * @return
     */
    @GetMapping("/question/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 根据id 获取题目提交信息
     *
     * @param questionSubmitId 题目提交id
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId);

    /**
     * 根据id 更新题目提交信息
     *
     * @param questionSubmitUpdate
     * @return
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmitUpdate);

}
