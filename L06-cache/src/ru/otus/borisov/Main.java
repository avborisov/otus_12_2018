package ru.otus.borisov;

import ru.otus.borisov.cache.CacheImpl;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CacheImpl<Integer, String> cache = new CacheImpl<>(1_000_000, 0, 0);

        for (int i = 0; i < 1_000_000; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < 1_000_000; i++) {
            cache.get(i);
        }

        System.out.println("Hits: " + cache.getHitsCount());
        System.out.println("Misses: " + cache.getMissesCount());
        System.out.println("Size: " + cache.size());

        cache.dispose();
    }

}
