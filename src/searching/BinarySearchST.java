package searching;

import fundanmentals.Queue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 3/8/17.
 * 3-2 有序符号表实现：二分查找(基于有序数组)
 * 数据结构是一对平行数组，缺点是插入操作很慢
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;             // 键值对数量

    public BinarySearchST() {
        this(2);
    }

    public BinarySearchST(int cap) {
        keys = (Key[]) new Comparable[cap];
        vals = (Value[]) new Object[cap];
    }

    // 表中存在该键返回该键的位置；反之返回表中小于该键的数量(相当于如果该key在keys里，它的位置)  [0, N]
    public int rank(Key key) {
        int lo = 0, hi = N - 1;    // 此处是N；不是keys.lengh
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp > 0) lo = mid + 1;
            else if (cmp < 0) hi = mid - 1;
            else return mid;
        }
        return lo;
    }

    // 符号表的put，get，delete，keys方法
    public void put(Key key, Value val) {
        // 保证符号表中任何键的值都不为空
        if (val == null) {
            delete(key);
            return;
        }
        // 已存在此键值；如果key不再keys里，有两种情况：一是很大，rank后等于N；二是一般大，rank后小于N，但是keys[i].compareTo(key) != 0
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {  // 注意不是 i >= 0 && i < N
            vals[i] = val;
            return;
        }
        if (N == keys.length) resize(2 * keys.length);
        // 新的键，在合适位置插入新的键值对
        for (int j = N; j > i; j--) {  // 整体后移：所以put方法很慢
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }

    public void delete(Key key) {
        if (isEmpty() || !contains(key)) return;
        int i = rank(key);
        for (int j = i; j < N - 1; j++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }
        N--;
        // 避免游离
        keys[N] = null;
        vals[N] = null;
        // resize 注意 N > 0
        if (N > 0 && N == keys.length / 4) resize(keys.length / 2);
    }

    public Iterable<Key> keys(Key lo, Key hi) {  // 此处lo，hi可以不在此
        Queue<Key> queue = new Queue<>();
        if (lo.compareTo(hi) > 0) return queue;  // 注意这种情况！
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    public Iterable<Key> keys() {
        return keys(keys[0], keys[N - 1]);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    // 以下均是有序符号表特有的方法
    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[N - 1];
    }

    public void deleteMin() {
        delete(min());
    }

    public void deleteMax() {
        delete(max());
    }

    public Key select(int k) {    // Return the kth smallest key in this symbol table
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("invalid argument");
        }
        return keys[k];
    }

    public Key floor(Key key) {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return keys[i];
        if (i == 0) return null;
        return keys[i - 1];
    }

    public Key ceiling(Key key) {
        int i = rank(key);
        if (i == N) return null;
        return keys[i];
    }

    public int size(Key lo, Key hi) {
        if (rank(lo) > rank(hi)) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    // 可变数组
    private void resize(int max) {
        Key[] tempKeys = (Key[]) new Comparable[max];
        Value[] tempVals = (Value[]) new Object[max];
        for (int i = 0; i < N; i++) {
            tempKeys[i] = keys[i];
            tempVals[i] = vals[i];
        }
        keys = tempKeys;
        vals = tempVals;
    }

    public static void main(String[] args) throws IOException {
        // 输入8，输出business 122
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tale.txt"));
        Scanner sc2 = new Scanner(System.in);
        int minlen = sc2.nextInt();  // 最小键长
        BinarySearchST<String, Integer> st = new BinarySearchST<>();
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