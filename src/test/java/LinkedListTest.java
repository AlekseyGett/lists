import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

@DisplayName("LinkedList test")
public class LinkedListTest {
    @Test
    @DisplayName("Should add single item and update the size")
    void addSingleItem() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(2, list.get(1));
        Assertions.assertEquals(3, list.get(2));
    }

    @Test
    @DisplayName("Should insert single item at specific position and update the size")
    void addSingleItemByIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(3);

        // Insert at the end
        list.add(1, 4);

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(4, list.get(1));

        // Insert at the beginning
        list.add(0, 1);

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, list.get(0));

        // Insert at the middle
        list.add(1, 2);

        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals(2, list.get(1));

        // Insert outside the list bounds
        Exception e = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
           list.add(10, 5);
        });

        Assertions.assertEquals("index: 10, size: 4", e.getMessage());
    }

    @Test
    @DisplayName("Should add items of a given collection at the end")
    void addItems() {
        LinkedList<Integer> list = new LinkedList<>();

        Assertions.assertEquals(0, list.size());

        // Insert an empty collection
        Collection<Integer> source = new ArrayList<>();
        boolean result = list.addAll(source);

        Assertions.assertFalse(result);
        Assertions.assertEquals(0, list.size());

        // Insert non-empty collection
        source.add(1);
        source.add(2);
        source.add(3);

        result = list.addAll(source);

        Assertions.assertTrue(result);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(2, list.get(1));
        Assertions.assertEquals(3, list.get(2));
    }

    @Test
    @DisplayName("Should insert items of a given collection at specific position")
    void addItemsByIndex() {
        LinkedList<Integer> list = new LinkedList<>();

        // Insert an empty collection
        Collection<Integer> source = new ArrayList<>();
        boolean result = list.addAll(0, source);

        Assertions.assertFalse(result);
        Assertions.assertEquals(0, list.size());

        // Insert at the end
        source.add(5);
        source.add(6);

        result = list.addAll(0, source);

        Assertions.assertTrue(result);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(5, list.get(0));
        Assertions.assertEquals(6, list.get(1));

        // Insert at the beginning
        source.clear();
        source.add(1);
        source.add(2);

        result = list.addAll(0, source);

        Assertions.assertTrue(result);
        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(2, list.get(1));
        Assertions.assertEquals(5, list.get(2));
        Assertions.assertEquals(6, list.get(3));

        // Insert at the middle
        source.clear();
        source.add(3);
        source.add(4);

        result = list.addAll(2, source);

        Assertions.assertTrue(result);
        Assertions.assertEquals(6, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(2, list.get(1));
        Assertions.assertEquals(3, list.get(2));
        Assertions.assertEquals(4, list.get(3));
        Assertions.assertEquals(5, list.get(4));
        Assertions.assertEquals(6, list.get(5));

        // Insert outside the list bounds
        source.clear();
        source.add(7);
        source.add(8);

        Exception e = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.addAll(10, source);
        });

        Assertions.assertEquals("index: 10, size: 6", e.getMessage());
    }

    @Test
    @DisplayName("Should update value by index")
    void setItemByIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(0);

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(0, list.get(0));

        int oldItem = list.set(0, 1);

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(0, oldItem);
        Assertions.assertEquals(1, list.get(0));
    }

    @Test
    @DisplayName("Should return true if the list contains an object passed as a parameter")
    void containsObject() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Assertions.assertTrue(list.contains(1));
        Assertions.assertTrue(list.contains(2));
        Assertions.assertTrue(list.contains(3));
        Assertions.assertFalse(list.contains(4));
    }

    @Test
    @DisplayName("Should return true if the list contains all items of a collection passed as a parameter")
    void containsItemsOfCollection() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Collection<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);

        Assertions.assertTrue(list.containsAll(items));

        items.add(5);
        items.add(6);

        Assertions.assertFalse(list.containsAll(items));
    }

    @Test
    @DisplayName("Should return the first index of a given object in the list or -1")
    void indexOf() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);

        int index = list.indexOf(1);
        Assertions.assertEquals(0, index);

        index = list.indexOf(2);
        Assertions.assertEquals(2, index);

        index = list.indexOf(3);
        Assertions.assertEquals(4, index);

        index = list.indexOf(4);
        Assertions.assertEquals(-1, index);
    }

    @Test
    @DisplayName("Should return the last index of a given object in the list or -1")
    void lastIndexOf() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);

        int index = list.lastIndexOf(1);
        Assertions.assertEquals(1, index);

        index = list.lastIndexOf(2);
        Assertions.assertEquals(3, index);

        index = list.lastIndexOf(3);
        Assertions.assertEquals(5, index);

        index = list.lastIndexOf(4);
        Assertions.assertEquals(-1, index);
    }

    @Test
    @DisplayName("Should remove a given item from the list")
    void removeItem() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        boolean listChanged = list.remove(Integer.valueOf(2));

        Assertions.assertTrue(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));

        listChanged = list.remove(Integer.valueOf(2));

        Assertions.assertFalse(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));
    }

    @Test
    @DisplayName("Should remove an item from the list by index")
    void removeItemByIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        // Remove from the middle
        Integer removedItem = list.remove(1);

        Assertions.assertEquals(2, removedItem);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));
        Assertions.assertEquals(4, list.get(2));

        // Remove from the end
        removedItem = list.remove(2);

        Assertions.assertEquals(4, removedItem);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));

        // Remove from the beginning
        removedItem = list.remove(0);

        Assertions.assertEquals(1, removedItem);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(3, list.get(0));

        // Remove outside the list bounds
        Exception e = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(10);
        });

        Assertions.assertEquals("index: 10, size: 1", e.getMessage());
    }

    @Test
    @DisplayName("Should remove all items a given collection contains")
    void removeAllItems() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Collection<Integer> items = new ArrayList<>();
        items.add(2);
        items.add(4);

        boolean listChanged = list.removeAll(items);

        Assertions.assertTrue(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));

        listChanged = list.removeAll(items);

        Assertions.assertFalse(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(1, list.get(0));
        Assertions.assertEquals(3, list.get(1));
    }

    @Test
    @DisplayName("Should remove all items a given collection does not contain")
    void retainAllItems() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Collection<Integer> items = new ArrayList<>();
        items.add(2);
        items.add(4);

        boolean listChanged = list.retainAll(items);

        Assertions.assertTrue(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(2, list.get(0));
        Assertions.assertEquals(4, list.get(1));

        listChanged = list.retainAll(items);

        Assertions.assertFalse(listChanged);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(2, list.get(0));
        Assertions.assertEquals(4, list.get(1));
    }

    @Test
    @DisplayName("Should reset the list to initial state")
    void clear() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        Assertions.assertEquals(1, list.size());

        list.clear();

        Assertions.assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Should return an array of objects that contains all items of the list")
    void toArray() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Object[] array = list.toArray();

        Assertions.assertArrayEquals(new Object[] {1, 2, 3}, array);
    }

    @Test
    @DisplayName("Should return a typed array that contains all items of the list")
    void toTypedArray() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // With an array of less size than the list
        Number[] array = new Number[2];
        Number[] result = list.toArray(array);

        Assertions.assertNotSame(array, result);
        Assertions.assertEquals(3, result.length);
        Assertions.assertEquals(1, result[0]);
        Assertions.assertEquals(2, result[1]);
        Assertions.assertEquals(3, result[2]);

        // With an array of the same size as the list
        array = new Number[3];
        result = list.toArray(array);

        Assertions.assertSame(array, result);
        Assertions.assertEquals(3, result.length);
        Assertions.assertEquals(1, result[0]);
        Assertions.assertEquals(2, result[1]);
        Assertions.assertEquals(3, result[2]);

        // With an array of greater size than the list
        array = new Number[5];

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 10;
        }

        result = list.toArray(array);

        Assertions.assertSame(array, result);
        Assertions.assertEquals(5, result.length);
        Assertions.assertEquals(1, result[0]);
        Assertions.assertEquals(2, result[1]);
        Assertions.assertEquals(3, result[2]);
        Assertions.assertNull(result[3]);
        Assertions.assertEquals(14, result[4]);
    }

    @Test
    @DisplayName("TODO: subList test")
    void subList() {
        // TODO
    }

    @Test
    @DisplayName("Should return correct iterator that allows to iterate over the list")
    void listIterator() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ListIterator<Integer> iterator = list.listIterator();

        // * 1, 2, 3
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertFalse(iterator.hasPrevious());
        Assertions.assertEquals(0, iterator.nextIndex());
        Assertions.assertEquals(-1, iterator.previousIndex());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Integer item = iterator.previous();
        });

        Assertions.assertEquals(1, iterator.next());

        // *, 2, 3
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());
        Assertions.assertEquals(2, iterator.next());

        // 1, *, 3
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());
        Assertions.assertEquals(3, iterator.next());

        // 1, 2, *
        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(3, iterator.nextIndex());
        Assertions.assertEquals(2, iterator.previousIndex());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Integer item = iterator.next();
        });

        // 1, 2, *
        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(3, iterator.nextIndex());
        Assertions.assertEquals(2, iterator.previousIndex());
        Assertions.assertEquals(3, iterator.previous());

        // 1, *, 3
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());
        Assertions.assertEquals(2, iterator.previous());
    }

    @Test
    @DisplayName("Should return correct iterator with the cursor set to specific position")
    void listIteratorByIndex() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // With correct index
        ListIterator<Integer> iterator = list.listIterator(1);

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());
        Assertions.assertEquals(2, iterator.next());

        iterator.previous();

        Assertions.assertEquals(1, iterator.previous());

        // With index out of the list bounds
        Exception e = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            ListIterator<Integer> i = list.listIterator(10);
        });

        Assertions.assertEquals("index: 10, size: 3", e.getMessage());
    }

    @Test
    @DisplayName("Should remove current item and shift cursor")
    void remove() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        ListIterator<Integer> iterator = list.listIterator();

        // Remove before any next() or previous() calls
        Assertions.assertThrows(IllegalStateException.class, iterator::remove);

        // Remove from the beginning
        Assertions.assertEquals(1, iterator.next());
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());

        iterator.remove();

        Assertions.assertEquals(0, iterator.nextIndex());
        Assertions.assertEquals(-1, iterator.previousIndex());
        Assertions.assertEquals(2, iterator.next());

        // Remove from the middle
        Assertions.assertEquals(3, iterator.next());
        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());

        iterator.remove();

        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());
        Assertions.assertEquals(4, iterator.next());

        // Remove from the end
        Assertions.assertEquals(5, iterator.next());

        iterator.remove();

        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());
        Assertions.assertFalse(iterator.hasNext());

        // Remove after call to previous
        Assertions.assertEquals(4, iterator.previous());

        iterator.remove();

        Assertions.assertEquals(0, iterator.previousIndex());

        // Remove just after another call to remove()
        Assertions.assertThrows(IllegalStateException.class, iterator::remove);

        // Remove just after call to add()
        Assertions.assertEquals(2, iterator.previous());

        iterator.add(1);

        Assertions.assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Should replace current item with a given value")
    void iteratorSet() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ListIterator<Integer> iterator = list.listIterator();

        iterator.next();
        Assertions.assertEquals(2, iterator.next());

        iterator.set(5);
        Assertions.assertEquals(5, iterator.previous());
    }

    @Test
    @DisplayName("Should insert given item between previous and next items and shift cursor forward")
    void iteratorAdd() {
        LinkedList<Integer> list = new LinkedList<>();
        ListIterator<Integer> iterator = list.listIterator();

        // Insert into empty list
        iterator.add(3);

        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());
        Assertions.assertEquals(3, iterator.previous());

        // Insert at the beginning
        Assertions.assertFalse(iterator.hasPrevious());

        iterator.add(1);

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());
        Assertions.assertEquals(1, iterator.previous());

        // Insert at the middle
        iterator.next();
        iterator.add(2);

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());
        Assertions.assertEquals(2, iterator.previous());

        // Insert at the end
        iterator.next();
        iterator.next();
        iterator.add(4);

        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertTrue(iterator.hasPrevious());
        Assertions.assertEquals(4, iterator.nextIndex());
        Assertions.assertEquals(3, iterator.previousIndex());
        Assertions.assertEquals(4, iterator.previous());

        Assertions.assertEquals(4, list.size());
    }
}
