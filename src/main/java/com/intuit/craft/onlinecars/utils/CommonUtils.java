package com.intuit.craft.onlinecars.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@UtilityClass
@Slf4j
public class CommonUtils {

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if ((object1 == null) || (object2 == null)) {
            return false;
        }
        return object1.equals(object2);
    }

    public boolean notEqual(Object object1, Object object2) {
        return !equals(object1, object2);
    }

    public String getValueOrEmptyString(Object object) {
        if (Objects.nonNull(object)) {
            return object.toString();
        }
        return "";
    }
}
