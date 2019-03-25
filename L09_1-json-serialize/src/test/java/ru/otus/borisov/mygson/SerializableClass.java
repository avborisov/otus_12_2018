package ru.otus.borisov.mygson;

import java.util.*;

class SerializableClass implements Comparable {

    private boolean booleanField;
    private byte byteField;
    private char charField;
    private short shortField;
    private long longField;
    private float floatField;
    private double doubleField;

    private Object nullValue;
    private String stringField;
    private Object[] array;

    private ArrayList arrayList;
    private LinkedList linkedList;
    private HashSet hashSet;
    private LinkedHashSet linkedHashSet;
    private HashMap hashMap;
    private TreeMap treeMap;

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public byte getByteField() {
        return byteField;
    }

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public char getCharField() {
        return charField;
    }

    public void setCharField(char charField) {
        this.charField = charField;
    }

    public short getShortField() {
        return shortField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public float getFloatField() {
        return floatField;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Object[] getArray() {
        return array;
    }

    public void setArray(Object[] array) {
        this.array = array;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public HashSet getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet hashSet) {
        this.hashSet = hashSet;
    }

    public LinkedHashSet getLinkedHashSet() {
        return linkedHashSet;
    }

    public void setLinkedHashSet(LinkedHashSet linkedHashSet) {
        this.linkedHashSet = linkedHashSet;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public TreeMap getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(TreeMap treeMap) {
        this.treeMap = treeMap;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializableClass)) return false;
        SerializableClass that = (SerializableClass) o;
        return booleanField == that.booleanField &&
                byteField == that.byteField &&
                charField == that.charField &&
                shortField == that.shortField &&
                longField == that.longField &&
                Float.compare(that.floatField, floatField) == 0 &&
                Double.compare(that.doubleField, doubleField) == 0 &&
                Objects.equals(stringField, that.stringField) &&
                Arrays.equals(array, that.array) &&
                Objects.equals(arrayList, that.arrayList) &&
                Objects.equals(linkedList, that.linkedList) &&
                Objects.equals(hashSet, that.hashSet) &&
                Objects.equals(linkedHashSet, that.linkedHashSet) &&
                Objects.equals(hashMap, that.hashMap) &&
                Objects.equals(treeMap, that.treeMap);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(booleanField, byteField, charField, shortField, longField, floatField, doubleField, stringField, arrayList, linkedList, hashSet, linkedHashSet, hashMap, treeMap);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
