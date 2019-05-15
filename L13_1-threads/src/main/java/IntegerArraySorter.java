import java.util.ArrayList;
import java.util.List;

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
        List<Limit> limits = doSortSingleThread(0, array.length - 1);
        while (limits.size() > 0) {
            List<Limit> afterSortIterationLims = new ArrayList<Limit>();
            for (Limit limit : limits) {
                afterSortIterationLims.addAll(doSortSingleThread(limit.getBegin(), limit.getEnd()));
            }
            limits = afterSortIterationLims;
        }
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
                List<Limit> limits = doSortSingleThread(start, finalCur);
                while (limits.size() > 0) {
                    List<Limit> afterSortIterationLims = new ArrayList<Limit>();
                    for (Limit limit : limits) {
                        afterSortIterationLims.addAll(doSortSingleThread(limit.getBegin(), limit.getEnd()));
                    }
                    limits = afterSortIterationLims;
                }
            }
        };

        Thread thRight = new Thread() {
            public void run() {
                List<Limit> limits = doSortSingleThread(finalCur + 1, end);
                while (limits.size() > 0) {
                    List<Limit> afterSortIterationLims = new ArrayList<Limit>();
                    for (Limit limit : limits) {
                        afterSortIterationLims.addAll(doSortSingleThread(limit.getBegin(), limit.getEnd()));
                    }
                    limits = afterSortIterationLims;
                }
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

    private List<Limit> doSortSingleThread(final int start, final int end) {
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        cur = sort(i, j, cur);

        List<Limit> result = new ArrayList<Limit>();
        if (start < cur) {
            Limit lim = new Limit(start, cur);
            result.add(lim);
        }

        if (cur + 1 < end) {
            Limit lim = new Limit(cur + 1, end);
            result.add(lim);
        }
        return result;
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

    private class Limit {

        private int begin;
        private int end;

        public Limit(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }
    }

}
