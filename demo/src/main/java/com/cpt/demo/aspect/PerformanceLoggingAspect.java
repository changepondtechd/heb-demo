package com.cpt.demo.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceLoggingAspect.class);

    @Around("@within(com.cpt.demo.aspect.EnablePerfLog) || @annotation(com.cpt.demo.aspect.EnablePerfLog)")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.nanoTime() - startTime;
            LOGGER.info("Performance log: {} executed successfully in {} ms with args {}",
                    methodName, elapsedTime / 1_000_000.0, Arrays.toString(args));
            return result;
        } catch (Throwable ex) {
            long elapsedTime = System.nanoTime() - startTime;
            LOGGER.warn("Performance log: {} failed after {} ms with args {}",
                    methodName, elapsedTime / 1_000_000.0, Arrays.toString(args), ex);
            throw ex;
        }
    }
}
