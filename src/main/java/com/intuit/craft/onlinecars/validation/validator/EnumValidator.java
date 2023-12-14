package com.intuit.craft.onlinecars.validation.validator;

import com.intuit.craft.onlinecars.validation.annotation.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {

    List<String> valueList = null;

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        return value != null && valueList.contains(value.toString().toUpperCase());
    }

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum> enumClass = constraintAnnotation.enumClass();
        Enum[] enumValArr = enumClass.getEnumConstants();
        for (Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}
