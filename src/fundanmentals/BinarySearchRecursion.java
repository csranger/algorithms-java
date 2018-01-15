package fundanmentals;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hailong on 2/25/17.
 * 1-3 二分查找算法的递归实现
 */
public class BinarySearchRecursion {
    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1);
    }

    private static int rank(int key, int[] a, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) return rank(key, a, lo, mid - 1);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi);
        else return mid;
    }

    public static void main(String[] args) throws IOException {
        In in = new In("/Users/hailong/Documents/Data/algs4-data/tinyW.txt");  // 相当于Scanner，只是多了一个读取为数组的静态方法
        int[] whitelist = in.readAllInts();
        Arrays.sort(whitelist);
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tinyT.txt"));
        while (sc.hasNextInt()) {
            int key = sc.nextInt();
            if (rank(key, whitelist) == -1) {
                System.out.println(key);
            }
        }
    }
}
