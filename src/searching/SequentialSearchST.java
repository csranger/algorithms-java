package searching;

import fundanmentals.Queue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 3/6/17.
 * 3-1 初级符号表实现 无序符号表：顺序查找(基于无序链表)
 * 数据结构是链表
 */
public class SequentialSearchST<Key, Value> {
    private int N;          // 键值对数量
    private Node first;     // 链表首结点

    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public SequentialSearchST() {
    }

    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        // 如果不存在，在表头插入一个新的节点
        first = new Node(key, val, first);
        N++;
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
    }

    public void delete(Key key) {
        first = delete(first, key);   // 将key所在节点的下一个节点设为first
    }

    // 理解delete(Node x, Key key)返回的是以 x 为首结点且删除key结点后的链表，递归实现，链表中十分常见
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            N--;
            return x.next;
        }
        x.next = delete(x.next, key); // 递归
        return x;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();    // 因为优先队列 Queue<Key> implements Iterator<Key>
        for (Node x = first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }
        return queue;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    // 符号表典型用例：统计了标准输入中各个单词的出现频率
    public static void main(String[] args) throws IOException {
        // 输入8，输出business 122
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tale.txt"));
        Scanner sc2 = new Scanner(System.in);
        int minlen = sc2.nextInt();  // 最小键长
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        while (sc.hasNext()) {
            String word = sc.next();
            if (word.length() < minlen) continue;   // 忽略小于长度 minlen 的字符串
            if (!st.contains(word)) st.put(word, 1);
            else st.put(word, st.get(word) + 1);
        }
        // 找出频率出现最高的单词
        String max = " ";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }
        System.out.println(max + " " + st.get(max));
    }
}
