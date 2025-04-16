package com.srgstart.zhongcodesandbox;

import com.srgstart.zhongcodesandbox.model.ExecuteCodeRequest;
import com.srgstart.zhongcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @author srgstart
 * @create 2025/04/02 13:53
 * @description Java 原生代码沙箱实现
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
