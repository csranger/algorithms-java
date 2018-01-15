package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 3/2/17.
 * 2-3 希尔排序：基于插入排序的快速的排序方法
 * 特点：使用插入排序的方法对 h-数组 进行排序；并逐渐降低 h 值到1
 * 相比于选择排序，插入排序，希尔排序可以用于大型数组
 */
public class Shell {
    public static void sort(Comparable[] a) {
        // 确定h的值
        int h = 1;
        while (h < a.length / 3) h = h * 3 + 1;
        // h值不断减小到1
        while (h >= 1) {
            // 使用插入排序的方法对 h-数组 进行排序
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    // 通过以下两个方法来操作数据
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

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In("/Users/hailong/Documents/Data/algs4-data/words3.txt");
        String[] a = in.readAllStrings();
        sort(a);
        System.out.println("数组a已排序？ " + isSorted(a));
        show(a);
    }
}
