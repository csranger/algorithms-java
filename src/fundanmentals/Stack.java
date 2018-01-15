package fundanmentals;

import java.util.Iterator;

/**
 * Created by hailong on 2/26/17.
 * 1-5 下压堆栈：链表实现
 * first成员变量，将元素push到表头，迭代时从first开始，所以先进后出
 */
public class Stack<Item> implements Iterable<Item> {
    private Node first;        // 栈顶
    private int N;             // 元素数量

    private class Node {
        private Item item;
        private Node next;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public Stack() {
        first = null;
        N = 0;
    }

    public void push(Item item) {   // 将元素push到表头
        Node oldfirst = first;
        first = new Node(item, oldfirst);
        N++;
    }

    public Item pop() {
        Item item = first.item;        // 注意：外部类可以访问内部类的私有属性！
        first = first.next;
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

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
        }
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("Java");
        stack.push("Python");
        stack.push("C++");
        for (String s : stack) {
            System.out.println(s);
        }
    }
}
