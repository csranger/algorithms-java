package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 3/5/17.
 * 2-10 堆排序
 * 经典而优雅的排序方法
 */
public class Heap {

    // 堆排序用到的3个工具方法：sink less exch方法
    private static boolean less(Comparable[] a, int i, int j) {  // 注意：二叉树的位置等于数组中位置加 1
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {     // 注意：二叉树的位置等于数组中位置加 1
        Comparable t = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = t;
    }

    private static void sink(Comparable[] a, int k, int n) {     // 不变
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(a, j, j + 1)) j++;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    public static void sort(Comparable[] a) {
        // 堆的构造：将长度为N的数组变成堆有序的数组
        int n = a.length;    // 基于堆的完全二叉树元素数量
        for (int k = n / 2; k >= 1; k--) sink(a, k, n);
        // 下沉排序：删除堆中最大元素，放入堆缩小后数组中空出的位置
        while (n > 1) {
            exch(a, 1, n--);
            sink(a, 1, n);
        }
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a, i + 1, i)) return false;
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
