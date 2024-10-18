package boardProject.global.aop;

import boardProject.global.validation.ResidentNumFormatCheck;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResidentNumFormatCheck.class)
public @interface ResidentNumFormat {

    String message() default "주민등록번호 형식에 맞지 않습니다.";

}
