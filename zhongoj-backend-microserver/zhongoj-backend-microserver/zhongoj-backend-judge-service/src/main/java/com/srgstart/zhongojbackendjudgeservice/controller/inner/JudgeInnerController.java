package com.srgstart.zhongojbackendjudgeservice.controller.inner;

import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import com.srgstart.zhongojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author srgstart
 * @create 2025/04/09 17:06
 * @description
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeFeignClient judgeService;

    @GetMapping("/do")
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
