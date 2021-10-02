import java.util.*;

public class LinkedList<T> implements List<T> {
    private Node<T> first = null;
    private Node<T> last = null;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T item) {
        Node<T> node = new Node<>();
        node.value = item;

        if (first == null) {
            first = node;
        } else {
            node.prev = last;
            last.next = node;
        }

        last = node;

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

        Node<T> current = getNode(index);
        Node<T> prev = current.prev;

        Node<T> node = new Node<>();
        node.value = item;
        node.prev = prev;
        node.next = current;

        if (prev == null) {
            first = node;
        } else {
            prev.next = node;
        }

        current.prev = node;

        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> source) {
        if (source.isEmpty()) {
            return false;
        }

        for (T item: source) {
            add(item);
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

        Node<T> current = getNode(index);
        Node<T> prev = current.prev;

        // Create sublist

        Node<T> firstNode = null;
        Node<T> lastNode = null;

        for (T item: source) {
            Node<T> node = new Node<>();
            node.value = item;

            if (firstNode == null) {
                firstNode = node;
            } else {
                node.prev = lastNode;
                lastNode.next = node;
            }

            lastNode = node;
        }

        // Insert the sublist

        if (prev == null) {
            first = firstNode;
        } else {
            firstNode.prev = prev;
            prev.next = firstNode;
        }

        lastNode.next = current;
        current.prev = lastNode;

        size += source.size();

        return true;
    }

    @Override
    public T set(int index, T item) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        Node<T> node = getNode(index);

        T oldItem = node.value;
        node.value = item;

        return oldItem;
    }

    @Override
    public T get(int index) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public boolean contains(Object obj) {
        T item = (T) obj;
        Node<T> node = findNode(item);
        return node != null;
    }

    @Override
    public boolean containsAll(Collection<?> items) {
        for (Object obj: items) {
            if (!this.contains(obj)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object obj) {
        int index = 0;

        for (Node<T> node = first; node != null; node = node.next) {
            if (node.value.equals(obj)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        int index = size - 1;

        for (Node<T> node = last; node != null; node = node.prev) {
            if (node.value.equals(obj)) {
                return index;
            }

            index--;
        }

        return -1;
    }

    @Override
    public boolean remove(Object obj) {
        T item = (T) obj;
        Node<T> node = findNode(item);

        if (node == null) {
            return false;
        }

        removeNode(node);

        return true;
    }

    @Override
    public T remove(int index) {
        if (isInvalidIndex(index)) {
            String message = getIndexOutOfBoundsMessage(index);
            throw new IndexOutOfBoundsException(message);
        }

        Node<T> node = getNode(index);
        removeNode(node);
        return node.value;
    }

    @Override
    public boolean removeAll(Collection<?> items) {
        if (isEmpty() || items.isEmpty()) {
            return false;
        }

        boolean result = false;

        for (Node<T> node = first; node != null; node = node.next) {
            if (items.contains(node.value)) {
                removeNode(node);
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection<?> items) {
        if (isEmpty() || items.isEmpty()) {
            return false;
        }

        boolean result = false;

        for (Node<T> node = first; node != null; node = node.next) {
            if (!items.contains(node.value)) {
                removeNode(node);
                result = true;
            }
        }

        return result;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int index = 0;

        for (Node<T> node = first; node != null; node = node.next) {
            arr[index++] = node.value;
        }

        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = (T1[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size
            );
        }

        Object[] result = a;
        int index = 0;

        for (Node<T> node = first; node != null; node = node.next) {
            result[index++] = node.value;
        }

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
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new LinkedListIterator(index);
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

    private Node<T> getNode(int index) {
        Node<T> node;

        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }

        return node;
    }

    private Node<T> findNode(T item) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.value.equals(item)) {
                return node;
            }
        }

        return null;
    }

    private void removeNode(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null && next == null) {
            first = null;
            last = null;
        } else if (prev == null) {
            next.prev = null;
            first = next;
        } else if (next == null) {
            prev.next = null;
            last = prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }

        size--;
    }

    // Iterator

    private class LinkedListIterator implements ListIterator<T> {
        private int prevCursor;
        private int nextCursor;
        private Node<T> prevNode;
        private Node<T> nextNode;

        private ShiftDirection lastShiftDirection;
        private boolean modificationProhibited = true;

        LinkedListIterator() {
            this(0);
        }

        LinkedListIterator(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index > size) {
                String message = getIndexOutOfBoundsMessage(index);
                throw new IndexOutOfBoundsException(message);
            }

            prevCursor = index - 1;
            nextCursor = index;

            if (size == 0) {
                return;
            }

            if (nextCursor < size) {
                nextNode = getNode(nextCursor);
                prevNode = nextNode.prev;
            } else {
                prevNode = getNode(prevCursor);
                nextNode = prevNode.next;
            }
        }

        @Override
        public boolean hasNext() {
            return nextCursor < size;
        }

        @Override
        public T next() {
            if (isInvalidIndex(nextCursor)) {
                throw new NoSuchElementException();
            }

            T item = nextNode.value;

            shiftRight();
            modificationProhibited = false;

            return item;
        }

        @Override
        public boolean hasPrevious() {
            return prevCursor >= 0;
        }

        @Override
        public T previous() {
            if (isInvalidIndex(prevCursor)) {
                throw new NoSuchElementException();
            }

            T item = prevNode.value;

            shiftLeft();
            modificationProhibited = false;

            return item;
        }

        @Override
        public int nextIndex() {
            return nextCursor;
        }

        @Override
        public int previousIndex() {
            return prevCursor;
        }

        @Override
        public void remove() {
            if (modificationProhibited) {
                throw new IllegalStateException();
            }

            if (lastShiftDirection == ShiftDirection.LEFT) {
                Node<T> node = nextNode;
                nextNode = nextNode.next;
                removeNode(node);
            } else {
                Node<T> node = prevNode;
                prevNode = prevNode.prev;
                removeNode(node);

                prevCursor--;
                nextCursor--;
            }

            modificationProhibited = true;
        }

        @Override
        public void set(T item) {
            if (modificationProhibited) {
                throw new IllegalStateException();
            }

            if (lastShiftDirection == ShiftDirection.LEFT) {
                nextNode.value = item;
            } else {
                prevNode.value = item;
            }
        }

        @Override
        public void add(T item) {
            Node<T> node = new Node<>();
            node.value = item;
            node.prev = prevNode;
            node.next = nextNode;

            if (prevNode != null) {
                prevNode.next = node;
            }

            if (nextNode != null) {
                nextNode.prev = node;
            }

            if (nextCursor == 0) {
                first = node;
            }

            if (nextCursor == size) {
                last = node;
            }

            size++;

            prevCursor++;
            nextCursor++;
            prevNode = node;

            modificationProhibited = true;
        }

        private void shiftLeft() {
            prevCursor--;
            nextCursor--;
            nextNode = prevNode;
            prevNode = prevNode.prev;
            lastShiftDirection = ShiftDirection.LEFT;
        }

        private void shiftRight() {
            prevCursor++;
            nextCursor++;
            prevNode = nextNode;
            nextNode = nextNode.next;
            lastShiftDirection = ShiftDirection.RIGHT;
        }
    }

    private enum ShiftDirection { LEFT, RIGHT }
}
