package ru.otus.borisov.cache;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImpl<K, V> implements ICache<K, V> {

    private int maxSize;
    private long lifeTime;
    private long idleTime;
    private long hitsCount;
    private long missesCount;

    private Timer timer = new Timer();

    private Map<K, SoftReference<CachedElement>> cache;

    public CacheImpl(int maxSize, long lifeTime, long idleTime) {
        cache = new ConcurrentHashMap();
        this.maxSize = maxSize;
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
    }

    @Override
    public void put(K key, V object) {
        if (maxSize == cache.size()) {
            final Iterator<K> iterator = cache.keySet().iterator();
            iterator.next();
            iterator.remove();
        }
        SoftReference<CachedElement> cached = new SoftReference<>(new CachedElement(key, object));
        cache.put(key, cached);
    }

    @Override
    public V get(K key) {
        final SoftReference<CachedElement> softReference = cache.get(key);
        if (softReference == null || softReference.get() == null) {
            missesCount++;
            return null;
        } else {
            hitsCount++;
        }
        softReference.get().lastHandleTime = System.currentTimeMillis();
        return softReference.get().value;
    }

    @Override
    public long getHitsCount() {
        return hitsCount;
    }

    @Override
    public long getMissesCount() {
        return missesCount;
    }

    @Override
    public long size() {
        return 0;
    }

    private class CachedElement {

        private V value;
        private long lastHandleTime;

        public CachedElement(K key, V value) {
            this.value = value;
            this.lastHandleTime = System.currentTimeMillis();

            if (lifeTime > 0) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (isValueInCacheOrNull(key, value)) {
                            cache.remove(key);
                        }
                    }
                }, lifeTime);
            }

            if (idleTime > 0) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (isValueInCacheOrNull(key, value) && System.currentTimeMillis() - lastHandleTime >= idleTime) {
                            cache.remove(key);
                            this.cancel();
                        }
                    }
                }, idleTime, idleTime);
            }
        }

        private boolean isValueInCacheOrNull(K key, V value) {
            SoftReference<CachedElement> cachedElementSoftReference = cache.get(key);
            return cachedElementSoftReference == null || cachedElementSoftReference.get() == null || cachedElementSoftReference.get().value.equals(value);
        }
    }

    public void dispose() {
        timer.cancel();
    }

}
