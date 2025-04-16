package com.srgstart.zhongojbackendserviceclient.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.zhongojbackendcommon.common.ErrorCode;
import com.srgstart.zhongojbackendcommon.exception.BusinessException;
import com.srgstart.zhongojbackendmodel.model.entity.User;
import com.srgstart.zhongojbackendmodel.model.enums.UserRoleEnum;
import com.srgstart.zhongojbackendmodel.model.vo.UserVO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.srgstart.zhongojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 * @author Administrator
 */
@FeignClient(name = "zhongoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {


    /**
     * 根据 id 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") Long userId);

    /**
     * 根据 id列表 获取用户列表
     *
     * @param idList 用户 id列表
     * @return
     */
    @GetMapping("/list/idList")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }


    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }


    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
