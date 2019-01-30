package ru.otus.borisov;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class Main {

    static final Set<String> YOUNG_GC = new HashSet<String>(3);
    static final Set<String> OLD_GC = new HashSet<>(3);

    static long startTime;

    static {
        // young generation GC names
        YOUNG_GC.add("PS Scavenge");
        YOUNG_GC.add("ParNew");
        YOUNG_GC.add("Copy");
        YOUNG_GC.add("G1 Young Generation");

        // old generation GC names
        OLD_GC.add("PS MarkSweep");
        OLD_GC.add("ConcurrentMarkSweep");
        OLD_GC.add("MarkSweepCompact");
        OLD_GC.add("G1 Old Generation");
    }

    private static void printGCMetrics() {

        long minorCount = 0;
        long minorTime = 0;
        long majorCount = 0;
        long majorTime = 0;
        long unknownCount = 0;
        long unknownTime = 0;

        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gc : mxBeans) {
            long count = gc.getCollectionCount();
            if (count >= 0) {
                if (YOUNG_GC.contains(gc.getName())) {
                    minorCount += count;
                    minorTime += gc.getCollectionTime();
                } else if (OLD_GC.contains(gc.getName())) {
                    majorCount += count;
                    majorTime += gc.getCollectionTime();
                } else {
                    unknownCount += count;
                    unknownTime += gc.getCollectionTime();
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("YoungGC -> Count: ").append(minorCount)
                .append(", Time (ms): ").append(minorTime)
                .append(", OldGC -> Count: ").append(majorCount)
                .append(", Time (ms): ").append(majorTime);

        if (unknownCount > 0) {
            sb.append(", UnknownGC -> Count: ").append(unknownCount)
                    .append(", Time (ms): ").append(unknownTime);
        }

        long endTime = System.nanoTime();
        double secondsTime = (endTime - startTime) / 60_000_000_000.0;
        sb.append(", application work time: ").append(secondsTime).append(" min");

        System.out.println(sb);
    }

    public static void main(String[] args) throws InterruptedException {
        startTime = System.nanoTime();
        int initialSize = 1_000_000;

        Timer timer = new Timer();
        PrintGCStat task = new PrintGCStat();

        timer.schedule(task,0,1000);

        try {
            while (true) {
                String[] array = new String[initialSize];
                for (int i = 0; i < array.length; i++) {
                    array[i] = new String(String.valueOf(new Date().getTime()));
                    if (i % 2 == 0 || i % 3 == 0 || i % 5 == 0) {
                        array[i] = null;
                    }
                    if (i % 1_000 == 0) {
                        Thread.sleep(10);
                    }
                }
                System.out.println("Have not OutOfSpace Error, increase array size on 10");
                initialSize = initialSize * 10;
            }
        } finally {
            timer.cancel();
        }
    }

    public static class PrintGCStat extends TimerTask {
        public void run() {
            printGCMetrics();
        }
    }

}
