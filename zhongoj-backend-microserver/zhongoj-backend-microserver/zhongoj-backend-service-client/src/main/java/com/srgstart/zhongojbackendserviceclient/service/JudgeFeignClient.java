package com.srgstart.zhongojbackendserviceclient.service;


import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author srgstart
 * @create 2025/03/27 16:53
 * @description 判题服务
 */
@FeignClient(name = "zhongoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * 判题
     * @param questionSubmitId 提交记录id
     * @return 题目提交信息
     */
    @GetMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
