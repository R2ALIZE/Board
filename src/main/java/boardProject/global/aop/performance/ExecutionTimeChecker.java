package boardProject.global.aop.performance;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static boardProject.global.logging.LogMarkerFactory.PERFORMANCE;

@Slf4j
@Aspect
@Component
public class ExecutionTimeChecker {


    /** flag **/

    @Value("${aop.timechecker.enable}")
    private boolean isEnable;


    /** Pointcut **/

    @Pointcut("execution(* *..*(..))")
    public void allMethods() {}


    @Pointcut("execution(* boardProject.global.exception.GlobalExceptionHandler *(..))")
    public void exceptionHandler() {}


    @Pointcut("execution(* *..*Controller.*(..)")
    public void controllerMethods() {}



    @Around("allMethods()")
    public Object measureMethod (ProceedingJoinPoint pjp) throws Throwable {


        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        StopWatch methodStopWatch = new StopWatch("METHOD");
        long elapsedTime;
        Object retValue = null;


        if (!isEnable) {
            return pjp.proceed();
        }

        try {
            methodStopWatch.start();
            retValue = pjp.proceed();

        } finally {
            methodStopWatch.stop();
            elapsedTime = methodStopWatch.getTotalTimeMillis();

            logBasedOnTime(className,methodName,elapsedTime);

        }

        return retValue;
    }

    public void logBasedOnTime (String className, String methodName, long elapsedTime) {

        String message
                = String.format("%s (%s) : %d ms", className, methodName, elapsedTime);

        if (elapsedTime < 500) {
            log.info(PERFORMANCE,message);
        }

        if (elapsedTime >= 500 && elapsedTime <= 1000) {
            log.debug(PERFORMANCE,message);
        }

        if (elapsedTime > 1000) {
            log.error(PERFORMANCE,message);
        }
    }
}
