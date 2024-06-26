/**
 * The Initial Developer of some parts of this file, which are copied from, derived from, or
 * inspired by Cognite Java SDK for CDF, is Cognite AS (http://www.cognite.com/).
 * Copyright 2022 Cogntite AS. All Rights Reserved.
 */
package com.kinnovatio.utils;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.google.protobuf.util.Values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class hosts methods to help parse data from {@code Struct} objects. {@code Struct} is used to represent
 * data in CDF Raw as well as the typed version of Json objects.
 */
public class ParseStruct {

    /**
     * Parses a node in the {@code Struct} into a delimited string. This is convenient in case the node itself,
     * or one of its parents, is a list with multiple entries.
     *
     * @param struct The Struct to parse.
     * @param path The path of node to parse, separated by period ("."). Ex: "parent.child.grandChild"
     * @param delimiter The delimiter to use in the resulting String
     * @return A delimited string representation of parsed Struct
     */
    public static String parseStringDelimited(Struct struct, String path, String delimiter) {
        Objects.requireNonNull(struct, "Struct cannot be null");
        Validate.isTrue(null != path && !path.isBlank(),
                "Path cannot be null or empty");
        Validate.isTrue(null != delimiter && !delimiter.isBlank(),
                "Delimiter cannot be null or empty");

        List<String> pathList = Arrays.asList(path.split("\\."));
        return parseStringList(struct, pathList).stream().collect(Collectors.joining(delimiter));
    }

    /**
     * Parses a node in the {@code Struct} into a {@code List<String>}. This is convenient in case the node itself,
     * or one of its parents, is a list.
     *
     * @param struct The Struct to parse.
     * @param path The path of node to parse, each item in the list is a path component.
     * @return A list of the data matching the path. If no match, then an empty list is returned.
     */
    public static List<String> parseStringList(Struct struct, List<String> path) {
        Objects.requireNonNull(struct, "Struct cannot be null.");
        Objects.requireNonNull(path, "Path cannot be null.");

        List<String> returnList = new ArrayList<>();
        if (!path.isEmpty() && struct.containsFields(path.getFirst())) {
            returnList.addAll(parseStringList(
                    struct.getFieldsOrDefault(path.getFirst(), Values.of("")),
                    path.subList(1, path.size())));
        }

        return returnList;
    }

    /**
     * Parses a node in the {@code Value} into a {@code List<String>}. This is convenient in case the node itself,
     * or one of its parents, is a list.
     *
     * @see #parseStringList(Struct, List)
     *
     * @param value The Value to parse
     * @param path The path of node to parse, each item in the list is a path component.
     * @return A list of the data matching the path. If no match, then an empty list is returned.
     */
    public static List<String> parseStringList(Value value, List<String> path) {
        Objects.requireNonNull(value, "Value cannot be null.");
        Objects.requireNonNull(path, "Path cannot be null.");

        List<String> returnList = new ArrayList<>();
        if (!path.isEmpty()) {
            // We are not at the child node yet, so we have to unwrap and extract the next level.
            // This is only possible for a Struct or ValueList.
            if (value.hasStructValue()) {
                returnList.addAll(parseStringList(value.getStructValue(), path));
            } else if (value.hasListValue()) {
                List<Value> valueList = value.getListValue().getValuesList();
                for (Value element : valueList) {
                    returnList.addAll(parseStringList(element, path));
                }
            }
        } else {
            // We are at the child node. Must not be null. Handle list separately.
            if (value.hasListValue()) {
                List<Value> valueList = value.getListValue().getValuesList();
                for (Value element : valueList) {
                    if (!element.hasNullValue()) {
                        returnList.add(ParseValue.parseString(element));
                    }
                }
            } else if (!value.hasNullValue()) {
                returnList.add(ParseValue.parseString(value));
            }
        }

        return returnList;
    }
}
