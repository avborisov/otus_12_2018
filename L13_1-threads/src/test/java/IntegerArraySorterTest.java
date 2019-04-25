import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class IntegerArraySorterTest {

    private static int ARRAY_SIZE = 1000000;
    private static int[] array = new int[ARRAY_SIZE];

    @BeforeClass
    public static void initArray() {
        Random generator = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = generator.nextInt(100);
        }
    }

    @Test
    public void testSingleThreadSorting() {
        IntegerArraySorter sorter = new IntegerArraySorter(ArrayUtils.clone(array));

        long startTime = System.nanoTime();
        sorter.sortSingleThread();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Multi thread sort duration: " + duration);
    }

    @Test
    public void testMultiThreadSorting() {
        IntegerArraySorter sorter = new IntegerArraySorter(ArrayUtils.clone(array));

        long startTime = System.nanoTime();
        sorter.sortMultiThread();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Single thread sort duration: " + duration);
    }

    @Test
    public void compareSortResults() {
        IntegerArraySorter singleThreadSorter = new IntegerArraySorter(ArrayUtils.clone(array));
        IntegerArraySorter multiThreadSorter = new IntegerArraySorter(ArrayUtils.clone(array));
        assert(Arrays.equals(singleThreadSorter.getArray(), multiThreadSorter.getArray()));

        singleThreadSorter.sortSingleThread();
        multiThreadSorter.sortMultiThread();

        System.out.println(Arrays.toString(singleThreadSorter.getArray()));
        System.out.println(Arrays.toString(multiThreadSorter.getArray()));

        assert(Arrays.equals(singleThreadSorter.getArray(), multiThreadSorter.getArray()));
    }

}
