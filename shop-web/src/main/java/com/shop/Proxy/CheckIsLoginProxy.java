package com.shop.Proxy;

import com.shop.annotation.IsLogin;
import com.shop.core.base.exception.ParamException;
import com.shop.core.util.LoginIdentityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Component
@Aspect
public class CheckIsLoginProxy {

    @Pointcut("execution(* com.shop.controller.*.*(..))")
    public void pointcut(){

    }

    @Around(value = "pointcut()&&@annotation(isLogin)")
    public Object hanlerMethod (ProceedingJoinPoint pjp, IsLogin isLogin) throws Throwable {
        if(isLogin==null){
            return pjp.proceed();
        }
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Integer loginId = LoginIdentityUtil.getFromLoginId(request);

        if(loginId==null){
            throw new ParamException(-1,"用户未登录！");
        }
        Object result=pjp.proceed();
        return result;
    }

}
