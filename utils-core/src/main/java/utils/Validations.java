package utils;

public class Validations {
    static final String DEFAULT_ERROR_MESSAGE = "Failed precondition validation.";

    static void isTrue(boolean condition) {
        Validations.isTrue(condition, DEFAULT_ERROR_MESSAGE);
    }

    static void isTrue(boolean condition, String errorMessage) {
        if (!condition) throw new IllegalStateException(errorMessage);
    }
}
