import java.util.*;

public class ArrayList<T> implements List<T> {
    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    private Object[] items;
    private int size;

    ArrayList() {
        items = new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size + 1);

        items[size] = t;
        size++;

        return true;
    }

    @Override
    public void add(int index, T item) {
        if (isInvalidIndexToInsert(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        if (index == size) {
            add(item);
            return;
        }

        ensureCapacity(size + 1);

        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;

        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> source) {
        if (source.isEmpty()) {
            return false;
        }

        ensureCapacity(size + source.size());

        for (T item: source) {
            items[size++] = item;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> source) {
        if (isInvalidIndexToInsert(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        if (source.isEmpty()) {
            return false;
        }

        if (index == size) {
            return addAll(source);
        }

        ensureCapacity(size + source.size());

        Object[] temp = new Object[size - index];
        System.arraycopy(items, index, temp, 0, size - index);

        for (T item: source) {
            items[index++] = item;
        }

        System.arraycopy(temp, 0, items, index, temp.length);

        size = size + source.size();

        return true;
    }

    @Override
    public T set(int index, T item) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        T oldItem = (T) items[index];
        items[index] = item;

        return oldItem;
    }

    @Override
    public T get(int index) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        return (T) items[index];
    }

    @Override
    public boolean contains(Object obj) {
        for (int i = 0; i < size; i++) {
            Object item = items[i];
            if (item.equals(obj)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> elements) {
        for (Object obj: elements) {
            if (!this.contains(obj)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object obj) {
        for (int i = 0; i < size; i++) {
            Object item = items[i];
            if (item.equals(obj)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        for (int i = size - 1; i >= 0; i--) {
            Object item = items[i];
            if (item.equals(obj)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean remove(Object obj) {
        for (int i = 0; i < size; i++) {
            Object item = items[i];
            if (item.equals(obj)) {
                removeItem(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public T remove(int index) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        T item = (T) items[index];
        removeItem(index);
        return item;
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        if (isEmpty() || elements.isEmpty()) {
            return false;
        }

        return batchRemove(elements, true);
    }

    @Override
    public boolean retainAll(Collection<?> elements) {
        if (isEmpty() || elements.isEmpty()) {
            return false;
        }

        return batchRemove(elements, false);
    }

    @Override
    public void clear() {
        items = new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(items, 0, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }

        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null; // TODO
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ArrayListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    // Auxiliary methods

    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= size;
    }

    private boolean isInvalidIndexToInsert(int index) {
        return index < 0 || index > size;
    }

    private String getIndexOutOfBoundsMessage(int index) {
        return "index: " + index + ", size: " + size;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > items.length) {
            int newCapacity = getNewCapacity(items.length, minCapacity);
            grow(newCapacity);
        }
    }

    private int getNewCapacity(int oldCapacity, int minCapacity) {
        int minGrowth = minCapacity - oldCapacity;
        int prefGrowth = oldCapacity >> 1;
        return oldCapacity + Math.max(minGrowth, prefGrowth);
    }

    private void grow(int capacity) {
        Object[] arr = new Object[capacity];
        System.arraycopy(items, 0, arr, 0, size);
        items = arr;
    }

    private void removeItem(int index) {
        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        size -= 1;
    }

    private boolean batchRemove(Collection<?> elements, boolean removeIfContains) {
        Object[] newItems = new Object[items.length];
        int index = 0;
        int removedCount = 0;

        for (Object item: items) {
            if (item == null) {
                break;
            }

            if (elements.contains(item) == removeIfContains) {
                removedCount++;
            } else {
                newItems[index++] = item;
            }
        }

        if (removedCount > 0) {
            items = newItems;
            size -= removedCount;
            return true;
        }

        return false;
    }

    // Iterator

    private class ArrayListIterator implements ListIterator<T> {
        private int cursor;

        private ArrayList.ShiftDirection lastShiftDirection;
        private boolean modificationProhibited = true;

        ArrayListIterator() {
            this(0);
        }

        ArrayListIterator(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index > size) {
                String message = getIndexOutOfBoundsMessage(index);
                throw new IndexOutOfBoundsException(message);
            }

            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (isInvalidIndex(cursor)) {
                throw new NoSuchElementException();
            }

            lastShiftDirection = ShiftDirection.RIGHT;
            modificationProhibited = false;

            return (T) items[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor - 1 >= 0;
        }

        @Override
        public T previous() {
            if (isInvalidIndex(cursor - 1)) {
                throw new NoSuchElementException();
            }

            lastShiftDirection = ShiftDirection.LEFT;
            modificationProhibited = false;

            return (T) items[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (modificationProhibited) {
                throw new IllegalStateException();
            }

            if (lastShiftDirection == ShiftDirection.LEFT) {
                ArrayList.this.remove(cursor);
            } else {
                ArrayList.this.remove(cursor - 1);
                cursor--;
            }

            modificationProhibited = true;
        }

        @Override
        public void set(T item) {
            if (modificationProhibited) {
                throw new IllegalStateException();
            }

            if (lastShiftDirection == ShiftDirection.LEFT) {
                ArrayList.this.set(cursor, item);
            } else {
                ArrayList.this.set(cursor - 1, item);
            }
        }

        @Override
        public void add(T item) {
            ArrayList.this.add(cursor, item);
            cursor++;

            modificationProhibited = true;
        }
    }

    private enum ShiftDirection { LEFT, RIGHT }
}
