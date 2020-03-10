package com.company;

import com.company.toJson.TestClass;

import java.util.ArrayList;
import java.util.List;

// Map, List, Set, tuple

public class Main {

    public static void main(String[] args) {

        List<String> classesForExport = new ArrayList<>();
        classesForExport.add("com.company.toJson.RemouteFile");

        JsonExporter jsonExporter = new JsonExporter(classesForExport);

        Class<TestClass> rootClass = TestClass.class;
        String res = "";

        try {
            res += jsonExporter.getClassDescription(rootClass, null, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(res);
    }


//    public String descrField(String[] args) {
//        Class<TestClass> rootClass = TestClass.class;
//    }
}
