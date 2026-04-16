package com.marcuswhocodes.device_service.advices;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.marcuswhocodes.device_service.service.impl.*.*(..))")
    public void serviceMethods() {}


    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info(
                "Called service method: {} with arguments: {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs()
        );
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Called service method: {} with result: {}", joinPoint.getSignature().getName(), result);
    }
}
