package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 3/4/17.
 * 2-7 三向切分的快速排序
 */
public class Quick3way {
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        // 核心(相当于标准快排里的partition)：排定a[lo]的位置，同时lt左侧元素均小于v，gt右侧元素均大于v
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];    // 要排定的元素
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp > 0) exch(a, i, gt--);
            else if (cmp < 0) exch(a, i++, lt++);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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

    public static void main(String[] args) {
        In in = new In("/Users/hailong/Documents/Data/algs4-data/words3.txt");
        String[] a = in.readAllStrings();
        sort(a);
        show(a);
    }
}
