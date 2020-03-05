package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonExporter {

    private List<String> classNamesForExport = new ArrayList<>();

    public JsonExporter(List<String> classNamesForExport) {
        this.classNamesForExport = classNamesForExport;
    }

    private boolean needToConvert(String classname) {

//        System.out.println(c.getSimpleName() + " " + c.getName());
        if (classNamesForExport.contains(classname)) {
            return true;
        }

        return false;
    }

    public String getClassDescription(Class rootClass) {
        String res = "{\n";
        Field[] declaredFields = rootClass.getDeclaredFields();
        for (Field field :declaredFields) {
//            System.out.println(field.getName() + " " + field.getType().isSynthetic()+ " " + field.getType().isLocalClass()+ " " + field.getType().isMemberClass()+ " " + field.getType().isPrimitive());
            if (!isCollection(field.getType())) {
                res += describeField(field);
            } else {
                res += describeType(field);
            }
//            System.out.println09();
//            System.out.println();
        }
        return res + "\n}\n";
    }

    private String describeField(Field field) {
        String res = "";
        if (!needToConvert(field.getType().getName())) {
            res += "\"" + field.getName() + "\": \"" + field.getType().getSimpleName() + "\"," +
                    "\n";
        } else {
            res += "\"" + field.getName() + "\": " + getClassDescription(field.getType());
        }
        return res;
    }
    private String describeType(Field field) {
        String res = "[";

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        for (Type type : pt.getActualTypeArguments()) {
            Class tp = type.getClass();
            if (!isCollection(tp)) {
                if (!needToConvert(type.getTypeName())) {
                    res += "\"" + type.getTypeName() + "\": \"22222222222222222222" + type.getTypeName() + "\"," +
                            "\n";
                } else {
                    res += "\"" + type.getTypeName() + "\": 111111111111111 " + getClassDescription(type.getClass());
                }
            } else {
                res += "###########";
            }
        }

        return res + "]";
    }


    private boolean isCollection(Class<?> cls){
        if (Map.class.isAssignableFrom(cls)){
            return true;
        }
        if (Set.class.isAssignableFrom(cls)){
            return true;
        }
        return false;
    }

}
