package com.tatakai.parrotojbackenduserservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tatakai.parrotojbackendmodel.domain.User;
import com.tatakai.parrotojbackendmodel.vo.user.LoginUserVO;
import com.tatakai.parrotojbackendmodel.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 30215
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-09-05 23:11:43
*/
public interface UserService extends IService<User> {
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);
    Long userRegister(String userAccount,String userPassword,String checkPassword);
    User getLoginUser(HttpServletRequest request);
    Boolean userLogout(HttpServletRequest request);

    UserVO getUserVO(User user);

    boolean isAdmin(User loginUser);

    boolean addUserSignIn(Long userId);

    /**
     * 获取用户某一年的签到记录
     * @param user 用户
     * @param year 年份
     * @return 签到天数列表
     */

    List<Integer> getUserSignIn(User user, int year);
}
