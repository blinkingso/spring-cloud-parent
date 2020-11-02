package com.yz.filters.validation;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author andrew
 * @date 2020-11-01
 */
public class DefaultValidationHeadersStrategy implements ValidateHeadersStrategy {

    private final Map<String, String> cache;

    public DefaultValidationHeadersStrategy(Map<String, String> cache) {
        this.cache = cache;
    }

    @Override
    public boolean validate(Map<String, String> headers) {
        if (!cache.keySet().containsAll(headers.keySet())) {
            return false;
        }

        var legalSize = (int) headers.entrySet().stream().filter(it -> this.cache.get(it.getKey()).equals(it.getValue())).count();

        return legalSize == headers.size();
    }
}
