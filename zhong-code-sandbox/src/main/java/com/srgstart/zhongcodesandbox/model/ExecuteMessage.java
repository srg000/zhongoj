package com.srgstart.zhongcodesandbox.model;

import lombok.Data;

/**
 * @author srgstart
 * @create 2025/04/02 14:08
 * @description
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

    private Long memory;
}
