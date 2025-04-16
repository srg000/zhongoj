package com.srgstart.zhongojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.srgstart.zhongojbackendcommon.common.ErrorCode;
import com.srgstart.zhongojbackendcommon.constant.CommonConstant;
import com.srgstart.zhongojbackendcommon.exception.BusinessException;
import com.srgstart.zhongojbackendcommon.exception.ThrowUtils;
import com.srgstart.zhongojbackendcommon.utils.SqlUtils;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.srgstart.zhongojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.srgstart.zhongojbackendmodel.model.entity.Question;
import com.srgstart.zhongojbackendmodel.model.entity.QuestionSubmit;
import com.srgstart.zhongojbackendmodel.model.entity.User;
import com.srgstart.zhongojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.srgstart.zhongojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.srgstart.zhongojbackendmodel.model.vo.QuestionSubmitVO;
import com.srgstart.zhongojbackendmodel.model.vo.UserVO;
import com.srgstart.zhongojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.srgstart.zhongojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.srgstart.zhongojbackendquestionservice.service.QuestionService;
import com.srgstart.zhongojbackendquestionservice.service.QuestionSubmitService;
import com.srgstart.zhongojbackendserviceclient.service.JudgeFeignClient;
import com.srgstart.zhongojbackendserviceclient.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 题目提交信息服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 校验数据
     *
     * @param questionSubmit
     * @param add            对创建的数据进行校验
     */
    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add) {
        ThrowUtils.throwIf(questionSubmit == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Long questionId = questionSubmit.getQuestionId();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isAnyBlank(language, code), ErrorCode.PARAMS_ERROR);
        }
        // 修改数据时，有参数则校验
        ThrowUtils.throwIf(questionId == null || questionId < 0, ErrorCode.PARAMS_ERROR);
        if (StringUtils.isNotBlank(language)) {
            ThrowUtils.throwIf(language.length() > 80, ErrorCode.PARAMS_ERROR, "编程语言过长");
        }
    }

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未选择指定编程语言");
        }
        // 需要根据题目id 拿到判题样例 和 判题配置，供后面的代码沙箱使用
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);
        // 数据校验
        this.validQuestionSubmit(questionSubmit, true);
        // 填充默认值
        questionSubmit.setUserId(loginUser.getId());
        // 设置初始的判题状态
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        // 发送消息
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(questionSubmitId));
//        // 异步执行判题服务
//        CompletableFuture.runAsync(() -> {
//            judgeFeignClient.doJudge(questionSubmitId);
//        });
        return questionSubmitId;
    }

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取题目提交信息封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request) {
        // 对象转封装类
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);

        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionSubmit.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userFeignClient.getById(userId);
        }
        UserVO userVO = userFeignClient.getUserVO(user);
        questionSubmitVO.setUser(userVO);
        // endregion

        User loginUser = userFeignClient.getLoginUser(request);
        // 脱敏：仅本人和管理员可以查看提交的代码
        assert userId != null;
        if (!userId.equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    /**
     * 分页获取题目提交信息封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            return QuestionSubmitVO.objToVo(questionSubmit);
        }).collect(Collectors.toList());

        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userFeignClient.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        User loginUser = userFeignClient.getLoginUser(request);
        questionSubmitVOList.forEach(questionSubmitVO -> {
            Long userId = questionSubmitVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionSubmitVO.setUser(userFeignClient.getUserVO(user));
            // 脱敏：仅本人和管理员可以查看提交的代码
            assert userId != null;
            if (!userId.equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
                questionSubmitVO.setCode(null);
            }
            Long questionId = questionSubmitVO.getQuestionId();
            questionSubmitVO.setTitle(questionService.getById(questionId).getTitle());
        });
        // endregion

        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}
