package com.srgstart.zhongojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import com.srgstart.zhongojbackendmodel.model.entity.User;
import com.srgstart.zhongojbackendmodel.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交信息服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 校验数据
     *
     * @param questionSubmit
     * @param add 对创建的数据进行校验
     */
    void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add);

    /**
     * 题目提交业务
     * @param questionSubmitAddRequest 题目提交请求
     * @param loginUser 登录用户信息
     * @return id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);
    
    /**
     * 获取题目提交信息封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);

    /**
     * 分页获取题目提交信息封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
