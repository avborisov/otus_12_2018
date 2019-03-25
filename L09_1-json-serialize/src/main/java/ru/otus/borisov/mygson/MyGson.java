package ru.otus.borisov.mygson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MyGson {

    public String toJson(Object object) throws IllegalAccessException {
        if (object.getClass().isPrimitive() || object.getClass().equals(String.class)) {
            return object.toString();
        }
        if (object.getClass().isArray()) {
            return toJson(object, new JSONArray()).toJSONString();
        }
        return toJson(object, new JSONObject()).toJSONString();
    }

    private JSONObject toJson(Object object, JSONObject jsonObject) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {

            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null) continue;

            String fieldName = field.getName();

            if (field.getType().isPrimitive() || value instanceof String) {
                jsonObject.put(fieldName, getPrimitiveValue(value));
            } else if (field.getType().isArray()) {
                jsonObject.put(fieldName, toJson(value, new JSONArray()));
            } else if (value instanceof Collection) {
                jsonObject.put(fieldName, toJson(((Collection)value).toArray(), new JSONArray()));
            } else if (value instanceof Map) {
                Map map = (Map) value;
                Set keys = map.keySet();
                JSONObject mapObject = new JSONObject();
                for (Object key : keys) {
                    Object mapElement = map.get(key);
                    if (mapElement.getClass().isPrimitive() || mapElement.getClass().equals(String.class)) {
                        mapObject.put(key.toString(), getPrimitiveValue(mapElement));
                    } else if (mapElement.getClass().isArray()) {
                        mapObject.put(key.toString(), toJson(map.get(key), new JSONArray()));
                    } else {
                        mapObject.put(key.toString(), toJson(map.get(key), new JSONObject()));
                    }
                }
                jsonObject.put(fieldName, mapObject);
            }
        }

        return jsonObject;
    }

    private JSONArray toJson(Object array, JSONArray jsonArray) throws IllegalAccessException {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i ++) {
            Object arrayElement = Array.get(array, i);
            if (arrayElement.getClass().isPrimitive() || arrayElement.getClass().equals(String.class)) {
                jsonArray.add(getPrimitiveValue(arrayElement));
            } else if (arrayElement.getClass().isArray()) {
                jsonArray.add(toJson(arrayElement, new JSONArray()));
            } else {
                jsonArray.add(toJson(arrayElement, new JSONObject()));
            }
        }
        return jsonArray;
    }

    private Object getPrimitiveValue(Object primitive) {
        return primitive.getClass().equals(Character.class) ? "" + (Character) primitive : primitive;
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
}
