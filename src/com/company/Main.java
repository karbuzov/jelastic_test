package com.company;

import java.util.ArrayList;
import java.util.List;

// Map, List, Set, tuple

public class Main {

    public static void main(String[] args) {

        List<String> classesForExport = new ArrayList<>();
        classesForExport.add("com.company.RemouteFile");

        JsonExporter jsonExporter = new JsonExporter(classesForExport);

        Class<TestClass> rootClass = TestClass.class;
        String res = "";

        res += jsonExporter.getClassDescription(rootClass);

        System.out.println(res);
    }


//    public String descrField(String[] args) {
//        Class<TestClass> rootClass = TestClass.class;
//    }
}
