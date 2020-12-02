package com.ift604.bingo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {

    public static <T> List<T> reverse(final List<T> list) {
        final List<T> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
