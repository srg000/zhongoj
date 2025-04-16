package com.srgstart.zhongoj.model.dto.question;

import lombok.Data;

/**
 * @author srgstart
 * @create 2025/03/23 10:09
 * @description 题目用例
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

}
