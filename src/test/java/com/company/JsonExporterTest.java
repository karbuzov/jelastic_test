package com.company;

import com.company.toJson.TestClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonExporterTest {

    List<String> classesForExport = new ArrayList<>();
    JsonExporter jsonExporter = new JsonExporter(classesForExport);

    @Test
    public void testExactlyAsInMail() throws Exception{

        Class<TestClass> rootClass = TestClass.class;
        String res = "";
        res += jsonExporter.getClassDescription(rootClass, null, null);

        assertEquals("{\"result\": \"int\",\"keywords\": [{\"string\":[{\"isDirectory\": \"bool\",\"filePath\": \"string\",\"name\": \"string\",\"size\": \"long\"}, \"...\"]}, \"...\"],\"error\": \"string\"}", res);
    }

}