package com.srgstart.zhongoj.judge;

import com.srgstart.zhongoj.judge.strategy.DefaultJudgeStrategy;
import com.srgstart.zhongoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.srgstart.zhongoj.judge.strategy.JudgeContext;
import com.srgstart.zhongoj.judge.strategy.JudgeStrategy;
import com.srgstart.zhongoj.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
