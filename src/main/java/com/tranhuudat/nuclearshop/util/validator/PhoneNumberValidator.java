package com.tranhuudat.nuclearshop.util.validator;

import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.anotation.PhoneNumber;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        if (StringUtils.hasText(value)) {
            return Pattern.compile(ConstUtil.REGEX_PHONE_NUMBER).matcher(value).matches();
        }
        return false;
    }
}
