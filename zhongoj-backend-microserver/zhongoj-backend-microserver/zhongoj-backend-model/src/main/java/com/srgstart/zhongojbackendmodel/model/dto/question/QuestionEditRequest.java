package com.srgstart.zhongojbackendmodel.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑题目表请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例（json 数组）、前端传对象，后端将对象转换为json字符串
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json 对象）、前端传对象，后端将对象转换为json字符串
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}