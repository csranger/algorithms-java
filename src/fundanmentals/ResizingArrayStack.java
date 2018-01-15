package fundanmentals;

import java.util.Iterator;

/**
 * Created by hailong on 2/26/17.
 * 1-4 下压堆栈数据类型：数组实现
 * 可变数组＋实现泛型＋可迭代
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;         // 使用数组放入栈元素
    private int N;            // 栈元素数量

    public ResizingArrayStack() {
        a = (Item[]) new Object[2];
        N = 0;
    }

    public void push(Item item) {
        if (N == a.length) resize(2 * a.length);
        a[N++] = item;
    }

    public Item pop() {
        Item item = a[--N];
        a[N] = null;
        if (N > 0 && N == a.length / 4) resize(a.length / 2);   // 使用的是 == 判断，注意 N>0，防止数组大小为 0
        return item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = N;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            return a[--i];
        }

        public void remove() {
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<>();
        stack.push("Java");
        stack.push("Python");
        stack.push("C++");
        for (String s : stack) {
            System.out.println(s);
        }
    }
}
