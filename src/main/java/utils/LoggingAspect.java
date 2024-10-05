package utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class LoggingAspect {

    // Pointcut to capture method executions in all classes extending BasePage
//    @Pointcut("execution(* *(..)) && within(m.BasePage+)") // Matches all methods within BasePage and its subclasses
//    public void inBasePageMethods() {}

    @Pointcut("execution(* *(..))")
    public void inBasePageMethods() {
        //pointcut body, should be empty
    }

    // Advice to log method entry
    @Before("inBasePageMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Entering method " + methodName + " in class " + className);
    }

    // Advice to log method exit
    @AfterReturning(pointcut = "inBasePageMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Exiting method " + methodName + " in class " + className);
    }

    // Advice to log exceptions
    @AfterThrowing(pointcut = "inBasePageMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("Exception in method " + methodName + " in class " + className + ": " + exception.getMessage());
    }
}
