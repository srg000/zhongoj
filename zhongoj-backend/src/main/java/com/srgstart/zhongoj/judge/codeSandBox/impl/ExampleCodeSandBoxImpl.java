package com.srgstart.zhongoj.judge.codeSandBox.impl;

import com.srgstart.zhongoj.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.srgstart.zhongoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.srgstart.zhongoj.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongoj.model.enums.JudgeInfoMessageEnum;
import com.srgstart.zhongoj.model.enums.QuestionSubmitStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author srgstart
 * @create 2025/03/27 15:12
 * @description 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
