package com.algaworks.algafood.config.oap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final ThreadLocal<Boolean> isFirstCall = ThreadLocal.withInitial(() -> Boolean.TRUE);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* com.algaworks.algafood..*(..))")
    public void loggableMethods() {
    }

    @Around("loggableMethods()")
    public Object aroundMethod(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        if (Boolean.TRUE.equals(isFirstCall.get())) {
            boolean logCompleted = false;
            isFirstCall.set(Boolean.FALSE);
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                logEachData();
                logCompleted = true;
                throw ex;
            } finally {
                if (!logCompleted) {
                    logEachData();
                }
                LogContext.clear();
                isFirstCall.remove();
            }
        } else {
            return joinPoint.proceed();
        }
    }

    private void logEachData() {
        for (Object obj : LogContext.getAll()) {
            try {
                String json = objectMapper.writeValueAsString(obj);
                logger.info(json);
            } catch (JsonProcessingException ex) {
                logger.error("Erro ao serializar objeto: {}", obj, ex);
            }
        }
    }
}