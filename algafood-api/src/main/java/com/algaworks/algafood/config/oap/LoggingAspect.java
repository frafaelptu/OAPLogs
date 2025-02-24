package com.algaworks.algafood.config.oap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;


@Aspect
@Component
//@Bean
//@Conditional(MyCondition.class)
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final ThreadLocal<Boolean> isFirstCall = ThreadLocal.withInitial(() -> Boolean.TRUE);

    @Pointcut("execution(* com.algaworks.algafood..*(..))")
    public void loggableMethods() {
    }

    @Around("loggableMethods()")
    public Object aroundMethod(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        if (Boolean.TRUE.equals(isFirstCall.get())) {
            isFirstCall.set(Boolean.FALSE);
            LogTrackingDTO logTracking = new LogTrackingDTO();
            logTracking.setStartTime(String.valueOf(System.currentTimeMillis()));
            logTracking.setMethod(joinPoint.getSignature().getName());
            logTracking.setClassName(joinPoint.getSignature().getDeclaringTypeName());
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                logDuration(logTracking, ex);
                logDone(logTracking,LogLevel.ERROR, ex);
                throw ex;
            } finally {
                if (!logTracking.isLogCompleted()) {
                    logDuration(logTracking, null);
                    logDone(logTracking, LogLevel.SUCCESS, null);
                }
                LogContext.clear();
                isFirstCall.remove();
            }
        } else {
            return joinPoint.proceed();
        }
    }

    private void logDuration(LogTrackingDTO logTracking, Throwable ex) {
        if (ex != null) {
            LogContext.put("error", ex.getMessage());
        }
        logTracking.setEndTime(String.valueOf(System.currentTimeMillis()));
        logTracking.setDuration(String.valueOf(Long.parseLong(logTracking.getEndTime()) - Long.parseLong(logTracking.getStartTime())));
    }

    private void logDone(LogTrackingDTO logTracking, LogLevel logLevel, Throwable ex) {
        logTracking.setLogCompleted(true);

        if (LogContext.getAll().isEmpty()){
            return;
        }

        logTracking.setContext(LogContext.getAll());
        if (LogLevel.ERROR.equals(logLevel)) {
            logger.error("{}", getObjectToJson(logTracking), ex);
        } else {
            logger.info("{}", getObjectToJson(logTracking));
        }
    }

    private String getObjectToJson(LogTrackingDTO logTracking) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(logTracking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}