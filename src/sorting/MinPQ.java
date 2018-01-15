package sorting;

/**
 * Created by hailong on 3/4/17.
 * 2-8 基于堆的优先队列：堆实现
 * 核心实现是 删除最大元素delMin() 和 插入元素insert(Key v)
 * 相比于MaxPQ，将 less 改成 greater；实现由pq[i].compareTo(pq[j]) < 0 改成 pq[i].compareTo(pq[j]) > 0 即可
 */
public class MinPQ<Item extends Comparable<Item>> {  // 放入优先队列的元素必须实现 Comparable 接口
    private Item[] pq;       // 完全二叉树
    private int N;           // 元素数量，存储于 pq[1..N]中，pq[0]不使用

    public MinPQ(int cap) {
        pq = (Item[]) new Comparable[cap + 1];
        N = 0;
    }

    public MinPQ() {
        this(1);
    }

    public void insert(Item v) {
        if (N == pq.length - 1) resize(2 * pq.length);
        pq[++N] = v;
        swim(N);
    }

    public Item delMin() {
        Item m = pq[1];
        exch(1, N--);
        pq[N + 1] = null;    // 消除游离，注意
        sink(1);
        if (N > 0 && N == (pq.length - 1) / 4) resize(pq.length / 2);
        return m;
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

    private void resize(int cap) {
        Item[] temp = (Item[]) new Comparable[cap];     // 注意是Comparable[]不是Object[]
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    private void swim(int k) {        // 上浮：交换某个节点与其父节点使得堆有序
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (N >= 2 * k) {
            int j = 2 * k;
            if (N > j && greater(2 * k, 2 * k + 1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
        Item t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
}
