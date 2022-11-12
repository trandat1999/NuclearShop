package com.tranhuudat.nuclearshop.util.anotation;

import com.tranhuudat.nuclearshop.util.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {

    String message() default "{nuclearshop.validation.EmailExist.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
