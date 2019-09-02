package ru.otus.borisov.test;

import org.junit.Assert;
import org.junit.Test;

public class ThreadTest {

    private long lastTellingThread = -1;

    @Test
    public void testThread() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println("-----------------------------------");
            CounterThread t1 = new CounterThread();
            CounterThread t2 = new CounterThread();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
    }

    private class CounterThread extends Thread {
        public void run() {
            try {
                for (int i = 1; i < 10; i++) {
                    tellCounter(i);
                }
                for (int i = 10; i > 0; i--) {
                    tellCounter(i);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
    }

    private synchronized void tellCounter(int counter) throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        while (lastTellingThread == threadId) {
            wait();
        }
        System.out.println(threadId + ": " + counter);
        Assert.assertNotEquals(threadId, lastTellingThread);
        lastTellingThread = threadId;

        notify();
    }

}
