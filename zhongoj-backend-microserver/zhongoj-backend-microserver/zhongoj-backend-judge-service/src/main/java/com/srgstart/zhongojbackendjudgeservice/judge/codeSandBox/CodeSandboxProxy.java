package com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox;


import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author srgstart
 * @create 2025/03/27 15:18
 * @description 代码沙箱 代理模式，增强沙箱能力
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandBox {

    private final CodeSandBox codeSandBox;


    public CodeSandboxProxy(CodeSandBox codeSandbox) {
        this.codeSandBox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}