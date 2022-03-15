package com.shivam.employee.advice;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Aspect
@Log4j2
@Component
public class LoggableAnnotationProcessor {

    @Around("@annotation(com.shivam.employee.advice.Loggable)")
    public Object evaluate(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            final Object result = joinPoint.proceed();
            log.info("Result from joinPoint.proceed()  {{}}", result);
            return result;
        } finally {
            final String className = method.getDeclaringClass().getName();
            final String methodName = method.getName();
            stopWatch.stop();
            log.info(String
                    .format("Executed method {%s#%s} took {%s} ms", className, methodName, stopWatch.getLastTaskTimeMillis()));
        }
    }
}
