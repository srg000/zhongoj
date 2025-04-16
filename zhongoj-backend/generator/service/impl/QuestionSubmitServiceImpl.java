package com.srgstart.zhongoj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.zhongoj.common.ErrorCode;
import com.srgstart.zhongoj.constant.CommonConstant;
import com.srgstart.zhongoj.exception.ThrowUtils;
import com.srgstart.zhongoj.mapper.QuestionSubmitMapper;
import com.srgstart.zhongoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.srgstart.zhongoj.model.entity.QuestionSubmit;
import com.srgstart.zhongoj.model.entity.QuestionSubmitFavour;
import com.srgstart.zhongoj.model.entity.QuestionSubmitThumb;
import com.srgstart.zhongoj.model.entity.User;
import com.srgstart.zhongoj.model.vo.QuestionSubmitVO;
import com.srgstart.zhongoj.model.vo.UserVO;
import com.srgstart.zhongoj.service.QuestionSubmitService;
import com.srgstart.zhongoj.service.UserService;
import com.srgstart.zhongoj.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private UserService userService;

    /**
     * 校验数据
     *
     * @param questionSubmit
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add) {
        ThrowUtils.throwIf(questionSubmit == null, ErrorCode.PARAMS_ERROR);
        // todo 从对象中取值
        String title = questionSubmit.getTitle();
        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        if (StringUtils.isNotBlank(title)) {
            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
        }
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
        // todo 从对象中取值
        Long id = questionSubmitQueryRequest.getId();
        Long notId = questionSubmitQueryRequest.getNotId();
        String title = questionSubmitQueryRequest.getTitle();
        String content = questionSubmitQueryRequest.getContent();
        String searchText = questionSubmitQueryRequest.getSearchText();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        List<String> tagList = questionSubmitQueryRequest.getTags();
        Long userId = questionSubmitQueryRequest.getUserId();
        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("title", searchText).or().like("content", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        // JSON 数组查询
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
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

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionSubmit.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionSubmitVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
        long questionSubmitId = questionSubmit.getId();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            // 获取点赞
            QueryWrapper<QuestionSubmitThumb> questionSubmitThumbQueryWrapper = new QueryWrapper<>();
            questionSubmitThumbQueryWrapper.in("questionSubmitId", questionSubmitId);
            questionSubmitThumbQueryWrapper.eq("userId", loginUser.getId());
            QuestionSubmitThumb questionSubmitThumb = questionSubmitThumbMapper.selectOne(questionSubmitThumbQueryWrapper);
            questionSubmitVO.setHasThumb(questionSubmitThumb != null);
            // 获取收藏
            QueryWrapper<QuestionSubmitFavour> questionSubmitFavourQueryWrapper = new QueryWrapper<>();
            questionSubmitFavourQueryWrapper.in("questionSubmitId", questionSubmitId);
            questionSubmitFavourQueryWrapper.eq("userId", loginUser.getId());
            QuestionSubmitFavour questionSubmitFavour = questionSubmitFavourMapper.selectOne(questionSubmitFavourQueryWrapper);
            questionSubmitVO.setHasFavour(questionSubmitFavour != null);
        }
        // endregion

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

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> questionSubmitIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> questionSubmitIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> questionSubmitIdSet = questionSubmitList.stream().map(QuestionSubmit::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取点赞
            QueryWrapper<QuestionSubmitThumb> questionSubmitThumbQueryWrapper = new QueryWrapper<>();
            questionSubmitThumbQueryWrapper.in("questionSubmitId", questionSubmitIdSet);
            questionSubmitThumbQueryWrapper.eq("userId", loginUser.getId());
            List<QuestionSubmitThumb> questionSubmitQuestionSubmitThumbList = questionSubmitThumbMapper.selectList(questionSubmitThumbQueryWrapper);
            questionSubmitQuestionSubmitThumbList.forEach(questionSubmitQuestionSubmitThumb -> questionSubmitIdHasThumbMap.put(questionSubmitQuestionSubmitThumb.getQuestionSubmitId(), true));
            // 获取收藏
            QueryWrapper<QuestionSubmitFavour> questionSubmitFavourQueryWrapper = new QueryWrapper<>();
            questionSubmitFavourQueryWrapper.in("questionSubmitId", questionSubmitIdSet);
            questionSubmitFavourQueryWrapper.eq("userId", loginUser.getId());
            List<QuestionSubmitFavour> questionSubmitFavourList = questionSubmitFavourMapper.selectList(questionSubmitFavourQueryWrapper);
            questionSubmitFavourList.forEach(questionSubmitFavour -> questionSubmitIdHasFavourMap.put(questionSubmitFavour.getQuestionSubmitId(), true));
        }
        // 填充信息
        questionSubmitVOList.forEach(questionSubmitVO -> {
            Long userId = questionSubmitVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionSubmitVO.setUser(userService.getUserVO(user));
            questionSubmitVO.setHasThumb(questionSubmitIdHasThumbMap.getOrDefault(questionSubmitVO.getId(), false));
            questionSubmitVO.setHasFavour(questionSubmitIdHasFavourMap.getOrDefault(questionSubmitVO.getId(), false));
        });
        // endregion

        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}
