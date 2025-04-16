package com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox;


import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @author srgstart
 * @create 2025/03/27 14:05
 * @description 代码沙箱接口定义
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest 执行代码请求
     * @return 执行代码响应
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
