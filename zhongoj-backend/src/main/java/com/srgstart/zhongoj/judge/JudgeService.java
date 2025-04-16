package com.srgstart.zhongoj.judge;

import com.srgstart.zhongoj.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongoj.model.entity.QuestionSubmit;

/**
 * @author srgstart
 * @create 2025/03/27 16:53
 * @description 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 提交记录id
     * @return 题目提交信息
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
