package ru.otus.borisov.mygson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MyGson {

    public String toJson(Object object) throws IllegalAccessException {

        StringBuilder sb = new StringBuilder("{");

        for (Field field : object.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null) break;

            sb.append("\"").append(field.getName()).append("\"").append(":");
            if (field.getType().isPrimitive()) {
                appendPrimitiveValue(sb, field, value);
            } else if (value instanceof Collection) {
                appendCollection(sb, (Collection) value);
            } else if (value instanceof Map) {
                appendMap(sb, (Map) value);
            } else if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(toJson(value));
            }
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        sb.append("}");
        return sb.toString();
    }

    public <T> T fromJson(String json, Class<T> clazz) throws ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T object = clazz.newInstance();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);

        for (Field field : object.getClass().getDeclaredFields()) {

            field.setAccessible(true);

            if (field.getType().isPrimitive()) {
                if (field.getType().equals(Byte.TYPE)) {
                    field.set(object, ((Long) jsonObject.get(field.getName())).byteValue());
                } else if (field.getType().equals(Integer.TYPE)) {
                    field.set(object, ((Long) jsonObject.get(field.getName())).intValue());
                } else if (field.getType().equals(Short.TYPE)) {
                    field.set(object, ((Long) jsonObject.get(field.getName())).shortValue());
                } else if (field.getType().equals(Float.TYPE)) {
                    field.set(object, ((Double) jsonObject.get(field.getName())).floatValue());
                } else if (field.getType().equals(Character.TYPE)) {
                    field.set(object, ((String) jsonObject.get(field.getName())).charAt(0));
                } else {
                    field.set(object, jsonObject.get(field.getName()));
                }
            } else if (field.getType().isArray()) {
                JSONArray jsonArray = (JSONArray) jsonObject.get(field.getName());
                ArrayList list = new ArrayList();
                if (jsonArray != null) {
                    int len = jsonArray.size();
                    for (int i=0;i<len;i++){
                        list.add(jsonArray.get(i).toString());
                    }
                }
                field.set(object, list.toArray());
            } else if (Collection.class.isAssignableFrom(field.getType())) {

            } else if (Map.class.isAssignableFrom(field.getType())) {

            } else {
                field.set(object, jsonObject.get(field.getName()));
            }
        }

        return object;
    }

    private void appendMap(StringBuilder sb, Map value) throws IllegalAccessException {
        sb.append("{");
        Map map = value;
        for (Object key : map.keySet()) {
            sb.append("\"").append(key.toString()).append("\":");
            sb.append(toJson(map.get(key)));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
    }

    private void appendCollection(StringBuilder sb, Collection value) throws IllegalAccessException {
        sb.append("[");
        Iterator it = value.iterator();
        while (it.hasNext()) {
            Object collectionObject = it.next();
            sb.append(toJson(collectionObject));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
    }

    private void appendPrimitiveValue(StringBuilder sb, Field field, Object value) {
        if (field.getType().equals(Character.TYPE)) {
            sb.append("\"").append(value).append("\"");
        } else {
            sb.append(value);
        }
    }

}
