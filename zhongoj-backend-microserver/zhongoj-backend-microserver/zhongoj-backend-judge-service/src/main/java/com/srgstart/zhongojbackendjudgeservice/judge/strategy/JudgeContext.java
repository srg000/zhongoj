package com.srgstart.zhongojbackendjudgeservice.judge.strategy;


import com.srgstart.zhongojbackendmodel.model.dto.question.JudgeCase;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongojbackendmodel.model.entity.Question;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

    private Integer status;

}
