package com.srgstart.zhongojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @author srgstart
 * @create 2025/03/23 10:11
 * @description 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(MB)
     */
    private Long memoryLimit;

    /**
     * 栈大小限制(MB)
     */
    private Long stackLimit;

}
