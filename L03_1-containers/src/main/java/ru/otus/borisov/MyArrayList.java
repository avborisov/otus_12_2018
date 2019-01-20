package ru.otus.borisov;

import java.lang.reflect.Array;
import java.util.*;

class MyArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] list;
    private int size = 0;

    public MyArrayList() {
        list = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (list[i] == null) {
                    return true;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (o.equals(list[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Integer currentPos;
            private boolean removeAllowed = false;

            @Override
            public void remove() {
                if (!removeAllowed) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.remove(currentPos);
                removeAllowed = false;
            }

            @Override
            public boolean hasNext() {
                int nextPos = currentPos == null ? 0 : currentPos + 1;
                if (nextPos < list.length) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentPos = currentPos == null ? 0 : currentPos + 1;
                removeAllowed = true;
                return (T) list[currentPos];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(list, 0, result, 0, size);
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) toArray();
        }
        System.arraycopy(list, 0, a, 0, size);
        for (int i = size; i < size; i++) {
            a[i] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        expandIfNeed();
        list[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (o != null && o.equals(next) ||
                    o == null && next == null) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Iterator<? extends T> it = c.iterator();
        while (it.hasNext()) {
            T o = it.next();
            add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        size = size + c.size();
        expandIfNeed();

        Object[] tail = new Object[list.length - index];
        System.arraycopy(list, index, tail, 0, tail.length);

        Iterator<? extends T> it = c.iterator();
        while (it.hasNext()) {
            T object = it.next();
            list[index++] = object;
        }

        int tailIndex = 0;
        for (int i = index; i < index + tail.length; i++) {
            list[i] = tail[tailIndex++];
        }

        return true;
    }

    private void expandIfNeed() {
        if (list.length <= size) {
            Object[] newList = new Object[size*2];
            System.arraycopy(list, 0, newList, 0, list.length);
            list = newList;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (c.contains(o)) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (!c.contains(o)) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public void clear() {
        list = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        list[index] = element;
        return (T) list[index];
    }

    @Override
    public void add(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        size++;
        expandIfNeed();

        Object[] tail = new Object[list.length - index];
        System.arraycopy(list, index, tail, 0, tail.length);

        list[index] = element;

        int tailIndex = 0;
        for (int i = index + 1; i < index + tail.length; i++) {
            list[i] = tail[tailIndex++];
        }
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        size--;
        T removed = (T) list[index];
        System.arraycopy(list, index + 1, list, index, size);
        list[size] = null;
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null && list[i] == null ||
                    o != null & o.equals(list[i]))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o == null && list[i] == null ||
                    o != null & o.equals(list[i]))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        List<T> sublist = new MyArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add((T) list[i]);
        }
        return sublist;
    }

    private class MyListIterator implements ListIterator<T> {

        private Integer currentPos;
        private boolean actionAllowed = false;

        public MyListIterator() {
        }

        public MyListIterator(int index) {
            if (index < 0 || index > size()) {
                throw new IndexOutOfBoundsException();
            }
            this.currentPos = index - 1;
        }

        @Override
        public void remove() {
            if (!actionAllowed) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(currentPos);
            actionAllowed = false;
        }

        @Override
        public boolean hasNext() {
            int nextPos = currentPos == null ? 0 : currentPos + 1;
            if (nextPos < list.length) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentPos = currentPos == null ? 0 : currentPos + 1;
            actionAllowed = true;
            return (T) list[currentPos];
        }

        @Override
        public boolean hasPrevious() {
            if (currentPos == null || currentPos == 0) {
                return false;
            }
            return true;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            actionAllowed = true;
            return (T) list[--currentPos];
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                return size;
            }
            return currentPos + 1;
        }

        @Override
        public int previousIndex() {
            if (!hasPrevious()) {
                return -1;
            }
            return currentPos - 1;
        }

        @Override
        public void set(T t) {
            if (!actionAllowed) {
                throw new IllegalStateException();
            }
            list[currentPos] = t;
        }

        @Override
        public void add(T t) {
            int insertPos = currentPos == null ? 0 : currentPos;
            MyArrayList.this.add(insertPos, t);
        }
    };

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName() + "@" + Integer.toHexString(hashCode()));
        sb.append(newLine).append("Content: ").append(newLine);
        for (int i = 0; i < size; i++) {
            sb.append(i).append(": ").append(((T)list[i]).toString()).append(";").append(newLine);
        }
        return sb.toString();
    }
}
