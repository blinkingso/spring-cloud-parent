package com.yz.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author andrew
 * @date 2020-10-31
 */
public enum AuthoritiesDefinition {

    WRITE, READ, DELETE, QUERY;

    public static String[] allToString() {
        return toString(values());
    }

    public static String[] toString(AuthoritiesDefinition... authorities) {
        if (authorities == null || authorities.length == 0) {
            return new String[]{};
        }

        String[] arr = new String[authorities.length];
        Arrays.stream(authorities).map(AuthoritiesDefinition::name).collect(Collectors.toList())
                .toArray(arr);
        return arr;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
