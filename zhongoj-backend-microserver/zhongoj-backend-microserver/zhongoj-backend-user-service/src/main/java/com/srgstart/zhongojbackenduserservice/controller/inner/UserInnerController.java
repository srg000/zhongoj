package com.srgstart.zhongojbackenduserservice.controller.inner;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.srgstart.zhongojbackendcommon.annotation.AuthCheck;
import com.srgstart.zhongojbackendcommon.common.BaseResponse;
import com.srgstart.zhongojbackendcommon.common.DeleteRequest;
import com.srgstart.zhongojbackendcommon.common.ErrorCode;
import com.srgstart.zhongojbackendcommon.common.ResultUtils;
import com.srgstart.zhongojbackendcommon.constant.UserConstant;
import com.srgstart.zhongojbackendcommon.exception.BusinessException;
import com.srgstart.zhongojbackendcommon.exception.ThrowUtils;
import com.srgstart.zhongojbackendmodel.model.dto.user.*;
import com.srgstart.zhongojbackendmodel.model.entity.User;
import com.srgstart.zhongojbackendmodel.model.vo.LoginUserVO;
import com.srgstart.zhongojbackendmodel.model.vo.UserVO;
import com.srgstart.zhongojbackendserviceclient.service.UserFeignClient;
import com.srgstart.zhongojbackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.srgstart.zhongojbackenduserservice.service.impl.UserServiceImpl.SALT;


/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * 根据 id 获取用户信息
     *
     * @param userId 用户 id
     * @return 用户信息
     */
    @GetMapping("/get/id")
    @Override
    public User getById(Long userId) {
        return userService.getById(userId);
    }

    /**
     * 根据 id列表 获取用户列表
     *
     * @param idList 用户 id列表
     * @return
     */
    @GetMapping("/list/idList")
    @Override
    public List<User> listByIds(Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
