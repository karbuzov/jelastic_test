package com.company;

import com.company.toJson.TestClass;

public class Main {

    public static void main(String[] args) {

        JsonExporter jsonExporter = new JsonExporter();

        Class<TestClass> rootClass = TestClass.class;
        String res = "";

        try {
            res += jsonExporter.getClassDescription(rootClass, null, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(res);
    }
}
