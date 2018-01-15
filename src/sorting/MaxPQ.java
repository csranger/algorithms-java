package sorting;

import fundanmentals.Stack;
import fundanmentals.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 3/4/17.
 * 2-8 基于堆的优先队列：堆实现
 * 核心实现是 删除最大元素delMax() 和 插入元素insert(Key v)
 */
public class MaxPQ<Item extends Comparable<Item>> {  // 放入优先队列的元素必须实现 Comparable 接口
    private Item[] pq;       // 完全二叉树
    private int N;           // 元素数量，存储于 pq[1..N]中，pq[0]不使用

    public MaxPQ(int cap) {
        pq = (Item[]) new Comparable[cap + 1];
        N = 0;
    }

    public MaxPQ() {
        this(1);
    }

    public void insert(Item item) {
        if (N == pq.length - 1) resize(2 * pq.length);
        pq[++N] = item;
        swim(N);
    }

    public Item delMax() {
        Item max = pq[1];
        exch(1, N--);
        pq[N + 1] = null;    // 消除游离，注意
        sink(1);
        if (N > 0 && N == (pq.length - 1) / 4) resize(pq.length / 2);
        return max;
    }

    public Item max() {
        return pq[1];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    // 工具函数
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Item t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    private void swim(int k) {        // 上浮：交换某个节点与其父节点使得堆有序
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (N >= 2 * k) {
            int j = 2 * k;
            if (N > j && less(2 * k, 2 * k + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Comparable[max];     // 注意是Comparable[]不是Object[]
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public static void main(String[] args) throws IOException {
        // 输入N个字符串，每个字符串都对应一个整数，从中找出最小的 M 个整数(及其关联的字符串)
        // 例如标准输入5，会打印最小的5行
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        MaxPQ<Transaction> pq = new MaxPQ<>(M + 1);
        Scanner sc2 = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tinyBatch.txt"));
        while (sc2.hasNextLine()) {
            pq.insert(new Transaction(sc2.nextLine()));
            if (pq.size() > M) {
                pq.delMax();
            }
        }
        Stack<Transaction> stack = new Stack<>();
        while (!pq.isEmpty()) {
            stack.push(pq.delMax());
        }
        for (Transaction t : stack) {
            System.out.println(t);
        }
    }
}
