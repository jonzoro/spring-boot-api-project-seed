package com.company.project.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspectService {

    /***
     * 数据插入切点
     */
    @Pointcut("execution(* com.company.project.core.*.save*(..))")
    public void insertPointcut(){}

    @AfterReturning("insertPointcut()")
    public void insertLogHandler(JoinPoint point) {
        Object nowObject = point.getThis();

        System.out.println("=====================You had save sth....=====================");
        System.out.println("Handler service is :" + nowObject.toString());
    }
}
