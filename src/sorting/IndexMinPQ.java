package sorting;

/**
 * Created by hailong on 5/9/17.
 * 2-9 索引优先队列：看成一个能快速访问其中最大元素的数组，允许用例引用已经进入优先队列中的元素
 * 修改优先队列 MinPQ 代码实现索引优先队列 IndexMinPQ
 */
public class IndexMinPQ<Item extends Comparable<Item>> {
    private int N;           // 优先队列中元素数量
    private int[] pq;        // 索引二叉堆，保存索引；核心数据结构，真正比较的是items[pq[i]] items[pq[j]]
    private int[] qp;        // 逆序 qp[pq[i]] = pq[qp[i]] = i ；若i不在队列中，则总是令qp[i] = -1
    private Item[] items;    // 保存元素，它的索引保存在 pq 二叉堆里

    public IndexMinPQ(int cap) {
        N = 0;
        pq = new int[cap + 1];
        qp = new int[cap + 1];
        items = (Item[]) new Comparable[cap + 1];
        for (int i = 0; i <= cap; i++) {      // 初始化时队列中没有任何元素，索引对应值为 －1
            qp[i] = -1;
        }
    }

    // 4个辅助函数 greater exch swim sink（swim sink 相比MaxPQ类没有变化）
    private boolean greater(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (2 * k < N && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    // 简单方法
    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    // 4个核心方法：insert delMax change delete
    public void insert(int k, Item item) { // k是item的索引，所以 pq[N] = k (insert都是在对的末尾添加，然后swim)
        N++;
        pq[N] = k;
        qp[k] = N;
        items[k] = item;
        swim(N);
    }

    public int delMin() {
        int min = pq[1];      // 最大值在items数组中的索引
        exch(1, N--);
        sink(1);
        qp[min] = -1;         // 删除，contains使用qp来判断是否在队列中
        items[min] = null;    // 防止游离，垃圾回收
        return min;
    }

    public void change(int k, Item item) {   // 索引 k 为 k 的元素设为item
        items[k] = item;
        swim(qp[k]);    // 更新它在队列(二叉堆)中位置
        sink(qp[k]);
    }

    public void delete(int k) {
        int index = qp[k];
        exch(index, N--);
        sink(index);
        swim(index);
        qp[k] = -1;
        items[k] = null;
    }

    // 最大值相关方法
    public int minIndex() {
        return pq[1];
    }

    public Item min() {
        return items[pq[1]];
    }

}
