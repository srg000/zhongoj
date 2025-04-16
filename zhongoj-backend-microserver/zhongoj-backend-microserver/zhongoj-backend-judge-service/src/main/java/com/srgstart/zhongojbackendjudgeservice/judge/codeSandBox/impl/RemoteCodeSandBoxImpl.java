package com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.srgstart.zhongojbackendcommon.common.ErrorCode;
import com.srgstart.zhongojbackendcommon.exception.BusinessException;
import com.srgstart.zhongojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.srgstart.zhongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @author srgstart
 * @create 2025/03/27 15:12
 * @description 远程代码沙箱（实际场景调用）
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {

    /**
     * 定义鉴权请求头和密钥
     */
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String executeCodeRequestStr = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(executeCodeRequestStr)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
