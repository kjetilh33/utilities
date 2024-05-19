/**
 * The Initial Developer of some parts of this file, which are copied from, derived from, or
 * inspired by Cognite Java SDK for CDF, is Cognite AS (http://www.cognite.com/).
 * Copyright 2022 Cogntite AS. All Rights Reserved.
 *
 * The original code has been changed to use Objects.requireNonNull() instead of the Google Preconditions
 * library.
 */
package com.kinnovatio.utils;

import com.google.protobuf.Value;

import java.util.Objects;

/**
 * This class hosts methods for parsing {@code Value} objects to various target types (String, double, etc.).
 *
 * This can be helpful in particular when working with CDF.Raw and Json (parsed to {@code Struct}).
 */
public class ParseValue {

    /**
     * Tries to parse a {@code Value} to a {@code String} representation.
     *
     * @param rawValue the value to parse
     * @return the string representation
     */
    public static String parseString(Value rawValue) {
        Objects.requireNonNull(rawValue, "rawValue cannot be null");
        switch (rawValue.getKindCase()) {
            case STRING_VALUE:
                return rawValue.getStringValue().trim();
            case NUMBER_VALUE:
                return String.valueOf(rawValue.getNumberValue());
            case BOOL_VALUE:
                return String.valueOf(rawValue.getBoolValue());
            case LIST_VALUE:
                return rawValue.getListValue().toString();
            case STRUCT_VALUE:
                return rawValue.getStructValue().toString();
            case NULL_VALUE:
                return "null";
            default: // KIND_NOT_SET
                return "";
        }
    }

    /**
     * Tries to parse a {@code Value} to a {@code double}. If the Value has a numeric or string representation the parsing
     * will succeed as long as the {@code Value} is within the Double range.
     *
     * Will throw a {@code NumberFormatException} if parsing is unsuccessful.
     * @param rawValue the value to parse
     * @return the double representation
     * @throws NumberFormatException
     */
    public static double parseDouble(Value rawValue) throws NumberFormatException {
        Objects.requireNonNull(rawValue, "rawValue cannot be null");
        double returnDouble;
        if (rawValue.hasNumberValue()) {
            returnDouble = rawValue.getNumberValue();
        } else if (rawValue.hasStringValue()) {
            returnDouble = Double.parseDouble(rawValue.getStringValue());
        } else {
            throw new NumberFormatException("Unable to parse to double. "
                    + "Identified value type: " + rawValue.getKindCase()
                    + " Property value: " + rawValue.toString());
        }
        return returnDouble;
    }

    /**
     * Tries to parse a {@code Value} to a {@code long}. If the Value has a numeric or string representation the parsing
     * will succeed as long as the {@code Value} is within the long range.
     *
     * Will throw a {@code NumberFormatException} if parsing is unsuccessful.
     * @param rawValue the value to parse
     * @return the long representation
     * @throws NumberFormatException
     */
    public static long parseLong(Value rawValue) throws NumberFormatException {
        Objects.requireNonNull(rawValue, "rawValue cannot be null");
        long returnLong;
        if (rawValue.hasNumberValue()) {
            returnLong = Math.round(rawValue.getNumberValue());
        } else if (rawValue.hasStringValue()) {
            returnLong = Long.parseLong(rawValue.getStringValue());
        } else {
            throw new NumberFormatException("Unable to parse to long. "
                    + "Identified value type: " + rawValue.getKindCase()
                    + " Property value: " + rawValue.toString());
        }
        return returnLong;
    }

    /**
     * Tries to parse a {@code Value} to a {@code Boolean}. If the Value has a boolean, numeric or string representation
     * the parsing will succeed.
     *
     * A bool {@code Value} representation is parsed directly.
     * A String {@code Value} representation returns true if the string argument is not null and equal to, ignoring case, the
     * string "true".
     * A numeric {@code Value} representation returns true if the number equals "1".
     *
     * Will throw an {@code Exception} if parsing is unsuccessful.
     * @param rawValue the value to parse
     * @return the boolean representation
     * @throws Exception
     */
    public static boolean parseBoolean(Value rawValue) throws Exception {
        Objects.requireNonNull(rawValue, "rawValue cannot be null");
        boolean returnBoolean;
        if (rawValue.hasBoolValue()) {
            returnBoolean = rawValue.getBoolValue();
        } else if (rawValue.hasNumberValue()) {
            returnBoolean = Double.compare(1d, rawValue.getNumberValue()) == 0;
        } else if (rawValue.hasStringValue()) {
            returnBoolean = rawValue.getStringValue().equalsIgnoreCase("true");
        } else {
            throw new Exception("Unable to parse to boolean. "
                    + "Identified value type: " + rawValue.getKindCase()
                    + " Property value: " + rawValue.toString());
        }
        return returnBoolean;
    }
}
