package fundanmentals;

import java.util.Iterator;

/**
 * Created by hailong on 2/26/17.
 * 1-6 先进先出队列：链表实现
 * first 和 last 两个成员变量，将元素enqueue到表尾，迭代时从first开始，所以先进后出
 * 完全熟练同时存在first和last时链表的使用！
 */
public class Queue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    private class Node {
        private Node next;
        private Item item;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public void enqueue(Item item) {      // 将元素放到表尾
        Node oldlast = last;
        last = new Node(item, null);
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    public Item dequeue() {         // 从表头删除元素
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
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

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.enqueue("Java");
        queue.enqueue("C++");
        queue.enqueue("Python");
        for (String s : queue) {
            System.out.println(s);
        }
    }
}
