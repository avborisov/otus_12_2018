package ru.otus.borisov;

public class SyncThreadTest {

    public SyncThreadTest() {
        for (int i = 0; i < 2; i++) {
            CounterThread t = new CounterThread();
            t.start();
        }
    }

    private class CounterThread extends Thread {
        public void run() {
            for (int i = 1; i < 10; i++) {
                tellCounter(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("concurrence error");
                    return;
                }
            }
            for (int i = 10; i > 0; i--) {
                tellCounter(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("concurrence error");
                    return;
                }
            }
        }
    }

    synchronized private void tellCounter(int counter) {
        System.out.println(counter);
    }

}
