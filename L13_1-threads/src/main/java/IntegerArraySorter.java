import org.apache.commons.lang3.ArrayUtils;

public class IntegerArraySorter {

    private int[] array;

    public IntegerArraySorter(int[] array) {
        this.array = array;
    }

    public int[] sortMultiThread() {
        doSortMultiThread(0, array.length - 1);
        return array;
    }

    public int[] sortSingleThread() {
        doSortSingleThread(0, array.length - 1);
        return array;
    }

    private void doSortMultiThread(final int start, final int end) {
        if (start >= end) return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        cur = sort(i, j, cur);

        final int finalCur = cur;

        Thread thLeft = new Thread() {
            public void run() {
                doSortSingleThread(start, finalCur);
            }
        };

        Thread thRight = new Thread() {
            public void run() {
                doSortSingleThread(finalCur + 1, end);
            }
        };

        thLeft.start();
        thRight.start();

        try {
            thLeft.join();
            thRight.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doSortSingleThread(final int start, final int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        cur = sort(i, j, cur);
        doSortSingleThread(start, cur);
        doSortSingleThread(cur+1, end);
    }

    private int sort(int i, int j, int cur) {
        while (i < j) {
            while (i < cur && (array[i] <= array[cur])) {
                i++;
            }
            while (j > cur && (array[cur] <= array[j])) {
                j--;
            }
            if (i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        return cur;
    }

    public int[] getArray() {
        return array;
    }

}
