package ru.otus.borisov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class Main {

    //keep a count of the total time spent in GCs
    private static long totalGcDuration = 0;
    private static long minorCount = 0;
    private static long minorTime = 0;
    private static long majorCount = 0;
    private static long majorTime = 0;

    private static long startTime;

    private static void installGCMonitoring(){
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = new NotificationListener() {

                @Override
                public void handleNotification(Notification notification, Object handback) {
                    //we only handle GARBAGE_COLLECTION_NOTIFICATION notifications here
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {

                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        //get all the info and pretty print it
                        long duration = info.getGcInfo().getDuration();
                        String gctype = info.getGcAction();
                        if ("end of minor GC".equals(gctype)) {
                            minorCount++;
                            minorTime += duration;
                        } else if ("end of major GC".equals(gctype)) {
                            majorCount++;
                            majorTime += duration;
                        }
                        totalGcDuration += duration;
                    }
                }
            };

            //Add the listener
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static void printGCMetrics() {

        StringBuilder sb = new StringBuilder();
        sb.append("YoungGC -> Count: ").append(minorCount)
                .append(", Time (ms): ").append(minorTime)
                .append(", OldGC -> Count: ").append(majorCount)
                .append(", Time (ms): ").append(majorTime)
                .append(", Total GC time (ms): ").append(totalGcDuration);

        long endTime = System.nanoTime();
        double secondsTime = (endTime - startTime) / 60_000_000_000.0;
        sb.append(", application work time: ").append(secondsTime).append(" min");

        System.out.println(sb);
    }

    public static void main(String[] args) throws InterruptedException {
        startTime = System.nanoTime();
        int elementsInIteration = 100;

        installGCMonitoring();

        try {
            List<String> list = new ArrayList<>();
            while (true) {
                for (int i = 0; i < elementsInIteration; i++) {
                    list.add(new String(String.valueOf(System.nanoTime())));
                }
                for (int i = 0; i < elementsInIteration / 3; i++) {
                    list.remove(0);
                }
            }
        } finally {
            printGCMetrics();
        }
    }

}
