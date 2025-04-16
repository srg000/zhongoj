package com.srgstart.zhongojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;

import com.srgstart.zhongojbackendcommon.common.ErrorCode;
import com.srgstart.zhongojbackendcommon.exception.BusinessException;
import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandboxFactory;
import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandboxProxy;
import com.srgstart.zhongojbackendjudgeservice.judge.strategy.JudgeContext;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.srgstart.zhongojbackendmodel.model.dto.question.JudgeCase;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongojbackendmodel.model.entity.Question;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import com.srgstart.zhongojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.srgstart.zhongojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author srgstart
 * @create 2025/03/27 16:59
 * @description
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1、根据题目提交id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        // 2、如果题目提交状态不为 等待中，就不用重复执行了
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态不为等待中，不能重复执行");
        }
        // 3、更改判题（题目提交）的状态为"判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4、调用沙箱，获取到执行结果
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .inputList(inputList)
                .language(questionSubmit.getLanguage())
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 5、根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setStatus(executeCodeResponse.getStatus());

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6、修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(judgeInfo.getStatus());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        return questionFeignClient.getQuestionSubmitById(questionSubmit.getQuestionId());
    }
}
