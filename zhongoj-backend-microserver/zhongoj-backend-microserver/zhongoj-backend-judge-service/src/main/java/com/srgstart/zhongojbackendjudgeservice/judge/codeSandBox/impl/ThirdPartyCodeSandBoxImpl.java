package com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.impl;


import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @author srgstart
 * @create 2025/03/27 15:12
 * @description 第三方代码沙箱
 */
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
