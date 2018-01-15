package sorting;

/**
 * Created by hailong on 5/9/17.
 * 2-9 索引优先队列：看成一个能快速访问其中最大元素的数组，显然通过keys数组即可办到
 * 修改优先队列 MaxPQ 代码实现索引优先队列 IndexMaxPQ
 */
public class IndexMaxPQ<Item extends Comparable<Item>> {
    private int N;           // 优先队列中元素数量
    private int[] pq;        // 索引二叉堆: 元素索引的索引 -> 索引，把索引当成优先队列存储的对象  特别注意索引是不会因为交换而改变！！
    private Item[] items;    // 索引 -> 元素
    private int[] qp;        // 索引 -> 元素索引的索引

    public IndexMaxPQ(int cap) { // 索引的取值范围［0， cap - 1］
        N = 0;
        pq = new int[cap + 1];
        qp = new int[cap + 1];
        items = (Item[]) new Comparable[cap + 1];
        for (int i = 0; i <= cap; i++) {      // 初始化时队列中没有任何元素，索引对应值为 -1
            qp[i] = -1;
        }
    }

    // 4个核心方法：insert delMax change delete
    public void insert(int k, Item item) {
        N++;
        pq[N] = k;
        qp[k] = N;
        items[k] = item;
        swim(N);
    }

    public int delMax() {
        int max = pq[1];      // 最大值元素在items数组中的索引，索引是不会改变的
        exch(1, N--);         // 删除最大值元素索引
        sink(1);
        qp[max] = -1;         // 删除最大值元素索引的索引
        items[max] = null;    // 删除最大值元素
        return max;
    }

    public void change(int k, Item item) {   // 索引 k 为 k 的元素设为item
        items[k] = item;
        swim(qp[k]);    // 更新它在队列(二叉堆)中位置
        sink(qp[k]);
    }

    public void delete(int k) {   // 和delMax是一样的，del删的元素索引是pq[1]，delete删的元素索引是k
        int index = qp[k];
        exch(index, N--);
        sink(index);
        swim(index);
        qp[k] = -1;       // 无论如何交换，元素索引是不会改变的，依然是k!!
        items[k] = null;
    }

    // 最大值相关方法
    public int maxIndex() {
        return pq[1];
    }

    public Item max() {
        return items[pq[1]];
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

    // 4个辅助函数 less exch swim sink（swim sink 相比MaxPQ类没有变化）
    private boolean less(int i, int j) {  
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }

    private void exch(int i, int j) {      // 很重要，很容易错
        int t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (2 * k < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
