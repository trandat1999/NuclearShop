package com.tranhuudat.nuclearshop.util.validator;

import com.tranhuudat.nuclearshop.service.UserService;
import com.tranhuudat.nuclearshop.util.anotation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    UserService userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.checkExistEmail(s);
    }
}
