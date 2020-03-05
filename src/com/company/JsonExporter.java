package com.company;

import java.lang.reflect.*;
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

    public String writeCollection(Class rootClass, Field field) throws ClassNotFoundException {

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
//        Class cls = (Class)((ParameterizedType)types[0]).getRawType();
//        String res = "\"" + field.getName() + "\": [" + getClassDescription(types[0].getClass(), field) + "]" ;

//        for (Type type : ) {

//        res += getClassDescription(rootClass);
        return "";
    }


    public String writeMap(Class rootClass, Field field) throws ClassNotFoundException {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        Type keyType = types[0];
        ParameterizedType valueType = (ParameterizedType)types[1];

//        Class cls = (Class)(valueType);

        Class cls = (Class)valueType.getRawType();
//        Class<?> clazz = Class.forName(valueType.getTypeName());
        return "\"" + field.getName() + "\": [" + keyType.getTypeName()+ ":" + getClassDescription(cls, field)+ "]" ;

        //return "";
    }


    public String getClassDescription(Class rootClass, Field field) throws ClassNotFoundException {
        String res = "";

        if (isCollection(rootClass)){
            if (isMap(rootClass)){
                res += writeMap(rootClass, field);
            } else {
                res += writeCollection(rootClass, field);
            }
        } else {
            if (isPrimitive(rootClass)) {
                res += rootClass.getTypeName();
            } else {
                res += "{";
                Field[] declaredFields = rootClass.getDeclaredFields();
                for (Field fieldInner :declaredFields) {
                    res += "\""+ fieldInner.getName() +"\": "+"\""+getClassDescription(fieldInner.getType(), fieldInner)+ "\", ";

                }
                res += "}";
            }
        }

        return res;


//        Field[] declaredFields = rootClass.getDeclaredFields();
//        for (Field field :declaredFields) {
////            System.out.println(field.getName() + " " + field.getType().isSynthetic()+ " " + field.getType().isLocalClass()+ " " + field.getType().isMemberClass()+ " " + field.getType().isPrimitive());
//            if (!isCollection(field.getType())) {
//                res += describeField(field);
//            } else {
//                res += describeType(field);
//            }
////            System.out.println09();
////            System.out.println();
//        }
//        return res + "\n}\n";
    }

//    private String describeField(Field field) {
//        String res = "";
//        if (!needToConvert(field.getType().getName())) {
//            res += "\"" + field.getName() + "\": \"" + field.getType().getSimpleName() + "\"," +
//                    "\n";
//        } else {
//            res += "\"" + field.getName() + "\": " + getClassDescription(field.getType());
//        }
//        return res;
//    }
//    private String describeType(Field field) {
//        String res = "[";
//
//        ParameterizedType pt = (ParameterizedType) field.getGenericType();
//        for (Type type : pt.getActualTypeArguments()) {
//            Class tp = type.getClass();
//            if (!isCollection(tp)) {
//                if (!needToConvert(type.getTypeName())) {
//                    res += "\"" + type.getTypeName() + "\": \"22222222222222222222" + type.getTypeName() + "\"," +
//                            "\n";
//                } else {
//                    res += "\"" + type.getTypeName() + "\": 111111111111111 " + getClassDescription(type.getClass());
//                }
//            } else {
//                res += "###########";
//            }
//        }
//
//        return res + "]";
//    }

    private boolean isMap(Class<?> cls) {
        if (Map.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }



    public static boolean isValidPrimitiveType(Class<?> retType)
    {
        if (retType.isPrimitive() && retType != void.class) return true;
        if (Number.class.isAssignableFrom(retType)) return true;
//        if (AbstractCode.class.isAssignableFrom(retType)) return true;
        if (Boolean.class == retType) return true;
        if (Character.class == retType) return true;
        if (String.class == retType) return true;
//        if (Date.class.isAssignableFrom(retType)) return true;
        if (byte[].class.isAssignableFrom(retType)) return true;
        if (Enum.class.isAssignableFrom(retType)) return true;
        return false;
    }



    private boolean isPrimitive(Class cls) {
        if (cls.isPrimitive()) {
            return true;
        }
        if (isValidPrimitiveType(cls)) {
            return true;
        }
        return false;
    }

    private boolean isCollection(Class<?> cls){
        if (Set.class.isAssignableFrom(cls)){
            return true;
        }
        if (List.class.isAssignableFrom(cls)){
            return true;
        }
        if (Array.class.isAssignableFrom(cls)){
            return true;
        }
        if (Map.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

}
