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
