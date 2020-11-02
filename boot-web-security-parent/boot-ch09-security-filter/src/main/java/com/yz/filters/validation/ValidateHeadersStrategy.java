package com.yz.filters.validation;

import java.util.Map;

/**
 *  HTTP HEADERS VALIDATION STRATEGY
 *
 * @author andrew
 * @date 2020-11-01
 */
public interface ValidateHeadersStrategy {

    // check headers
    boolean validate(Map<String, String> headers);
}
