package com.intuit.craft.onlinecars.utils;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class CollectionUtils {

    public boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public boolean isEmpty(Map<?, ?> map) {
        return Objects.isNull(map) || map.isEmpty();
    }
}
