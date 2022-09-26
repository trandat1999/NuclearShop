package com.tranhuudat.nuclearshop.util.validator;

import com.tranhuudat.nuclearshop.util.anotation.BirthDate;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDate, Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (!ObjectUtils.isEmpty(value)) {
            return value.before(new Date());
        }
        return false;
    }
}
