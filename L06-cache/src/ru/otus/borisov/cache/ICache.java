package ru.otus.borisov.cache;

public interface ICache<K, T> {

    public void put(K key, T object);
    public T get(K key);

    public long getHitsCount();
    public long getMissesCount();
    public long size();

    public void dispose();

}
