package com.codestates.mainproject.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSpaceIntegerValidator implements ConstraintValidator<NotSpaceInteger, Integer> {

    @Override
    public void initialize(NotSpaceInteger constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value>=0;
    }
}
