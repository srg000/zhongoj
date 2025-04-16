package com.srgstart.zhongojbackendjudgeservice.judge;


import com.srgstart.zhongojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.srgstart.zhongojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.srgstart.zhongojbackendjudgeservice.judge.strategy.JudgeContext;
import com.srgstart.zhongojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
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
