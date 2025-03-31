package alexgr.task_management_hw_t1.aspect;

import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(alexgr.task_management_hw_t1.annotation.Logged)")
    public void loggingMethodByCustomAnnotation() {
    }

    @Before("loggingMethodByCustomAnnotation()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("вызывается метод " + joinPoint.getSignature().getName() + " с аргументами " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "loggingMethodByCustomAnnotation()", returning = "result")
    public void loggindAfterReturningResult(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().getName();
        log.info("метод " + method + " успешно выполнен с результатом " + result);
    }

    @AfterThrowing(pointcut = "loggingMethodByCustomAnnotation()", throwing = "exception")
    public void loggingAfterThrowingExc(JoinPoint joinPoint, TaskNotFoundException exception) {
        String method = joinPoint.getSignature().getName();
        log.error("Исключение в методе '{}': {}", method, exception.getMessage());
    }

    @Around("loggingMethodByCustomAnnotation()")
    public Object leadTimeProceeding(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception ex) {
            log.error("Ошибка в методе '{}.{}': {} - {}",
                    proceedingJoinPoint.getTarget().getClass().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(),
                    ex.getClass().getSimpleName(),
                    ex.getMessage());
            throw ex;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Метод '{}.{}' выполнен за {} мс",
                    proceedingJoinPoint.getTarget().getClass().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(),
                    endTime - startTime);
        }
    }

}

