package com.company;

public class JsonAsInMailFormatter {
    public static String changeTypeDescription(String type) {
        return type
                .replace("java.lang.String", "string")
                .replace("boolean", "bool");
    }

    public static String addCollectionMore() {
        return ", \"...\"";
    }

}
