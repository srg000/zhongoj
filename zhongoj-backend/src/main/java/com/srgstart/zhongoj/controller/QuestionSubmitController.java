package com.srgstart.zhongoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.srgstart.zhongoj.annotation.AuthCheck;
import com.srgstart.zhongoj.common.BaseResponse;
import com.srgstart.zhongoj.common.DeleteRequest;
import com.srgstart.zhongoj.common.ErrorCode;
import com.srgstart.zhongoj.common.ResultUtils;
import com.srgstart.zhongoj.constant.UserConstant;
import com.srgstart.zhongoj.exception.BusinessException;
import com.srgstart.zhongoj.exception.ThrowUtils;
import com.srgstart.zhongoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.srgstart.zhongoj.model.dto.questionsubmit.QuestionSubmitEditRequest;
import com.srgstart.zhongoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.srgstart.zhongoj.model.dto.questionsubmit.QuestionSubmitUpdateRequest;
import com.srgstart.zhongoj.model.entity.QuestionSubmit;
import com.srgstart.zhongoj.model.entity.User;
import com.srgstart.zhongoj.model.vo.QuestionSubmitVO;
import com.srgstart.zhongoj.service.QuestionSubmitService;
import com.srgstart.zhongoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交信息接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
//@RestController
//@RequestMapping("/questionSubmit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

//    // region 增删改查
//
//    /**
//     * 创建题目提交信息
//     *
//     * @param questionSubmitAddRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/add")
//    public BaseResponse<Long> addQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
//                                                HttpServletRequest request) {
//        ThrowUtils.throwIf(questionSubmitAddRequest == null ||
//                questionSubmitAddRequest.getQuestionId() == null, ErrorCode.PARAMS_ERROR);
//        User loginUser = userService.getLoginUser(request);
//        // 执行业务方法
//        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        return ResultUtils.success(questionSubmitId);
//    }
//
//    /**
//     * 删除题目提交信息
//     *
//     * @param deleteRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/delete")
//    public BaseResponse<Boolean> deleteQuestionSubmit(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
//        if (deleteRequest == null || deleteRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = userService.getLoginUser(request);
//        long id = deleteRequest.getId();
//        // 判断是否存在
//        QuestionSubmit oldQuestionSubmit = questionSubmitService.getById(id);
//        ThrowUtils.throwIf(oldQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可删除
//        if (!oldQuestionSubmit.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 操作数据库
//        boolean result = questionSubmitService.removeById(id);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
//
//    /**
//     * 更新题目提交信息（仅管理员可用）
//     *
//     * @param questionSubmitUpdateRequest
//     * @return
//     */
//    @PostMapping("/update")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Boolean> updateQuestionSubmit(@RequestBody QuestionSubmitUpdateRequest questionSubmitUpdateRequest) {
//        if (questionSubmitUpdateRequest == null || questionSubmitUpdateRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // todo 在此处将实体类和 DTO 进行转换
//        QuestionSubmit questionSubmit = new QuestionSubmit();
//        BeanUtils.copyProperties(questionSubmitUpdateRequest, questionSubmit);
//        // 数据校验
//        questionSubmitService.validQuestionSubmit(questionSubmit, false);
//        // 判断是否存在
//        long id = questionSubmitUpdateRequest.getId();
//        QuestionSubmit oldQuestionSubmit = questionSubmitService.getById(id);
//        ThrowUtils.throwIf(oldQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
//        // 操作数据库
//        boolean result = questionSubmitService.updateById(questionSubmit);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
//
//    /**
//     * 根据 id 获取题目提交信息（封装类）
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/get/vo")
//    public BaseResponse<QuestionSubmitVO> getQuestionSubmitVOById(long id, HttpServletRequest request) {
//        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
//        // 查询数据库
//        QuestionSubmit questionSubmit = questionSubmitService.getById(id);
//        ThrowUtils.throwIf(questionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
//        // 获取封装类
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVO(questionSubmit, request));
//    }
//
//    /**
//     * 分页获取题目提交信息列表（仅管理员可用）
//     *
//     * @param questionSubmitQueryRequest
//     * @return
//     */
//    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Page<QuestionSubmit>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        // 查询数据库
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        return ResultUtils.success(questionSubmitPage);
//    }
//
//    /**
//     * 分页获取题目提交信息列表（封装类）
//     *
//     * @param questionSubmitQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitVOByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
//                                                               HttpServletRequest request) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        // 查询数据库
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        // 获取封装类
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, request));
//    }
//
//    /**
//     * 分页获取当前登录用户创建的题目提交信息列表
//     *
//     * @param questionSubmitQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<QuestionSubmitVO>> listMyQuestionSubmitVOByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
//                                                                 HttpServletRequest request) {
//        ThrowUtils.throwIf(questionSubmitQueryRequest == null, ErrorCode.PARAMS_ERROR);
//        // 补充查询条件，只查询当前登录用户的数据
//        User loginUser = userService.getLoginUser(request);
//        questionSubmitQueryRequest.setUserId(loginUser.getId());
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        // 查询数据库
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        // 获取封装类
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, request));
//    }
//
//    /**
//     * 编辑题目提交信息（给用户使用）
//     *
//     * @param questionSubmitEditRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/edit")
//    public BaseResponse<Boolean> editQuestionSubmit(@RequestBody QuestionSubmitEditRequest questionSubmitEditRequest, HttpServletRequest request) {
//        if (questionSubmitEditRequest == null || questionSubmitEditRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // todo 在此处将实体类和 DTO 进行转换
//        QuestionSubmit questionSubmit = new QuestionSubmit();
//        BeanUtils.copyProperties(questionSubmitEditRequest, questionSubmit);
//        // 数据校验
//        questionSubmitService.validQuestionSubmit(questionSubmit, false);
//        User loginUser = userService.getLoginUser(request);
//        // 判断是否存在
//        long id = questionSubmitEditRequest.getId();
//        QuestionSubmit oldQuestionSubmit = questionSubmitService.getById(id);
//        ThrowUtils.throwIf(oldQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可编辑
//        if (!oldQuestionSubmit.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 操作数据库
//        boolean result = questionSubmitService.updateById(questionSubmit);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
//
//    // endregion
}
