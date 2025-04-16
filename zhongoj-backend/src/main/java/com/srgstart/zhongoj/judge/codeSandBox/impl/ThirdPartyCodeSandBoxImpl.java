package com.srgstart.zhongoj.judge.codeSandBox.impl;

import com.srgstart.zhongoj.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.srgstart.zhongoj.judge.codeSandBox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

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
