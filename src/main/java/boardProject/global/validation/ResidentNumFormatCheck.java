package boardProject.global.validation;

import boardProject.global.aop.ResidentNumFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResidentNumFormatCheck implements ConstraintValidator<ResidentNumFormat,String> {


    private final int TOTAL_LENGTH = 13;
    private final int BIRTH_PART_LENGTH = 6;
    private final int GENDER_PART_LENGTH = 7;
    private final int END_OF_MONTH = 12;
    private final int END_OF_DAY = 31;



    /** dto에서는 주민번호는 - 제외 13자리로 받음 **/


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return checkLength(value) && checkBirthPart(value) && checkGenderPart(value);
    }


    public boolean checkLength (String value) {
        return value.length() == TOTAL_LENGTH;
    }



    public boolean checkBirthPart (String value) {

        String birthPart = value.substring(0,6);

        boolean isValid = true;


        if (birthPart.length() != BIRTH_PART_LENGTH) {
            return false;
        }

        // 주민등록번호 상 월에 해당되는 2자리가 12보다 클 때
        if (Integer.valueOf(birthPart.substring(2,4)) > END_OF_MONTH) {
            return false;
        }

        // 주민등록번호 상 일에 해당되는 2자리가 31보다 클 때
        if (Integer.valueOf(birthPart.substring(4,6)) > END_OF_DAY) {
            return false;
        }

        return isValid;
    }


    public boolean checkGenderPart (String value) {


        String genderPart = value.substring(6,value.length());


        boolean isValid = true;


        if (genderPart.length() != GENDER_PART_LENGTH) {
            return false;
        }

        // 뒷자리 1번째 번호가 4보다 클 때 (현재 4까지만 사용되고 있음)
        if (Integer.valueOf(genderPart.substring(0,1)) > 4) {
            return false;
        }


        return isValid;

    }

}
