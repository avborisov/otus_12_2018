package ru.otus.borisov;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockThreadTest {

    Lock lock;

    public LockThreadTest() {
        lock = new ReentrantLock();

        for (int i = 0; i < 2; i++) {
            CounterThread t = new CounterThread();
            t.start();
        }
    }

    private class CounterThread extends Thread {
        public void run() {
            for (int i = 1; i < 10; i++) {
                tellCounter(i);
            }
            for (int i = 10; i > 0; i--) {
                tellCounter(i);
            }
        }
    }

    private void tellCounter(int counter) {
        lock.lock();
        System.out.println(counter);
        lock.unlock();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("concurrence error");
        }
    }

}
