package kz.iitu.springlab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    private static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint pjp, RetryOnFailure retry) throws Throwable {
        int maxAttempts = retry.attempts();
        Throwable lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return pjp.proceed();
            } catch (Throwable ex) {
                lastException = ex;
                log.warn("[RETRY] Попытка {} из {} завершилась ошибкой: {}",
                        attempt, maxAttempts, ex.getMessage());
            }
        }
        throw lastException;
    }
}