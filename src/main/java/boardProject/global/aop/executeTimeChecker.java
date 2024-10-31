package boardProject.global.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class executeTimeChecker {


    @Value("${aop.enable}")
    private boolean isEnable;




    @Pointcut("execution(* boardProject..*(..))")
    public void targetAllMethods() {}



    @Around("targetAllMethods()")
    public Object executionTimeCheck (ProceedingJoinPoint pjp) throws Throwable {


        if (!isEnable) {
            return pjp.proceed();
        }



        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        StopWatch stopWatch = new StopWatch();
        long elapsedTime;
        Object retValue = null;


        try {
            stopWatch.start();
            retValue = pjp.proceed();

        } finally {
            stopWatch.stop();
            elapsedTime = stopWatch.getTotalTimeMillis();

            if (elapsedTime < 500) {
                log.info(className +  "(" + methodName + ")" + ", elapsedTime : " + elapsedTime + " ms");
            }

            if (elapsedTime >= 500 && elapsedTime <= 1000) {
                log.warn(className +  "(" + methodName + ")" + ", elapsedTime : " + elapsedTime + " ms");;
            }

            if (elapsedTime > 1000) {
                log.error(className +  "(" + methodName + ")" + ", elapsedTime : " + elapsedTime + " ms");;
            }

        }

        return retValue;
    }


}
