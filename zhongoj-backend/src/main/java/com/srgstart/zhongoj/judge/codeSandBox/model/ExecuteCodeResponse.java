package com.srgstart.zhongoj.judge.codeSandBox.model;

import com.srgstart.zhongoj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author srgstart
 * @create 2025/03/27 14:06
 * @description 代码沙箱执行代码的 响应类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse {

    /**
     * 一组样例输出信息
     */
    private List<String> outputList;

    /**
     * 接口执行信息
     */
    private String message;

    /**
     * 代码执行状态
     */
    private Integer status;

    /**
     * 代码执行结果
     */
    private JudgeInfo judgeInfo;
}
