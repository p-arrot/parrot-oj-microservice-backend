package com.tatakai.parrotojbackenduserservice.controller;

import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.BaseResponse;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendcommon.result.ResultUtil;
import com.tatakai.parrotojbackendmodel.domain.User;
import com.tatakai.parrotojbackendmodel.dto.user.UserLoginRequest;
import com.tatakai.parrotojbackendmodel.dto.user.UserRegisterRequest;
import com.tatakai.parrotojbackendmodel.vo.user.LoginUserVO;
import com.tatakai.parrotojbackenduserservice.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLogin, HttpServletRequest request) {
        if (request == null || userLogin == null || StringUtils.isAnyBlank(userLogin.getUserAccount(), userLogin.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLogin.getUserAccount();
        String userPassword = userLogin.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtil.success(loginUserVO);
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegister) {
        if (userRegister == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegister.getUserAccount();
        String userPassword = userRegister.getUserPassword();
        String checkPassword = userRegister.getCheckPassword();
        Long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtil.success(id);
    }

    @GetMapping("/getLoginUser")
    public BaseResponse<User> getLoginUser(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(1000);
        if (request == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        return ResultUtil.success(userService.getLoginUser(request));
    }


    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        return ResultUtil.success(userService.userLogout(request));
    }

    @PostMapping("/sign_in")
    public BaseResponse<Boolean> addUserSignIn(HttpServletRequest request) {
        if (request == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        return ResultUtil.success(userService.addUserSignIn(loginUser.getId()));
    }

    @PostMapping("/get_sign_in")
    public BaseResponse<List<Integer>> getUserSignIn(HttpServletRequest request, int year) {
        if (request == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        return ResultUtil.success(userService.getUserSignIn(loginUser, year));
    }
}
