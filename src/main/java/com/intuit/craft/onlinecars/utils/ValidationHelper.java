package com.intuit.craft.onlinecars.utils;
import com.intuit.craft.onlinecars.exception.ExceptionCode;
import com.intuit.craft.onlinecars.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class ValidationHelper {

    public void isNull(Object object, ExceptionCode code, String errorMessage) {
        isTrue(Objects.isNull(object), code, errorMessage);
    }

    public void notNull(Object object, ExceptionCode code, String errorMessage) {
        isTrue(Objects.nonNull(object), code, errorMessage);
    }

    public void isEmpty(Collection<?> collection, ExceptionCode code, String errorMessage) {
        isTrue(CollectionUtils.isEmpty(collection), code, errorMessage);
    }

    public void notEmpty(Collection<?> collection, ExceptionCode code, String errorMessage) {
        isTrue(!CollectionUtils.isEmpty(collection), code, errorMessage);
    }

    public void isEmpty(Map<?, ?> map, ExceptionCode code, String errorMessage) {
        isTrue(CollectionUtils.isEmpty(map), code, errorMessage);
    }

    public void notEmpty(Map<?, ?> map, ExceptionCode code, String errorMessage) {
        isTrue(!CollectionUtils.isEmpty(map), code, errorMessage);
    }

    public void notEmptyOrBlank(String value, ExceptionCode code, String errorMessage) {
        if (CommonUtils.isEmpty(value) || CommonUtils.isEmpty(value.trim())) {
            throw new ValidationException(code, errorMessage);
        }
    }

    public void isTrue(boolean value, ExceptionCode code, String errorMessage) {
        if (!value) {
            throw new ValidationException(code, errorMessage);
        }
    }

    public void isNotZero(Long value, ExceptionCode code, String errorMessage) {
        if (value == null || value == 0L) {
            throw new ValidationException(code, errorMessage);
        }
    }

    public void isNotZero(Integer value, ExceptionCode code, String errorMessage) {
        if (value == null || value == 0) {
            throw new ValidationException(code, errorMessage);
        }
    }

    public void notEmptyOrNull(String value, String errorMessage) {
        if (CommonUtils.isEmpty(value)) {
            throw new ValidationException(errorMessage);
        }
    }

    public void isTrue(boolean value, String errorMessage) {
        if (!value) {
            throw new ValidationException(errorMessage);
        }
    }

}
