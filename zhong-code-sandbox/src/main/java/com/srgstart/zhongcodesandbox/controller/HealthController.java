package com.srgstart.zhongcodesandbox.controller;

import cn.hutool.http.HttpResponse;
import com.srgstart.zhongcodesandbox.JavaNativeCodeSandbox;
import com.srgstart.zhongcodesandbox.model.ExecuteCodeRequest;
import com.srgstart.zhongcodesandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;

/**
 * @author srgstart
 * @create 2025/03/28 10:11
 * @description
 */
@RestController
public class HealthController {

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;

    /**
     * 定义鉴权请求头和密钥
     */
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";


    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    /**
     * 执行代码
     * @param executeCodeRequest 执行代码请求
     * @return 执行代码响应
     */
    @PostMapping("/executeCode")
    public ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest,
                                           HttpServletRequest request, HttpServletResponse response) {
        // 使用请求头信息 鉴权
        String header = request.getHeader(AUTH_REQUEST_HEADER);
        if (header == null || !header.equals(AUTH_REQUEST_SECRET)) {
            response.setStatus(403);
            return null;
        }

        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }
}
