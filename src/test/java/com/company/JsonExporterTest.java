package com.company;

import com.company.toJson.TestClass;
import com.company.toJson.TestClass2;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonExporterTest {

    JsonExporter jsonExporter = new JsonExporter();

    @Test
    public void testExactlyAsInMail() throws Exception{

        Class<TestClass> rootClass = TestClass.class;
        String res = "";
        res += jsonExporter.getClassDescription(rootClass, null, null);

        String expected = "{" +
                "\"result\": \"int\"" +
                ",\"keywords\": [{\"string\":" +
                                "[{\"isDirectory\": \"bool\"," +
                                "\"filePath\": \"string\"," +
                                "\"name\": \"string\"," +
                                "\"size\": \"long\"}, \"...\"]}, \"...\"]," +
                "\"error\": \"string\"}";
        assertEquals(expected, res);
    }

    @Test
    public void testCompound() throws Exception{

        Class<TestClass2> rootClass = TestClass2.class;
        String res = "";
        res += jsonExporter.getClassDescription(rootClass, null, null);
        String expected = "{\"result\": \"int\",\"result2\": \"int\",\"longval\": \"long\",\"aaa\": {\"isDirectory\": \"bool\",\"filePath\": \"string\",\"name\": \"string\",\"size\": \"long\"},\"keywords\": [{\"string\":[{\"isDirectory\": \"bool\",\"filePath\": \"string\",\"name\": \"string\",\"size\": \"long\"}, \"...\"]}, \"...\"],\"error\": \"string\",\"keywords2\": [{\"string\":[{\"isDirectory\": \"bool\",\"filePath\": \"string\",\"name\": \"string\",\"size\": \"long\"}, \"...\"]}, \"...\"],\"keywords3\": [{\"string\":[{\"\"string\"\":{\"isDirectory\": \"bool\",\"filePath\": \"string\",\"name\": \"string\",\"size\": \"long\"}}, \"...\"]}, \"...\"],\"collection\":  [[\"string\", \"...\"], \"...\"],\"collection4\":  [[\"string\", \"...\"], \"...\"],\"collection5\":  [[\"string\", \"...\"], \"...\"],\"collection2\":  [\"string\", \"...\"],\"collection3\":  [\"string\", \"...\"]}";
        assertEquals(expected, res);
    }
}