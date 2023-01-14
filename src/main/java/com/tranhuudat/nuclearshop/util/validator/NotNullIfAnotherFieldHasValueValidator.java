package com.tranhuudat.nuclearshop.util.validator;

import com.tranhuudat.nuclearshop.type.OrderImportStatus;
import com.tranhuudat.nuclearshop.util.anotation.NotNullIfAnotherFieldHasValue;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * @author DatNuclear on 12/01/2023
 * @project NuclearShop
 */
public class NotNullIfAnotherFieldHasValueValidator implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

    private String fieldName;
    private String expectedFieldValue;
    private String dependFieldName;

    @Override
    public void initialize(NotNullIfAnotherFieldHasValue annotation) {
        fieldName          = annotation.fieldName();
        expectedFieldValue = annotation.fieldValue();
        dependFieldName    = annotation.dependFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (value == null) {
            return true;
        }
        try {
            Object fieldValue       = BeanUtils.getProperty(value, fieldName);
            Object dependFieldValue = BeanUtils.getProperty(value, dependFieldName);
            if(expectedFieldValue== null && fieldValue!=null && dependFieldValue == null){
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addNode(dependFieldName)
                        .addConstraintViolation();
                return false;
            }
            if (expectedFieldValue!=null && expectedFieldValue.equalsIgnoreCase(fieldValue.toString()) && dependFieldValue == null) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addNode(dependFieldName)
                        .addConstraintViolation();
                return false;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

}
