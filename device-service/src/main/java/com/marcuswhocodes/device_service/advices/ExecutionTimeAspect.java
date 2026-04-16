package com.marcuswhocodes.device_service.advices;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {


    @Pointcut("execution(* com.marcuswhocodes.device_service.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.nanoTime();
            long elapsedNS = end - start;
            long elapsedMs = TimeUnit.MILLISECONDS.convert(elapsedNS, TimeUnit.NANOSECONDS);
            String signature = joinPoint.getSignature().toShortString();
            log.info("Controller method {} executed in {} ms", signature, elapsedMs);
        }
    }
}
