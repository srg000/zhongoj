package com.srgstart.zhongojbackendmodel.model.dto.questionsubmit;

import lombok.Data;

/**
 * @author srgstart
 * @create 2025/03/23 10:11
 * @description 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间(ms)
     */
    private Long time;

    /**
     * 内存消耗（kb）
     */
    private Long memory;

    /**
     * 代码执行状态
     */
    private Integer status;

}
