package searching;

import fundanmentals.Queue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 3/19/17.
 * 3-6 散列表：基于线性探测法的散列表
 * 主要实现：resize(注意temp是散列表，不是数组，因为hash值已经改变: M/N 在2～8 )，put(注意遍历的方法)，get(注意遍历的方法)，
 * delete(注意删除一个键值对后，使用put方法更新后面的键值对！)，keys
 */
public class LinearProbingHashST<Key, Value> {
    private int N;          // 符号表中键值对总数
    private int M;          // 线性探测表的大小，就是数组大小
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() {
        this(4);
    }

    public LinearProbingHashST(int M) {
        this.M = M;
        N = 0;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key key) {                  // 散列函数
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int max) {
        // 注意 temp 是散列表，不是数组！这是与之前resize不同的地方
        // 不使用数组resize是因为当M改变，键的hash值会变，原来链表所在数组位置也会发生变化！
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(max);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        M = temp.M;
        keys = temp.keys;
        vals = temp.vals;
    }

    // put, get, delete, keys 方法
    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {    // 找到 i 使得 keys[i] = null
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {   // 不会死循环，因为 M/N > 2，肯定有null存在
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        // 找到key所在位置 i
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        // 删除键值对
        keys[i] = null;
        vals[i] = null;
        // 更新键值：注意先删除在重新put
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key tempKey = keys[i];
            Value tempVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(tempKey, tempVal);
            i = (i + 1) % M;
        }
        N--;
        // resize
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
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
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>();
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
