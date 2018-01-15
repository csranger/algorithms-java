package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 3/1/17.
 * 2-1 选择排序
 * 对于长度为 N 的数组，选择排序需要大概 N2/2 次比较和 N 次交换
 */
public class Selection {
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[min])) min = j;
            }
            exch(a, i, min);
        }
    }

    // 排序代码通过以下两个方法来操作数据
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // 在单行中打印数组
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    // 判断数组是否已经排序
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
