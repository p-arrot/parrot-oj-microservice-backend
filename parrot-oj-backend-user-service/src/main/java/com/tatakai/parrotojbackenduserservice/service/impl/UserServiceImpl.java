package com.tatakai.parrotojbackenduserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tatakai.parrotojbackendcommon.enums.AuthEnum;
import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendcommon.utils.ThrowUtil;
import com.tatakai.parrotojbackendmodel.domain.User;
import com.tatakai.parrotojbackendmodel.vo.user.LoginUserVO;
import com.tatakai.parrotojbackendmodel.vo.user.UserVO;
import com.tatakai.parrotojbackenduserservice.mapper.UserMapper;
import com.tatakai.parrotojbackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBitSet;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 30215
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-09-05 23:11:
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private RedissonClient redissonClient;
    private final String SALT = "CSL";
    private final String LOGIN_USER = "login_user";

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes()));
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("用户名或密码错误，登录失败");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误！");
        }
        request.getSession().setAttribute(LOGIN_USER, user);
        return getLoginUserVoByUser(user);
    }

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验用户名和密码的有效性
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码不得为空！");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致！");
        }
        if (userPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码至少四位！");
        }
        if ("未登录".equals(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法用户名！");
        }
        // 2. 判断用户名是否已存在
        Long account = this.baseMapper.selectCount(new QueryWrapper<User>().eq("userAccount", userAccount));
        if (account > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在！");
        }

        // 3. 添加用户(分布式锁)
        RLock lock = redissonClient.getLock("user:register:" + userAccount);
        boolean flag = false;
        try {
            flag = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!flag) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册用户失败！");
            }
            String passwordInDb = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(passwordInDb);
            user.setUserName(userAccount);
            boolean save = save(user);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册用户失败！");
            }
            return user.getId();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (flag) lock.unlock();
        }


    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(LOGIN_USER);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(LOGIN_USER);
        if (user == null) throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录！");
        request.getSession().removeAttribute(LOGIN_USER);
        return true;
    }

    @Override
    public UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public boolean isAdmin(User loginUser) {
        return AuthEnum.ADMIN.getValue().equals(loginUser.getUserRole());
    }

    @Override
    public boolean addUserSignIn(Long userId) {
        ThrowUtil.throwIf(userId == null, ErrorCode.NOT_LOGIN_ERROR);
        User user = this.getById(userId);
        ThrowUtil.throwIf(user == null, ErrorCode.NOT_LOGIN_ERROR);
        int year = LocalDate.now().getYear();
        String key = String.format("user:signIn:%d:%d", year, userId);
        int offset = LocalDate.now().getDayOfYear();
        RBitSet bitSet = redissonClient.getBitSet(key);
        if (!bitSet.get(offset)) {
            bitSet.set(offset, true);
        }
        return true;
    }

    @Override
    public List<Integer> getUserSignIn(User user, int year) {
        ThrowUtil.throwIf(user == null, ErrorCode.NOT_LOGIN_ERROR);
        long userId = user.getId();
        String key = String.format("user:signIn:%d:%d", year, userId);
        RBitSet rBitSet = redissonClient.getBitSet(key);
        BitSet bitSet = rBitSet.asBitSet();

        List<Integer> dayList = new ArrayList<>();
        int index = bitSet.nextSetBit(0);
        while (index != -1) {
            dayList.add(index);
            index = bitSet.nextSetBit(index + 1);
        }

        return dayList;
    }


    public LoginUserVO getLoginUserVoByUser(User user) {
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

}




