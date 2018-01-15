package fundanmentals;

import java.util.Iterator;

/**
 * Created by hailong on 2/26/17.
 * 1-7 背包：链表实现
 * 将Stack中的push改成add，并去掉pop实现即可
 */
public class Bag<Item> implements Iterable<Item> {
    private Node first;
    private int N;

    private class Node {
        private Item item;
        private Node next;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public void add(Item item) {   // 在表头增加
        Node oldfirst = first;
        first = new Node(item, oldfirst);
        N++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Bag<Integer> bag = new Bag<>();
        bag.add(1);
        bag.add(2);
        bag.add(1);
        bag.add(4);
        for (Integer i : bag) {
            System.out.println(i);
        }
    }
}
