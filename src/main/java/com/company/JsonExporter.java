package com.company;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class JsonExporter {

    private List<String> classNamesForExport = new ArrayList<>();

    public JsonExporter(List<String> classNamesForExport) {
        this.classNamesForExport = classNamesForExport;
    }

    public String writeCollection(Class rootClass, Field field, ParameterizedType typeList) throws ClassNotFoundException {

        if (typeList != null) {
            Type[] types = typeList.getActualTypeArguments();
            String collectionDescr = getClassDescription(((Class)types[0]), field, null);

            return  "[" + collectionDescr + "]" ;
        }

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        Type t = types[0];


        if (!(t instanceof Class)) {
            ParameterizedType valueType = (ParameterizedType)t;
            Class cls = (Class) valueType.getRawType();

            return " [" + getClassDescription(cls, field, valueType) + "]";
        } else {
            Class cls = (Class) types[0];

            String valDescr = getClassDescription(cls, null, null);
            return " [" + valDescr + "]";
        }
    }


    public String writeMap(Class rootClass, Field field, ParameterizedType typeList) throws ClassNotFoundException {

        if (typeList != null) {
            Type[] types = typeList.getActualTypeArguments();
            ParameterizedType p = (ParameterizedType)types[1];
            String keyDescr = getClassDescription(((Class)types[0]), field, null);
            String valDescr = getClassDescription((Class)p.getActualTypeArguments()[0], field, null);

            return  "[{" + keyDescr + ":" + valDescr+ "}]" ;
        }

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        Type keyType = types[0];

        ParameterizedType valueType = (ParameterizedType)types[1];

        Class cls = (Class)valueType.getRawType();

        return "[{" + keyType.getTypeName()+ ":" + getClassDescription(cls, field, valueType)+ "}]" ;
    }


    public String getClassDescription(Class rootClass, Field field, ParameterizedType types) throws ClassNotFoundException {
        String res = "";

        if (ClassUtils.isCollectionOrMap(rootClass)) {
            if (ClassUtils.isMap(rootClass)) {
                res += writeMap(rootClass, field, types);
            } else {
                res += writeCollection(rootClass, field, types);
            }
        } else {
            if (ClassUtils.isPrimitive(rootClass)) {
                res += rootClass.getTypeName();
            } else {
                res += "{";
                Field[] declaredFields = rootClass.getDeclaredFields();
                boolean firstTime = true;
                for (Field fieldInner :declaredFields) {
                    String descr = "\""+ fieldInner.getName() +"\": "+"\""+getClassDescription(fieldInner.getType(), fieldInner, null)+ "\"";
                    res += firstTime ? descr : "," + descr;
                    firstTime = false;
                }
                res += "}";
            }
        }

        return res;
    }
}
