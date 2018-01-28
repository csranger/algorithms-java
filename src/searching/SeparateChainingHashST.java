package searching;

import fundanmentals.Queue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 3/18/17.
 * 3-5 散列表：基于拉链法的散列表，使用最为广泛的散列表实现
 * 主要实现：resize(与之前的BinarSearchST不同:使得平均链表长度 N/M 在2～10之间), hash(利用hashCode方法), get, put(先resize再put), delete(先delete再resize), keys
 * 其余的方法 size, isEmpty, contains
 */
public class SeparateChainingHashST<Key, Value> {
    private int N;          // 键值对数量
    private int M;          // 数组大小，一般使用数组时不将数组大小放出来，因为其等于st.length，比如BinarySearchST；这里放出来是因为便于hash，resize等方法的使用
    private SequentialSearchST<Key, Value>[] st;    // 存放符号表对象的数组，符号表对象是基于无序链表实现

    public SeparateChainingHashST() {
        this(4);
    }

    public SeparateChainingHashST(int M) {
        // 创建 M 条链表，数组大小为 M
        this.M = M;
        // 注意new一个对象数组是不能使用泛型，但声明时是可以的: Key[] keys = (Key[]) Comparable[M]; 来自BinarySearchST
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<Key, Value>();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int max) {
        // 注意 temp 是散列表，不是数组！这是与之前resize不同的地方
        // 不使用数组resize是因为当M改变，键的hash值会变，原来链表所在数组位置也会发生变化！
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(max);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        // 利用temp更新所有属性
        this.N = temp.N;
        this.M = temp.M;
        this.st = temp.st;
    }

    public Value get(Key key) {
        return st[hash(key)].get(key);
    }

    public void put(Key key, Value val) {
        // val == null ?
        if (val == null) {
            delete(key);
            return;
        }
        // resize
        if (N >= 10 * M) {                // 如果平均链表长度大于等于10，更新数组／散列表的大小：这种实现使得平均链表长度不超过10
            resize(2 * M);
        }
        // put: 需要注意 N++ 情况
        int i = hash(key);
        if (!st[i].contains(key)) {
            N++;
        }
        st[i].put(key, val);
    }

    public void delete(Key key) {
        int i = hash(key);
        if (st[i].contains(key)) {
            N--;
        }
        st[i].delete(key);
        if (M > 4 && N <= M * 2) {     // 如果平均链表长度小于等于 2，更新数组／散列表的大小；4是初始数组大小
            resize(M / 2);
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    // 其余的方法 size, isEmpty, contains
    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    public boolean contains(Key key) {
        return get(key) != null;
    }

    public static void main(String[] args) throws IOException {
        // 输入8，输出business 122
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tale.txt"));
        Scanner sc2 = new Scanner(System.in);
        int minlen = sc2.nextInt();  // 最小键长
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<>();
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
