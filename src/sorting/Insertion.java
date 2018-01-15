package sorting;

import edu.princeton.cs.algs4.StdRandom;
import fundanmentals.Date;
import fundanmentals.Transaction;

import java.util.Comparator;

/**
 * Created by hailong on 3/1/17.
 * 2-2 插入排序
 * 特点：将 a[i] 与 a[i-1], a[i-2], a[i-3]...a[0]元素依次进行比较，比它小的就交换，直到比下一个元素大
 * 学习使用比较器 Comparator 指定排序：参见Transaction类
 */
public class Insertion {
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {     // 使得索引 i 之前的元素是有序的
            for (int j = i; j >= 1 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
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


    // 以下学习使用接口Comparator比较器
    // 以下使用比较器指定对 Transaction类 排序
    // 这要求被排序的对象已经存在 静态内部类 实现了Comparator接口；Transaction类满足这个条件
    public static void sort(Comparable[] a, Comparator comparator) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j >= 1 && less(a[j], a[j - 1], comparator); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static boolean less(Comparable v, Comparable w, Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }

    public static void main(String[] args) {
        Double[] a = new Double[100];
        for (int i = 0; i < 100; i++) {
            a[i] = StdRandom.uniform();
        }
        sort(a);
        show(a);

        // 以下使用比较器指定对 Transaction类 排序
        // 学习使用Comparator比较器
        Date d1 = new Date(5, 21, 2016);
        Date d2 = new Date(6, 22, 2017);
        Transaction t1 = new Transaction("xyz", d1, 9800);
        Transaction t2 = new Transaction("abc", d2, 9900);
        Transaction[] t = new Transaction[]{t1, t2};
        sort(t, new Transaction.amountOrder());
        show(t);
        sort(t, new Transaction.whoOrder());        // 可见两个排序结果相反！！
        show(t);
        sort(t);                                    // 默认排序使用的是amount属性
        show(t);
    }
}
