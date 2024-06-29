/**
 * The Initial Developer of some parts of this file, which are copied from, derived from, or
 * inspired by Cognite Java SDK for CDF, is Cognite AS (http://www.cognite.com/).
 * Copyright 2022 Cogntite AS. All Rights Reserved.
 */
package com.kinnovatio.utils;

public class Validate {
    static final String DEFAULT_ERROR_MESSAGE = "Failed precondition validation.";

    static void isTrue(boolean condition) {
        Validate.isTrue(condition, DEFAULT_ERROR_MESSAGE);
    }

    static void isTrue(boolean condition, String errorMessage) {
        if (!condition) throw new IllegalStateException(errorMessage);
    }
}
