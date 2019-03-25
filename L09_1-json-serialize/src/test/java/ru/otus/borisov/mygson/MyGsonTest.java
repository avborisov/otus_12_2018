package ru.otus.borisov.mygson;

import com.google.gson.Gson;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyGsonTest {

    @Test
    public void toJsonTest() throws IllegalAccessException {

        SerializableClass object = getSerializableObject();

        Gson gson = new Gson();
        String gsonJson = gson.toJson(object);

        MyGson myGson = new MyGson();
        String myGsonJson = myGson.toJson(object);

        System.out.println(myGsonJson);

        assertEquals(gsonJson.length(), myGsonJson.length());
    }

    @Test
    public void fromJsonTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, ParseException, InvocationTargetException {

        SerializableClass object = getSerializableObject();

        Gson gson = new Gson();
        String gsonJson = gson.toJson(object);
        SerializableClass desirializedByGson = gson.fromJson(gsonJson, SerializableClass.class);

        MyGson myGson = new MyGson();
        String myGsonJson = myGson.toJson(object);
        SerializableClass desirializedByMyGson = myGson.fromJson(myGsonJson, SerializableClass.class);

        assertTrue(desirializedByGson.equals(desirializedByMyGson));
    }

    private SerializableClass getSerializableObject() {
        SerializableClass object = getPrimitiveSerializableObject();

        object.setStringField("Hello Gson!");

        object.setArray(new SerializableClass[] {getPrimitiveSerializableObject(), getPrimitiveSerializableObject()});

        ArrayList<SerializableClass> arrayList = new ArrayList();
        arrayList.add(getPrimitiveSerializableObject());
        arrayList.add(getPrimitiveSerializableObject());
        object.setArrayList(arrayList);

        LinkedList<SerializableClass> linkedList = new LinkedList();
        linkedList.add(getPrimitiveSerializableObject());
        linkedList.add(getPrimitiveSerializableObject());
        object.setLinkedList(linkedList);

        HashSet<SerializableClass> hashSet = new HashSet();
        hashSet.add(getPrimitiveSerializableObject());
        hashSet.add(getPrimitiveSerializableObject());
        object.setHashSet(hashSet);

        LinkedHashSet<SerializableClass> linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(getPrimitiveSerializableObject());
        linkedHashSet.add(getPrimitiveSerializableObject());
        object.setLinkedHashSet(linkedHashSet);

        HashMap<String, SerializableClass> hashMap = new HashMap();
        hashMap.put("0", getPrimitiveSerializableObject());
        hashMap.put("1", getPrimitiveSerializableObject());
        object.setHashMap(hashMap);

        TreeMap<String, SerializableClass> treeMap = new TreeMap();
        treeMap.put("0", getPrimitiveSerializableObject());
        treeMap.put("1", getPrimitiveSerializableObject());
        object.setTreeMap(treeMap);

        return object;
    }

    private SerializableClass getPrimitiveSerializableObject() {
        SerializableClass object = new SerializableClass();
        object.setBooleanField(true);
        object.setByteField(Byte.MAX_VALUE);
        object.setCharField('a');
        object.setShortField(Short.MAX_VALUE);
        object.setLongField(Long.MAX_VALUE);
        object.setFloatField(Float.MAX_VALUE);
        object.setDoubleField(Double.MAX_VALUE);
        return object;
    }

}
