package com.tranhuudat.nuclearshop.util.component;

import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class LogUsernameAOP {
    @Around("@annotation(com.tranhuudat.nuclearshop.util.anotation.LogUsername)")
    public Object logUsername(ProceedingJoinPoint call) throws Throwable {
        Object[] args = call.getArgs();
        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        StringBuilder params = new StringBuilder();
        assert args.length == parameterAnnotations.length;
        for(int i = 0; i < args.length; i++){
            for (Annotation annotation : parameterAnnotations[i]) {
                if (!(annotation instanceof PathVariable)){
                    continue;
                }
                PathVariable pathVariable = (PathVariable) annotation;
                params.append(pathVariable.value()).append(ConstUtil.EQUAL).append(ConstUtil.SPACE).append(args[i]);
            }
        }
        LogUsername logUsername = method.getAnnotation(LogUsername.class);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(logUsername.m() +ConstUtil.SPACE + params +" by username: {}",username);
        return call.proceed();
    }
}
