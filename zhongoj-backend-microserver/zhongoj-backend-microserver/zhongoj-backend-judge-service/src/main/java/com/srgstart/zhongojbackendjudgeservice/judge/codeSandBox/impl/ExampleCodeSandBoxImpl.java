package com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.impl;



import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.srgstart.zhongojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.srgstart.zhongojbackendmodel.model.enums.QuestionSubmitStatusEnum;

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
