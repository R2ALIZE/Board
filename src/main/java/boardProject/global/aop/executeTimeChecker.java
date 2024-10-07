package boardProject.global.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // AOP 구현 클래스 명시
@Component // 클래스를 bean으로 등록
@Slf4j
public class executeTimeChecker {


    @Around("execution(* boardProject..*(..))")
    public Object executionTimeCheck (ProceedingJoinPoint pjp) throws Throwable {

        String className = pjp.getTarget().getClass().getName();

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
