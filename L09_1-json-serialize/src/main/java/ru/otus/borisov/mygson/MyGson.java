package ru.otus.borisov.mygson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
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

    public <T> Object fromJson(String json, Class<T> clazz) throws ParseException, IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();

        JSONParser parser = new JSONParser();
        Object jsonObject = parser.parse(json);

        if (jsonObject instanceof JSONObject) {
            return getObject((JSONObject) jsonObject, clazz);
        } else if (jsonObject instanceof JSONArray){
            return getArray((JSONArray) jsonObject, clazz);
        }

        return json;
    }

    private <T> T getObject(JSONObject jsonObject, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();

        for (Field field : object.getClass().getDeclaredFields()) {

            field.setAccessible(true);

            Class<?> fieldType = field.getType();
            Object value = jsonObject.get(field.getName());

            if (value == null) {
                field.set(object, null);
            } else if (fieldType.isPrimitive()) {
                if (fieldType.equals(Byte.TYPE)) {
                    field.set(object, ((Long) value).byteValue());
                } else if (fieldType.equals(Integer.TYPE)) {
                    field.set(object, ((Long) value).intValue());
                } else if (fieldType.equals(Short.TYPE)) {
                    field.set(object, ((Long) value).shortValue());
                } else if (fieldType.equals(Float.TYPE)) {
                    field.set(object, ((Double) value).floatValue());
                } else if (fieldType.equals(Character.TYPE)) {
                    field.set(object, ((String) value).charAt(0));
                } else {
                    field.set(object, value);
                }
            } else if (fieldType.isArray()) {
                Class type = fieldType.getComponentType();
                field.set(object, getArray((JSONArray) value, type));
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                ParameterizedType dataType = (ParameterizedType) field.getGenericType();
                Class<?> dataTypeClass = (Class<?>) dataType.getActualTypeArguments()[0];
                field.set(object, getCollection((JSONArray) value, fieldType, dataTypeClass));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                ParameterizedType dataType = (ParameterizedType) field.getGenericType();
                Class<?> valueTypeClass = (Class<?>) dataType.getActualTypeArguments()[1];
                field.set(object, getMap((JSONObject) value, fieldType, valueTypeClass));
            } else if (fieldType.equals(String.class)) {
                field.set(object, value);
            } else {
                field.set(object, getObject((JSONObject) value, fieldType));
            }
        }
        return object;
    }

    private Collection createCollectionOfType(Class<?> fieldType) {
        Collection col;
        if (fieldType.equals(ArrayList.class)) {
            col = new ArrayList();
        } else if (fieldType.equals(LinkedList.class)) {
            col = new LinkedList();
        } else if (fieldType.equals(HashSet.class)) {
            col = new HashSet();
        } else if (fieldType.equals(LinkedHashSet.class)) {
            col = new LinkedHashSet();
        } else {
            throw new UnsupportedOperationException("Unsupported collection type yet!");
        }
        return col;
    }

    private Map createMapOfType(Class<?> fieldType) {
        Map map;
        if (fieldType.equals(HashMap.class)) {
            map = new HashMap();
        } else if (fieldType.equals(TreeMap.class)) {
            map = new TreeMap();
        } else {
            throw new UnsupportedOperationException("Unsupported map type yet!");
        }
        return map;
    }

    private <T> T[] getArray(JSONArray jsonArray, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T[] result = (T[]) Array.newInstance(clazz, jsonArray.size());
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                Object jsonObject = jsonArray.get(i);
                result[i] = (getObject((JSONObject) jsonObject, clazz));
            }
        }
        return result;
    }

    private Collection getCollection(JSONArray jsonArray, Class collectionType, Class dataType) throws IllegalAccessException, InstantiationException {
        Collection col = createCollectionOfType(collectionType);
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                Object jsonObject = jsonArray.get(i);
                col.add(getObject((JSONObject) jsonObject, dataType));
            }
        }
        return col;
    }

    private Map getMap(JSONObject jsonObject, Class mapType, Class valueTypeClass) throws IllegalAccessException, InstantiationException {
        Map map = createMapOfType(mapType);
        if (jsonObject != null) {
            Set set =  jsonObject.keySet();
            for (Object key : set) {
                map.put(key, getObject((JSONObject)jsonObject.get(key), valueTypeClass));
            }
        }
        return map;
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

}
