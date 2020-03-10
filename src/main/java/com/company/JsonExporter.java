package com.company;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class JsonExporter {

    private List<String> classNamesForExport = new ArrayList<>();

    JsonExporter(List<String> classNamesForExport) {
        this.classNamesForExport = classNamesForExport;
    }

    public String getClassDescription(Class rootClass, Field field, ParameterizedType types) throws ClassNotFoundException {
        StringBuilder res = new StringBuilder();

        if (ClassUtils.isCollectionOrMap(rootClass)) {
            if (ClassUtils.isMap(rootClass)) {
                res.append(writeMap(field, types));
            } else {
                res.append(writeCollection(field, types));
            }
        } else {
            if (ClassUtils.isPrimitive(rootClass)) {
                res.append("\"" + JsonAsInMailFormatter.changeTypeDescription(rootClass.getTypeName()) + "\"");
            } else {
                formatCompoundObject(rootClass, res);
            }
        }

        return res.toString();
    }

    private void formatCompoundObject(Class rootClass, StringBuilder res) throws ClassNotFoundException {
        res.append("{");
        Field[] declaredFields = rootClass.getDeclaredFields();
        boolean firstTime = true;
        for (Field fieldInner :declaredFields) {
            String descr = "\""+ fieldInner.getName() +"\": " + getClassDescription(fieldInner.getType(), fieldInner, null);
            res.append(firstTime ? descr : "," + descr);
            firstTime = false;
        }
        res.append("}");
    }

    private String writeCollection(Field field, ParameterizedType typeList) throws ClassNotFoundException {

        if (typeList != null) {
            Type[] types = typeList.getActualTypeArguments();
            String collectionDescr = getClassDescription(((Class)types[0]), field, null);

            return  "[" + collectionDescr + JsonAsInMailFormatter.addCollectionMore() + "]" ;
        }

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        Type t = types[0];


        if (!(t instanceof Class)) {
            ParameterizedType valueType = (ParameterizedType)t;
            Class cls = (Class) valueType.getRawType();

            return " [" + getClassDescription(cls, field, valueType) + JsonAsInMailFormatter.addCollectionMore() + "]";
        } else {
            Class cls = (Class) types[0];

            String valDescr = getClassDescription(cls, null, null);
            return " [" + valDescr + JsonAsInMailFormatter.addCollectionMore() + "]";
        }
    }


    private String writeMap(Field field, ParameterizedType typeList) throws ClassNotFoundException {

        if (typeList != null) {
            Type[] types = typeList.getActualTypeArguments();
            ParameterizedType p = (ParameterizedType)types[1];
            String keyDescr = getClassDescription(((Class)types[0]), field, null);
            String valDescr = getClassDescription((Class)p.getActualTypeArguments()[0], field, null);

            return  "[{\"" + JsonAsInMailFormatter.changeTypeDescription(keyDescr) + "\":" + JsonAsInMailFormatter.changeTypeDescription(valDescr) + "}" + JsonAsInMailFormatter.addCollectionMore() + "]" ;
        }

        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] types = pt.getActualTypeArguments();
        Type keyType = types[0];

        ParameterizedType valueType = (ParameterizedType)types[1];

        Class cls = (Class)valueType.getRawType();

        return "[{\"" + JsonAsInMailFormatter.changeTypeDescription(keyType.getTypeName())+ "\":" + getClassDescription(cls, field, valueType)+ "}" + JsonAsInMailFormatter.addCollectionMore() + "]" ;
    }

}
