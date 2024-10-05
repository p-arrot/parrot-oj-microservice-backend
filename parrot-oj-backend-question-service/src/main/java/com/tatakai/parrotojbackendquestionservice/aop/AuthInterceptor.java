package com.tatakai.parrotojbackendquestionservice.aop;

import com.tatakai.parrotojbackendcommon.annotation.AuthCheck;
import com.tatakai.parrotojbackendcommon.enums.AuthEnum;
import com.tatakai.parrotojbackendcommon.exception.BusinessException;
import com.tatakai.parrotojbackendcommon.result.ErrorCode;
import com.tatakai.parrotojbackendmodel.domain.User;
import com.tatakai.parrotojbackendserviceclient.UserFeignClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserFeignClient userFeignClient;

    @Around("@annotation(authCheck)")
    public Object authCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        AuthEnum mustRole = authCheck.mustRole();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        User loginUser = userFeignClient.getLoginUser(request);
        if (mustRole == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        if (mustRole == AuthEnum.USER) {
            if (loginUser == null || Objects.equals(loginUser.getUserRole(), AuthEnum.NOT_LOGIN.getValue()))
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);

        }
        if (mustRole == AuthEnum.ADMIN) {
            if (!Objects.equals(loginUser.getUserRole(), AuthEnum.ADMIN.getValue()))
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return joinPoint.proceed();
    }
}
