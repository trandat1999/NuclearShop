package com.tranhuudat.nuclearshop.util.anotation;

import com.tranhuudat.nuclearshop.util.validator.UniqueEmailValidator;
import com.tranhuudat.nuclearshop.util.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueUsername {

    String message() default "{nuclearshop.validation.UsernameExist.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
