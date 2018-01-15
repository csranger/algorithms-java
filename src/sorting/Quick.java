package sorting;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hailong on 3/3/17.
 * 2-6 快速排序：也是一种分治的排序方法
 * 和归并排序很像，只是先处理数组，再分别排序左右两半部分的子数组。
 */
public class Quick {

    // 核心部分：原地切分，排定切分元素a[lo]的位置，同时左侧元素均小于等于a[lo]右侧元素均大于等于a[lo]
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) if (i == hi) break; // 找到索引为i的元素大于等于v
            while (less(v, a[--j])) if (j == lo) break; // 找到索引为j的元素小于等于v；if (j == lo)可省略
            if (i >= j) break;                          // 循环结束条件；不能改成 while (i < j)
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    // 核心部分：递归(所以需要 lo hi 用来不断缩小范围，递归才能结束)
    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;    // ＝ 不能少！！递归结束条件；这部分和Merge很相似，只是先对数组进行"处理"，再分别左右两部分递归排序
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);       // 相比归并排序多了一个打乱数组元素顺序，用于消除对输入的影响
        sort(a, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In("/Users/hailong/Documents/Data/algs4-data/words3.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
