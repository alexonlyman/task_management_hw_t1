package alexgr.task_management_hw_t1.aspect;

import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@annotation(alexgr.task_management_hw_t1.annotation.Logged)")
    public void loggingMethodByCustomAnnotation() {
    }

    @Before("loggingMethodByCustomAnnotation()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("вызывается метод " + joinPoint.getSignature().getName() + " с аргументами "
                + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "loggingMethodByCustomAnnotation()", returning = "result")
    public void loggindAfterReturningResult(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().getName();
        System.out.println("метод " + method + " успешно выполнен с результатом " + result);
    }

    @AfterThrowing(pointcut = "loggingMethodByCustomAnnotation()", throwing = "exception")
    public void loggingAfterThrowingExc(JoinPoint joinPoint, TaskNotFoundException exception) {
        String method = joinPoint.getSignature().getName();
        System.err.println("Системный лог: Исключение в методе '" + method + "': " + exception.getMessage());
    }

    @Around("loggingMethodByCustomAnnotation()")
    public Object leadTimeProceeding(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Метод '" + proceedingJoinPoint.getSignature().getName() + "' выполнен за " + (endTime - startTime) + " мс");
        return result;
    }

}

