package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonExporter {

    private List<String> classNamesForExport = new ArrayList<>();

    public JsonExporter(List<String> classNamesForExport) {
        this.classNamesForExport = classNamesForExport;
    }

    private boolean needToConvert(Class<?> c) {

//        System.out.println(c.getSimpleName() + " " + c.getName());
        if (classNamesForExport.contains(c.getName())) {
            return true;
        }

        return false;
    }

    public String getClassDescription(Class rootClass) {
        String res = "{\n";
        Field[] declaredFields = rootClass.getDeclaredFields();
        for (Field field :declaredFields) {
//            System.out.println(field.getName() + " " + field.getType().isSynthetic()+ " " + field.getType().isLocalClass()+ " " + field.getType().isMemberClass()+ " " + field.getType().isPrimitive());
            if (!isCollection(field)) {
                res += describeField(field);
            } else {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                for (Type type : pt.getActualTypeArguments()) {
                    needToConvert(type.getClass());
                    res += describeType(type);
//                "\"" + type.getTypeName() + "\": \"";
//                    System.out.println(type.getTypeName());
                }
            }
            //            System.out.println();
//            System.out.println();
        }
        return res + "\n}\n";
    }

    private String describeField(Field field) {
        String res = "";
        if (!needToConvert(field.getType())) {
            res += "\"" + field.getName() + "\": \"" + field.getType().getSimpleName() + "\"," +
                    "\n";
        } else {
            res += "\"" + field.getName() + "\": " + getClassDescription(field.getClass());
        }
        return res;
    }
    private String describeType(Type type) {
        String res = "";
//        "\"" + type.getTypeName() + "\": \"";
        if (!needToConvert(type.getClass())) {
            res += "\"" + type.getTypeName() + "\": \"" + type.getTypeName() + "\"," +
                    "\n";
        } else {
            res += "\"" + type.getTypeName() + "\": " + getClassDescription(type.getClass());
        }
        return res;
    }


    private boolean isCollection(Field field){
        if (Map.class.isAssignableFrom(field.getType())){
            return true;
        }
        return false;
    }

}
