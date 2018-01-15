package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 3/2/17.
 * 2-4 归并排序：复杂度 NlogN
 * 特点：将一个数组排序，可以先递归的将它分成两半分别排序，然后将结果归并起来
 * 递归实现的归并排序是算法设计中分治思想的典型应用：将一个大问题分割成小问题分别解决，然后用小问题的答案来解决整个大问题
 */
public class Merge {
    private static Comparable[] aux;  // 归并所需要的辅助数组

    // 核心部分：原地归并，在数组中移动元素而不需要使用额外的空间。
    // 可将数组的某一部分 a[lo, hi] 进行归并 a[lo, mid] a[mid+1, hi] 排序
    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;   //  i, j 游标，两个左右扫描指针，用来遍历 aux 辅助数组，进行比较
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];   // 注意比较的是aux[i]与aux[j]不是a[i]和a[j]
            else a[k] = aux[j++];
        }
    }

    // 核心部分：递归(所以需要 lo hi 用来不断缩小范围，递归才能结束)
    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;           // 递归结束条件!!
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);               // 将左半边排序
        sort(a, mid + 1, hi);       // 将右半边排序
        merge(a, lo, mid, hi);          // 归并结果
    }

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];      // 相比快速排序多了一个辅助数组
        sort(a, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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
